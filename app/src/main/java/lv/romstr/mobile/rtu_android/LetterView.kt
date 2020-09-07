package lv.romstr.mobile.rtu_android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class LetterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val letter: String
    private val letterTop: Float
    private val letterTextSize: Float
    private val circleColor: Int

    private val textPaint: TextPaint
    private val circlePaint: Paint

    init {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.CircleLetter, 0, 0)
        attributes.apply {
            circleColor = getColor(R.styleable.CircleLetter_circleColor, 0)
            letter = getString(R.styleable.CircleLetter_text) ?: ""
            letterTextSize = getDimension(R.styleable.CircleLetter_textSize, 0f)
            recycle()
        }

        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = letterTextSize
            textAlign = Paint.Align.CENTER
        }


        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = circleColor
            strokeWidth = 5f
            isAntiAlias = true
        }

        val rectangle = Rect()

        textPaint.getTextBounds(letter, 0, 1, rectangle)
        letterTop = rectangle.height() / 2f
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(
            width / 2f, height / 2f,
            width / 2f, circlePaint
        )

        canvas.drawText(letter, width / 2f, letterTop + height / 2f, textPaint)
    }
}