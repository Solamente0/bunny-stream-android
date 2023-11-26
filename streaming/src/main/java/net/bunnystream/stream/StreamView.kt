package net.bunnystream.stream

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import androidx.constraintlayout.widget.ConstraintLayout
import com.pedro.common.ConnectChecker
import com.pedro.library.rtmp.RtmpCamera1
import net.bunnystream.stream.databinding.StreamViewBinding

class StreamView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "StreamView"
        private const val STREAM_CONNECT_RETRIES = 10
        private const val STREAM_CONNECT_DELAY_MS = 5000L
    }

    private val binding = StreamViewBinding.inflate(LayoutInflater.from(context), this)

    private val connectChecker = object : ConnectChecker {
        override fun onAuthError() {
            Log.d(TAG, "ConnectChecker onAuthError")
            camera.stopStream()
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
            }
        }

        override fun onConnectionStarted(url: String) {
            Log.d(TAG, "ConnectChecker onConnectionStarted: $url")
        }

        override fun onConnectionSuccess() {
            Log.d(TAG, "ConnectChecker onConnectionSuccess")
        }

        override fun onDisconnect() {
            Log.d(TAG, "ConnectChecker onDisconnect")
        }

        override fun onNewBitrate(bitrate: Long) {
            Log.d(TAG, "ConnectChecker onNewBitrate: $bitrate")
        }
    }

    private val surfaceCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            Log.d(TAG, "SurfaceHolder.Callback surfaceCreated: $holder")
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            Log.d(TAG, "SurfaceHolder.Callback surfaceChanged: $holder, format: $format, width: $width, height: $height")
            camera.startPreview()
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            Log.d(TAG, "SurfaceHolder.Callback surfaceDestroyed: $holder")
            if (camera.isStreaming) {
                camera.stopStream()
                binding.startStop.setBackgroundResource(R.drawable.start_stop_default_background)
            }
            camera.stopPreview()
        }
    }

    private val camera = RtmpCamera1(binding.surfaceView, connectChecker)

    init {
        camera.streamClient.setReTries(STREAM_CONNECT_RETRIES)
        binding.surfaceView.holder.addCallback(surfaceCallback)

        binding.switchCamera.setOnClickListener {
            camera.switchCamera()
        }

        val streamUrl = ""

        binding.startStop.setOnClickListener {
            if(!camera.isStreaming) {
                if(camera.isRecording || camera.prepareAudio() || camera.prepareVideo()){
                    camera.startStream(streamUrl)
                    it.setBackgroundResource(R.drawable.start_stop_recording_background)
                }
            } else {
                it.setBackgroundResource(R.drawable.start_stop_default_background)
                camera.stopStream()
            }
        }
    }

    private fun fetchConfig() {

    }
}