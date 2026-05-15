package com.matrusneh.health.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.matrusneh.health.R
import java.text.SimpleDateFormat
import java.util.*

class KickBarChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var data: List<Int> = emptyList()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val barColor = ContextCompat.getColor(context, R.color.teal_700)
    private val labelColor = ContextCompat.getColor(context, R.color.mid_gray)
    
    private val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())

    fun setData(newData: List<Int>) {
        this.data = newData
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (data.isEmpty()) return

        val maxVal = data.maxOrNull()?.coerceAtLeast(1) ?: 1
        val padding = 40f
        val chartWidth = width - (padding * 2)
        val chartHeight = height - (padding * 4)
        val barWidth = chartWidth / data.size * 0.6f
        val spacing = chartWidth / data.size * 0.4f

        paint.color = barColor
        textPaint.color = labelColor
        textPaint.textSize = 30f
        textPaint.textAlign = Paint.Align.CENTER

        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -6)

        data.forEachIndexed { index, value ->
            val barLeft = padding + (index * (barWidth + spacing)) + (spacing / 2)
            val barHeight = (value.toFloat() / maxVal) * chartHeight
            val barTop = padding + chartHeight - barHeight
            val barRight = barLeft + barWidth
            val barBottom = padding + chartHeight

            // Draw Bar
            val rect = RectF(barLeft, barTop, barRight, barBottom)
            canvas.drawRoundRect(rect, 10f, 10f, paint)

            // Draw Value
            textPaint.isFakeBoldText = true
            textPaint.color = barColor
            canvas.drawText(value.toString(), barLeft + (barWidth / 2), barTop - 10f, textPaint)

            // Draw Day Label
            textPaint.isFakeBoldText = false
            textPaint.color = labelColor
            val dayLabel = dateFormat.format(cal.time)
            canvas.drawText(dayLabel, barLeft + (barWidth / 2), barBottom + 40f, textPaint)
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }
    }
}
