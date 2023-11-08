package net.bunnystream.player.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.media3.common.util.UnstableApi
import com.github.rubensousa.previewseekbar.media3.PreviewTimeBar
import net.bunnystream.player.model.Moment

class BunnyTimeBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : PreviewTimeBar(context, attrs) {

    var pointColor: Paint = Paint()
    private var moments: List<Moment>? = null
    private var totalDuration: Long? = null

    fun setMoments(moments: List<Moment>, totalDuration: Long) {
        this.moments = moments
        this.totalDuration = totalDuration
        invalidate()
    }

    @UnstableApi
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val moments = moments ?: return
        val totalDuration = totalDuration ?: return

        val width = width - getPaddingLeft() - getPaddingRight()

        for (point in moments) {
            if (point.timestamp in 0..totalDuration) {
                val proportion = point.timestamp.toFloat() / totalDuration
                val xPosition = getPaddingLeft() + (width * proportion).toInt()

                val radius = POINT_WIDTH.coerceAtMost(POINT_HEIGHT) / 2f

                canvas.drawCircle(
                    xPosition.toFloat(),
                    (height / 2f),
                    radius,
                    pointColor,
                )
            }
        }
    }

    fun getXPositionForTime(time: Long): Int {
        if (totalDuration != null && totalDuration!! <= 0) {
            return -1
        }
        val proportion: Float = time.toFloat() / totalDuration!!
        val width = width - getPaddingLeft() - getPaddingRight()
        return getPaddingLeft() + (width * proportion).toInt()
    }

    companion object {
        private const val POINT_WIDTH = 30
        private const val POINT_HEIGHT = 30
    }

}
