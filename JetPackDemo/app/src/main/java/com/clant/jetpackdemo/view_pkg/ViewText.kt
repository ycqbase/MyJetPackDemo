package com.clant.jetpackdemo.view_pkg

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.clant.jetpackdemo.R

class ViewText : View {
    private var tv_size = 15f
    private var tv_color = Color.BLACK
    private var tv_value = ""
    private lateinit var mPaint: Paint
    private lateinit var mFrontMetrics: Paint.FontMetrics
    private var mLayoutWidth: Int = 0
    private var mLayoutHeight: Int = 0
    private val mRect: Rect = Rect()
    private var mMaxCount: Int = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        var array = context?.obtainStyledAttributes(attrs, R.styleable.ViewText)
        if (array != null) {
            tv_size = array.getDimension(R.styleable.ViewText_tv_size, tv_size)
            tv_color = array.getInt(R.styleable.ViewText_tv_color, tv_color)
            tv_value = array.getString(R.styleable.ViewText_tv_value)

            mLayoutWidth = array.getLayoutDimension(R.styleable.ViewText_android_layout_width, -1);
            mLayoutHeight =
                array.getLayoutDimension(R.styleable.ViewText_android_layout_height, -2);
            array.recycle()
        }

        mPaint = Paint()
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.color = tv_color
        mPaint.textSize = tv_size



        for (i in 1 until tv_value.length) {
            var maxWidth = mPaint.measureText(tv_value.substring(0, i))
            if (maxWidth > mLayoutWidth) {
                mMaxCount = i
                break
            }
        }


        mPaint.getTextBounds(tv_value, 0, tv_value.length, mRect)
        mFrontMetrics = mPaint.fontMetrics




    }

    companion object {
        val ycq = "ycq"
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        setMeasuredDimension(
            getChildMeasureSpec(
                widthMeasureSpec,
                mLayoutWidth,
                mRect.right - mRect.left,
                paddingLeft + paddingRight
            ),
            getChildMeasureSpec(
                heightMeasureSpec, mLayoutHeight,
                (mFrontMetrics.bottom - mFrontMetrics.top).toInt(), paddingTop + paddingBottom
            )
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var baseY =
            height / 2 + (mFrontMetrics.bottom - mFrontMetrics.top) / 2 - mFrontMetrics.bottom
        canvas.drawText(
            tv_value,
            0f + paddingLeft,
            paddingTop + baseY,
            mPaint
        )
        mPaint.color = Color.BLACK
        canvas.drawLine(0f, baseY, 1000f, baseY, mPaint)

        var top = height / 2 + (mFrontMetrics.bottom - mFrontMetrics.top) / 2 + mFrontMetrics.top
        canvas.drawLine(0f, top, 1000f, top, mPaint)

        var bottom =
            height / 2 + (mFrontMetrics.bottom - mFrontMetrics.top) / 2 + mFrontMetrics.bottom
        canvas.drawLine(0f, bottom, 1000f, bottom, mPaint)

        var ascent =
            height / 2 + (mFrontMetrics.bottom - mFrontMetrics.top) / 2 + mFrontMetrics.ascent
        canvas.drawLine(0f, ascent, 1000f, ascent, mPaint)

        var descent =
            height / 2 + (mFrontMetrics.bottom - mFrontMetrics.top) / 2 - mFrontMetrics.descent
        canvas.drawLine(0f, descent, 1000f, descent, mPaint)

        var middle = height / 2f
        canvas.drawLine(0f, middle, 1000f, middle, mPaint)



        Log.i(
            "yxf",
            "${mFrontMetrics.top} ${mFrontMetrics.bottom}  ${mFrontMetrics.ascent}  ${mFrontMetrics.descent}"
        )
    }

    fun getChildMeasureSpec(spec: Int, childDimension: Int, rSize: Int, padding: Int): Int {
        val specMode = MeasureSpec.getMode(spec)
        val specSize = MeasureSpec.getSize(spec)
        var resultSize = 0
        var resultMode = 0
        when (specMode) {
            MeasureSpec.EXACTLY -> if (childDimension >= 0) {
                resultSize = childDimension
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == ViewGroup.LayoutParams.MATCH_PARENT) {
                resultSize = specSize
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == ViewGroup.LayoutParams.WRAP_CONTENT) {
                resultSize = rSize + padding
                resultMode = MeasureSpec.AT_MOST
            }
            MeasureSpec.AT_MOST -> if (childDimension >= 0) {
                resultSize = childDimension
                resultMode = MeasureSpec.EXACTLY
            } else if (childDimension == ViewGroup.LayoutParams.MATCH_PARENT) {
                resultSize = specSize
                resultMode = MeasureSpec.AT_MOST
            } else if (childDimension == ViewGroup.LayoutParams.WRAP_CONTENT) {
                resultSize = rSize + padding
                resultMode = MeasureSpec.AT_MOST
            }
        }
        return MeasureSpec.makeMeasureSpec(resultSize, resultMode)
    }

}