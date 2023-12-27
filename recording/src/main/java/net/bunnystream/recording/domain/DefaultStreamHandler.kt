package net.bunnystream.recording.domain

import android.util.Log
import android.view.SurfaceHolder
import android.view.ViewGroup
import arrow.core.Either
import com.pedro.common.ConnectChecker
import com.pedro.encoder.input.video.CameraHelper.Facing
import com.pedro.encoder.utils.gl.AspectRatioMode
import com.pedro.library.generic.GenericCamera1
import com.pedro.library.view.OpenGlView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import net.bunnystream.recording.DeviceCamera
import net.bunnystream.recording.RecordingDurationListener
import net.bunnystream.recording.RecordingStateListener

class DefaultStreamHandler(
    private val streamRepository: RecordingRepository,
    coroutineDispatcher: CoroutineDispatcher
) : StreamHandler {
    companion object {
        private const val TAG = "StreamHandler"
        private const val STREAM_CONNECT_RETRIES = 3
        private const val STREAM_CONNECT_DELAY_MS = 2000L
    }

    override var recordingStateListener: RecordingStateListener? = null

    override var recordingDurationListener: RecordingDurationListener? = null

    private lateinit var camera: GenericCamera1

    private val scope = CoroutineScope(coroutineDispatcher)

    private var timerJob: Job? = null

    private var recordingStartTime: Long? = null

    private var cameraFacing = Facing.BACK

    private val connectChecker: ConnectChecker = object : ConnectChecker {
        override fun onAuthError() {
            Log.d(TAG, "ConnectChecker onAuthError")
            camera.stopStream()
            timerJob?.cancel()
            recordingStateListener?.onStreamAuthError()
            recordingStartTime = null
        }

        override fun onAuthSuccess() {
            Log.d(TAG, "ConnectChecker onAuthSuccess")
        }

        override fun onConnectionFailed(reason: String) {
            Log.d(TAG, "ConnectChecker onConnectionFailed: $reason")
            if (camera.streamClient.reTry(STREAM_CONNECT_DELAY_MS, reason, null)) {
                // no-op
            } else {
                camera.stopStream()
                timerJob?.cancel()
                recordingStateListener?.onStreamConnectionFailed(reason)
                recordingStartTime = null
            }
        }

        override fun onConnectionStarted(url: String) {
            Log.d(TAG, "ConnectChecker onConnectionStarted: $url")
            recordingStateListener?.onStreamInitializing()
        }

        override fun onConnectionSuccess() {
            Log.d(TAG, "ConnectChecker onConnectionSuccess")
            recordingStateListener?.onStreamConnected()
            recordingStartTime = System.currentTimeMillis()
            startTimer()
        }

        override fun onDisconnect() {
            Log.d(TAG, "ConnectChecker onDisconnect")
            timerJob?.cancel()
            recordingStateListener?.onStreamDisconnected()
            recordingStartTime = null
        }

        override fun onNewBitrate(bitrate: Long) {
            Log.d(TAG, "ConnectChecker onNewBitrate: $bitrate")
        }
    }

    private val surfaceCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            // no-op
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            camera.startPreview(cameraFacing)
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            if (camera.isStreaming) {
                camera.stopStream()
            }
            camera.stopPreview()
        }
    }

    override fun initialize(container: ViewGroup, deviceCamera: DeviceCamera) {
        val openGlView = OpenGlView(container.context)
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        openGlView.setAspectRatioMode(AspectRatioMode.Fill)
        container.addView(openGlView, lp)

        camera = GenericCamera1(openGlView, connectChecker)
        camera.streamClient.setReTries(STREAM_CONNECT_RETRIES)
        Log.d(TAG, "initialize, surface.isValid: ${openGlView.holder.surface.isValid}")
        openGlView.holder.addCallback(surfaceCallback)

        cameraFacing = when(deviceCamera) {
            DeviceCamera.FRONT -> Facing.FRONT
            DeviceCamera.BACK -> Facing.BACK
        }

        if(openGlView.holder.surface.isValid) {
            camera.startPreview(cameraFacing)
        }
    }

    override fun startStreaming(libraryId: Long, videoId: String?){
        recordingStateListener?.onStreamInitializing()
        scope.launch {

            if(videoId != null) {
                if (camera.isRecording || camera.prepareAudio() && camera.prepareVideo()) {
                    camera.startStream(videoId)
                }
            } else {
                val result = streamRepository.prepareRecording(libraryId)

                when(result) {
                    is Either.Left -> {
                        MainScope().launch {
                            recordingStateListener?.onStreamConnectionFailed(result.value)
                        }
                    }
                    is Either.Right -> {
                        if (camera.isRecording || camera.prepareAudio() && camera.prepareVideo()) {
                            camera.startStream(result.value)
                        }
                    }
                }
            }
        }
    }

    override fun stopStreaming(){
        camera.stopStream()
        recordingStateListener?.onStreamStopped()
    }

    override fun isStreaming(): Boolean {
        return camera.isStreaming
    }

    override fun selectCamera(deviceCamera: DeviceCamera) {
        Log.d(TAG, "selectCamera: $deviceCamera")
        when(deviceCamera) {
            DeviceCamera.BACK -> {
                camera.switchCamera(Facing.BACK.ordinal)
                recordingStateListener?.onCameraChanged(DeviceCamera.BACK)
            }
            DeviceCamera.FRONT -> {
                camera.switchCamera(Facing.FRONT.ordinal)
                recordingStateListener?.onCameraChanged(DeviceCamera.FRONT)
            }
        }
    }

    override fun switchCamera() {
        camera.switchCamera()
        when(camera.cameraFacing){
            Facing.FRONT -> recordingStateListener?.onCameraChanged(DeviceCamera.FRONT)
            Facing.BACK -> recordingStateListener?.onCameraChanged(DeviceCamera.BACK)
            else -> { /* no-op */ }
        }
    }

    override fun isMuted(): Boolean {
        return camera.isAudioMuted
    }

    override fun setMuted(muted: Boolean) {
        if(muted) {
            camera.disableAudio()
            recordingStateListener?.onAudioMuted(true)
        } else {
            camera.enableAudio()
            recordingStateListener?.onAudioMuted(false)
        }
    }

    private fun startTimer(){
        timerJob = MainScope().launch {
            while (isActive) {
                recordingStartTime?.let {
                    val duration = System.currentTimeMillis() - it
                    recordingDurationListener?.onDurationUpdated(duration, duration.toFormattedDuration())
                    delay(1000)
                }
            }
        }
    }
}