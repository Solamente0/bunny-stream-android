package net.bunnystream.stream

import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.pedro.common.ConnectChecker
import com.pedro.library.rtmp.RtmpCamera1

class StreamHandler {

    companion object {
        private const val TAG = "StreamHandler"
        private const val STREAM_CONNECT_RETRIES = 5
        private const val STREAM_CONNECT_DELAY_MS = 2000L
    }

    private lateinit var camera: RtmpCamera1
    private lateinit var streamEndpoint: String

    private val connectChecker: ConnectChecker = object : ConnectChecker {
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

                //binding.startStop.setBackgroundResource(R.drawable.start_stop_default_background)

            }
            camera.stopPreview()
        }
    }

    fun initialize(surfaceView: SurfaceView, streamEndpoint: String) {
        this.streamEndpoint = streamEndpoint
        camera = RtmpCamera1(surfaceView, connectChecker)
        camera.streamClient.setReTries(STREAM_CONNECT_RETRIES)

        surfaceView.holder.addCallback(surfaceCallback)
    }

    fun startStreaming(){
        if(camera.isRecording || camera.prepareAudio() || camera.prepareVideo()){
            camera.startStream(streamEndpoint)
        }
    }

    fun stopStreaming(){
        camera.stopStream()
    }

    fun isStreaming(): Boolean {
        return camera.isStreaming
    }

    fun switchCamera() {
        camera.switchCamera()
    }
}