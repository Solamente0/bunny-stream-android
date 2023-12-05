package net.bunnystream.stream

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.isVisible
import kotlinx.coroutines.Dispatchers
import net.bunnystream.androidsdk.BunnyStreamSdk
import net.bunnystream.stream.data.DefaultStreamRepository
import net.bunnystream.stream.databinding.StreamViewBinding
import net.bunnystream.stream.domain.DefaultStreamHandler
import net.bunnystream.stream.domain.StreamHandler
import net.bunnystream.stream.domain.StreamRepository

class BunnyStreamView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr), StreamView {

    companion object {
        private const val TAG = "BunnyStreamView"
    }

    private val binding = StreamViewBinding.inflate(LayoutInflater.from(context), this)

    private val streamRepository: StreamRepository = DefaultStreamRepository(Dispatchers.IO)
    private val streamHandler: StreamHandler = DefaultStreamHandler(
        streamRepository = streamRepository,
        coroutineDispatcher = Dispatchers.IO
    )

    private val libraryId = BunnyStreamSdk.libraryId

    override var hideDefaultControls: Boolean = false
        set(value) {
            field = value
            binding.streamControls.isVisible = !value
        }

    override var closeStreamClickListener: OnClickListener? = null
        set(value) {
            field = value
            binding.close.setOnClickListener(value)
        }

    override var streamStateListener: StreamStateListener? = null

    override var streamDurationListener: StreamDurationListener? = null

    private var defaultCamera = DeviceCamera.BACK

    init {
        extractAttrs(attrs)

        streamHandler.streamStateListener = object : StreamStateListener {
            override fun onStreamInitializing() {
                setPreparing()
                streamStateListener?.onStreamInitializing()
            }

            override fun onStreamConnected() {
                setStreaming()
                streamStateListener?.onStreamConnected()
            }

            override fun onStreamStopped() {
                setNotStreaming()
                streamStateListener?.onStreamStopped()
            }

            override fun onStreamDisconnected() {
                setNotStreaming()
                streamStateListener?.onStreamDisconnected()
            }

            override fun onStreamAuthError() {
                setNotStreaming()
                if(streamStateListener != null) {
                    streamStateListener?.onStreamAuthError()
                } else {
                    showStreamAuthErrorDialog()
                }
            }

            override fun onStreamConnectionFailed(message: String) {
                setNotStreaming()
                if(streamStateListener != null) {
                    streamStateListener?.onStreamConnectionFailed(message)
                } else {
                    showStreamConnectionErrorDialog(message)
                }
            }

            override fun onCameraChanged(deviceCamera: DeviceCamera) {
                streamStateListener?.onCameraChanged(deviceCamera)
            }

            override fun onAudioMuted(muted: Boolean) {
                binding.mute.isActivated = muted
                streamStateListener?.onAudioMuted(muted)
            }
        }

        binding.switchCamera.setOnClickListener {
            streamHandler.switchCamera()
        }

        binding.startStop.setOnClickListener {
            if(!streamHandler.isStreaming()) {
                streamHandler.startStreaming(libraryId)
            } else {
                streamHandler.stopStreaming()
            }
        }

        binding.mute.setOnClickListener {
            streamHandler.setMuted(!streamHandler.isMuted())
        }

        binding.close.setOnClickListener(closeStreamClickListener)

        streamHandler.streamDurationListener = object : StreamDurationListener {
            override fun onDurationUpdated(durationMillis: Long, durationFormatted: String) {
                val onlineText = context.getString(R.string.stream_status_online)
                binding.status.text = "$onlineText  •  $durationFormatted"
                streamDurationListener?.onDurationUpdated(durationMillis, durationFormatted)
            }
        }
    }

    override fun startPreview(){
        val cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val micPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)

        val camGranted = cameraPermission == PackageManager.PERMISSION_GRANTED
        val micGranted = micPermission == PackageManager.PERMISSION_GRANTED

        if(camGranted && micGranted){
            streamHandler.initialize(binding.surfaceViewContainer, defaultCamera)
        } else {
            Log.w(TAG, "Couldn't initialize preview, " +
                    "missing android.permission.CAMERA and " +
                    "android.permission.RECORD_AUDIO permissions"
            )
        }
    }

    override fun startStreaming(){
        streamHandler.startStreaming(libraryId)
    }

    override fun stopStreaming(){
        streamHandler.stopStreaming()
    }

    override fun switchCamera(){
        streamHandler.switchCamera()
    }

    override fun setAudioMuted(muted: Boolean){
        streamHandler.setMuted(muted)
    }

    override fun isStreaming(): Boolean {
        return streamHandler.isStreaming()
    }

    private fun setStreaming(){
        binding.startStop.isActivated = true
        binding.startStop.visibility = View.VISIBLE
        binding.progress.isVisible = false

        binding.status.isActivated = true
        binding.status.setText(R.string.stream_status_online)

        binding.close.visibility = View.INVISIBLE
    }

    private fun setPreparing(){
        binding.startStop.visibility = View.INVISIBLE
        binding.progress.isVisible = true
    }

    private fun setNotStreaming(){
        binding.startStop.isActivated = false
        binding.startStop.visibility = View.VISIBLE
        binding.progress.isVisible = false

        binding.status.isActivated = false
        binding.status.setText(R.string.stream_status_offline)

        binding.close.visibility = View.VISIBLE
    }

    private fun showStreamConnectionErrorDialog(message: String) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.stream_connection_error))
            .setMessage(message)
            .setNeutralButton(R.string.dialog_ok, null)
            .show()
    }

    private fun showStreamAuthErrorDialog() {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.stream_auth_error))
            .setNeutralButton(R.string.dialog_ok, null)
            .show()
    }

    private fun extractAttrs(attrs: AttributeSet?) {
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.BunnyStreamView).use {
                hideDefaultControls = it.getBoolean(R.styleable.BunnyStreamView_bsvHideDefaultControls, false)
                val cam = it.getInt(R.styleable.BunnyStreamView_bsvDefaultCamera, 0)
                defaultCamera = if(cam == 0) {
                    DeviceCamera.BACK
                } else {
                    DeviceCamera.FRONT
                }
            }
        }
    }
}