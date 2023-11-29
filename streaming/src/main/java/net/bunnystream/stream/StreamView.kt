package net.bunnystream.stream

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import net.bunnystream.stream.databinding.StreamViewBinding

class StreamView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "StreamView"
    }

    private val binding = StreamViewBinding.inflate(LayoutInflater.from(context), this)

    private val streamHandler = StreamHandler()

    init {

        binding.switchCamera.setOnClickListener {
            streamHandler.switchCamera()
        }

        binding.startStop.setOnClickListener {
            if(!streamHandler.isStreaming()) {
                streamHandler.startStreaming()
                it.setBackgroundResource(R.drawable.start_stop_recording_background)
            } else {
                it.setBackgroundResource(R.drawable.start_stop_default_background)
                streamHandler.stopStreaming()
            }
        }
    }

    private fun fetchConfig() {

    }
}