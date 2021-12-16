package com.company.test_app.presentation.view

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.os.SystemClock
import android.provider.Settings
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.company.test_app.R
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt

@Suppress("unused", "MemberVisibilityCanBePrivate")
class ProgressWheel : View {
    private val barLength = 16
    private val barMaxLength = 270
    private val pauseGrowingTime: Long = 200
    /**
     * *********
     * DEFAULTS *
     * **********
     */
    //Sizes (with defaults in DP)
    private var circleRadius = 28
    private var barWidth = 4
    private var rimWidth = 4
    private var fillRadius = false
    private var timeStartGrowing = 0.0
    private var barSpinCycleTime = 460.0
    private var barExtraLength = 0f
    private var barGrowingFromFront = true
    private var pausedTimeWithoutGrowing: Long = 0
    //Colors (with defaults)
    private var barColor = -0x56000000
    private var rimColor = 0x00FFFFFF

    //Paints
    private val barPaint = object : Paint() {
        init {
            style = Style.STROKE
            strokeJoin = Join.ROUND
            strokeCap = Cap.ROUND
        }
    }
    private val rimPaint = Paint()

    //Rectangles
    private var circleBounds = RectF()

    //Animation
    //The amount of degrees per timestamp
    private var spinSpeed = 230.0f
    //private float spinSpeed = 120.0f;
    // The last time the spinner was animated
    private var lastTimeAnimated: Long = 0

    private var linearProgress: Boolean = false

    private var mProgress = 0.0f
    private var mTargetProgress = 0.0f
    /**
     * Check if the wheel is currently spinning
     */

    var isSpinning = false
        private set

    private var callback: ProgressCallback? = null

    private var shouldAnimate: Boolean = false

    /**
     * @return the current progress between 0.0 and 1.0,
     * if the wheel is indeterminate, then the result is -1
     */
    //----------------------------------
    //Getters + setters
    //----------------------------------

    /**
     * Set the progress to a specific value,
     * the bar will smoothly animate until that value
     *
     * [progress] the progress between 0 and 1
     */
    // If we are currently in the right position
    // we setValue again the last time animated so the
    // animation starts smooth from here
    var progress: Float
        get() = if (isSpinning) -1f else mProgress / 360.0f
        set(progress) {
            var innerProgress = progress
            if (isSpinning) {
                mProgress = 0.0f
                isSpinning = false

                runCallback()
            }

            if (innerProgress > 1.0f) {
                innerProgress -= 1.0f
            } else if (innerProgress < 0) {
                innerProgress = 0f
            }

            if (innerProgress == mTargetProgress) {
                return
            }
            if (mProgress == mTargetProgress) {
                lastTimeAnimated = SystemClock.uptimeMillis()
            }

            mTargetProgress = (innerProgress * 360.0f).coerceAtMost(360.0f)

            invalidate()
        }

    /**
     * The constructor for the ProgressWheel
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.ProgressWheel))

        setAnimationEnabled()
    }

    /**
     * The constructor for the ProgressWheel
     */
    constructor(context: Context) : super(context) {
        setAnimationEnabled()
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun setAnimationEnabled() {
        val currentApiVersion = Build.VERSION.SDK_INT

        val animationValue: Float
        if (currentApiVersion >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            animationValue = Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.ANIMATOR_DURATION_SCALE, 1f
            )
        } else {
            @Suppress("DEPRECATION")
            animationValue = Settings.System.getFloat(
                context.contentResolver,
                Settings.System.ANIMATOR_DURATION_SCALE, 1f
            )
        }

        shouldAnimate = animationValue != 0f
    }

    //----------------------------------
    //Setting up stuff
    //----------------------------------

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val viewWidth = circleRadius + this.paddingLeft + this.paddingRight
        val viewHeight = circleRadius + this.paddingTop + this.paddingBottom

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        //Measure Width
        width = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                //Must be this size
                widthSize
            }
            MeasureSpec.AT_MOST -> {
                //Can't be bigger than...
                viewWidth.coerceAtMost(widthSize)
            }
            else -> {
                //Be whatever you want
                viewWidth
            }
        }

        //Measure Height
        height = if (heightMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            viewHeight.coerceAtMost(heightSize)
        } else {
            //Be whatever you want
            viewHeight
        }

        setMeasuredDimension(width, height)
    }

    /**
     * Use onSizeChanged instead of onAttachedToWindow to getValue the dimensions of the view,
     * because this method is called after measuring the dimensions of MATCH_PARENT & WRAP_CONTENT.
     * Use this dimensions to setup the bounds and paints.
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        setupBounds(w, h)
        setupPaints()
        invalidate()
    }

    /**
     * Set the properties of the paints we're using to
     * draw the progress wheel
     */
    private fun setupPaints() {
        barPaint.color = barColor
        barPaint.isAntiAlias = true
        barPaint.strokeWidth = barWidth.toFloat()

        rimPaint.color = rimColor
        rimPaint.isAntiAlias = true
        rimPaint.style = Paint.Style.STROKE
        rimPaint.strokeWidth = rimWidth.toFloat()
    }

    /**
     * Set the bounds of the component
     */
    private fun setupBounds(layout_width: Int, layout_height: Int) {
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight

        if (!fillRadius) {
            // Width should equal to Height, find the min value to setup the circle
            val minValue =
                (layout_width - paddingLeft - paddingRight).coerceAtMost(layout_height - paddingBottom - paddingTop)

            val circleDiameter = minValue.coerceAtMost(circleRadius * 2 - barWidth * 2)

            // Calc the Offset if needed for centering the wheel in the available space
            val xOffset = (layout_width - paddingLeft - paddingRight - circleDiameter) / 2 + paddingLeft
            val yOffset = (layout_height - paddingTop - paddingBottom - circleDiameter) / 2 + paddingTop

            circleBounds = RectF(
                (xOffset + barWidth).toFloat(),
                (yOffset + barWidth).toFloat(),
                (xOffset + circleDiameter - barWidth).toFloat(),
                (yOffset + circleDiameter - barWidth).toFloat()
            )
        } else {
            circleBounds = RectF(
                (paddingLeft + barWidth).toFloat(), (paddingTop + barWidth).toFloat(),
                (layout_width - paddingRight - barWidth).toFloat(), (layout_height - paddingBottom - barWidth).toFloat()
            )
        }
    }

    /**
     * Parse the attributes passed to the view from the XML
     *
     * @param typedArray the attributes to parse
     */
    private fun parseAttributes(typedArray: TypedArray) {
        // We transform the default values from DIP to pixels
        val metrics = context.resources.displayMetrics
        barWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, barWidth.toFloat(), metrics).toInt()
        rimWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rimWidth.toFloat(), metrics).toInt()
        circleRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, circleRadius.toFloat(), metrics).toInt()

        circleRadius = typedArray.getDimension(R.styleable.ProgressWheel_pw_circleRadius, circleRadius.toFloat()).toInt()

        fillRadius = typedArray.getBoolean(R.styleable.ProgressWheel_pw_fillRadius, false)

        barWidth = typedArray.getDimension(R.styleable.ProgressWheel_pw_barWidth, barWidth.toFloat()).toInt()

        rimWidth = typedArray.getDimension(R.styleable.ProgressWheel_pw_rimWidth, rimWidth.toFloat()).toInt()

        val baseSpinSpeed = typedArray.getFloat(R.styleable.ProgressWheel_pw_spinSpeed, spinSpeed / 360.0f)
        spinSpeed = baseSpinSpeed * 360

        barSpinCycleTime = typedArray.getInt(R.styleable.ProgressWheel_pw_barSpinCycleTime, barSpinCycleTime.toInt()).toDouble()

        barColor = typedArray.getColor(R.styleable.ProgressWheel_pw_barColor, barColor)

        rimColor = typedArray.getColor(R.styleable.ProgressWheel_pw_rimColor, rimColor)

        linearProgress = typedArray.getBoolean(R.styleable.ProgressWheel_pw_linearProgress, false)

        if (typedArray.getBoolean(R.styleable.ProgressWheel_pw_progressIndeterminate, false)) {
            spin()
        }

        // Recycle
        typedArray.recycle()
    }

    fun setCallback(progressCallback: ProgressCallback) {
        callback = progressCallback

        if (!isSpinning) {
            runCallback()
        }
    }

    //----------------------------------
    //Animation stuff
    //----------------------------------

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(circleBounds, 360f, 360f, false, rimPaint)

        var mustInvalidate = false

        if (!shouldAnimate) {
            return
        }

        if (isSpinning) {
            //Draw the spinning bar
            mustInvalidate = true

            val deltaTime = SystemClock.uptimeMillis() - lastTimeAnimated
            val deltaNormalized = deltaTime * spinSpeed / 1000.0f

            updateBarLength(deltaTime)

            mProgress += deltaNormalized
            if (mProgress > 360) {
                mProgress -= 360f

                // A full turn has been completed
                // we run the callback with -1 in case we want to
                // do something, like changing the color
                runCallback(-1.0f)
            }
            lastTimeAnimated = SystemClock.uptimeMillis()

            var from = mProgress - 90
            var length = barLength + barExtraLength

            if (isInEditMode) {
                from = 0f
                length = 135f
            }

            canvas.drawArc(circleBounds, from, length, false, barPaint)
        } else {
            val oldProgress = mProgress

            if (mProgress != mTargetProgress) {
                //We smoothly decrease the progress bar
                mustInvalidate = true

                val deltaTime = (SystemClock.uptimeMillis() - lastTimeAnimated).toFloat() / 1000
                val deltaNormalized = deltaTime * spinSpeed

                mProgress = (mProgress + deltaNormalized).coerceAtMost(mTargetProgress)
                lastTimeAnimated = SystemClock.uptimeMillis()
            }

            if (oldProgress != mProgress) {
                runCallback()
            }

            var offset = 0.0f
            var progress = mProgress
            if (!linearProgress) {
                val factor = 2.0f
                offset = (1.0f - (1.0f - mProgress / 360.0f).toDouble().pow((2.0f * factor).toDouble())).toFloat() * 360.0f
                progress = (1.0f - (1.0f - mProgress / 360.0f).toDouble().pow(factor.toDouble())).toFloat() * 360.0f
            }

            if (isInEditMode) {
                progress = 360f
            }

            canvas.drawArc(circleBounds, offset - 90, progress, false, barPaint)
        }

        if (mustInvalidate) {
            invalidate()
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)

        if (visibility == VISIBLE) {
            lastTimeAnimated = SystemClock.uptimeMillis()
        }
    }

    private fun updateBarLength(deltaTimeInMilliSeconds: Long) {
        if (pausedTimeWithoutGrowing >= pauseGrowingTime) {
            timeStartGrowing += deltaTimeInMilliSeconds.toDouble()

            if (timeStartGrowing > barSpinCycleTime) {
                // We completed a size change cycle
                // (growing or shrinking)
                timeStartGrowing -= barSpinCycleTime
                //if(barGrowingFromFront) {
                pausedTimeWithoutGrowing = 0
                //}
                barGrowingFromFront = !barGrowingFromFront
            }

            val distance = cos((timeStartGrowing / barSpinCycleTime + 1) * Math.PI).toFloat() / 2 + 0.5f
            val destLength = (barMaxLength - barLength).toFloat()

            if (barGrowingFromFront) {
                barExtraLength = distance * destLength
            } else {
                val newLength = destLength * (1 - distance)
                mProgress += barExtraLength - newLength
                barExtraLength = newLength
            }
        } else {
            pausedTimeWithoutGrowing += deltaTimeInMilliSeconds
        }
    }

    /**
     * Reset the count (in increment mode)
     */
    fun resetCount() {
        mProgress = 0.0f
        mTargetProgress = 0.0f
        invalidate()
    }

    /**
     * Turn off spin mode
     */
    fun stopSpinning() {
        isSpinning = false
        mProgress = 0.0f
        mTargetProgress = 0.0f
        invalidate()
    }

    /**
     * Puts the view on spin mode
     */
    fun spin() {
        lastTimeAnimated = SystemClock.uptimeMillis()
        isSpinning = true
        invalidate()
    }

    private fun runCallback(value: Float) {
        if (callback != null) {
            callback!!.onProgressUpdate(value)
        }
    }

    private fun runCallback() {
        if (callback != null) {
            val normalizedProgress = (mProgress * 100 / 360.0f).roundToInt().toFloat() / 100
            callback!!.onProgressUpdate(normalizedProgress)
        }
    }

    /**
     * Set the progress to a specific value,
     * the bar will be setValue instantly to that value
     *
     * @param progress the progress between 0 and 1
     */
    fun setInstantProgress(progress: Float) {
        @Suppress("NAME_SHADOWING")
        var progress = progress
        if (isSpinning) {
            mProgress = 0.0f
            isSpinning = false
        }

        if (progress > 1.0f) {
            progress -= 1.0f
        } else if (progress < 0) {
            progress = 0f
        }

        if (progress == mTargetProgress) {
            return
        }

        mTargetProgress = (progress * 360.0f).coerceAtMost(360.0f)
        mProgress = mTargetProgress
        lastTimeAnimated = SystemClock.uptimeMillis()
        invalidate()
    }

    // Great way to save a view's state http://stackoverflow.com/a/7089687/1991053
    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()

        val ss = WheelSavedState(superState)

        // We save everything that can be changed at runtime
        ss.mProgress = this.mProgress
        ss.mTargetProgress = this.mTargetProgress
        ss.isSpinning = this.isSpinning
        ss.spinSpeed = this.spinSpeed
        ss.barWidth = this.barWidth
        ss.barColor = this.barColor
        ss.rimWidth = this.rimWidth
        ss.rimColor = this.rimColor
        ss.circleRadius = this.circleRadius
        ss.linearProgress = this.linearProgress
        ss.fillRadius = this.fillRadius

        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is WheelSavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        super.onRestoreInstanceState(state.superState)

        this.mProgress = state.mProgress
        this.mTargetProgress = state.mTargetProgress
        this.isSpinning = state.isSpinning
        this.spinSpeed = state.spinSpeed
        this.barWidth = state.barWidth
        this.barColor = state.barColor
        this.rimWidth = state.rimWidth
        this.rimColor = state.rimColor
        this.circleRadius = state.circleRadius
        this.linearProgress = state.linearProgress
        this.fillRadius = state.fillRadius

        this.lastTimeAnimated = SystemClock.uptimeMillis()
    }

    /**
     * Sets the determinate progress mode
     *
     * @param isLinear if the progress should decrease linearly
     */
    fun setLinearProgress(isLinear: Boolean) {
        linearProgress = isLinear
        if (!isSpinning) {
            invalidate()
        }
    }

    /**
     * @return the radius of the wheel in pixels
     */
    fun getCircleRadius(): Int {
        return circleRadius
    }

    /**
     * Sets the radius of the wheel
     *
     * @param circleRadius the expected radius, in pixels
     */
    fun setCircleRadius(circleRadius: Int) {
        this.circleRadius = circleRadius
//        if (!isSpinning) {
//            invalidate()
//        }
        requestLayout()
    }

    /**
     * @return the width of the spinning bar
     */
    fun getBarWidth(): Int {
        return barWidth
    }

    /**
     * Sets the width of the spinning bar
     *
     * @param barWidth the spinning bar width in pixels
     */
    fun setBarWidth(barWidth: Int) {
        this.barWidth = barWidth
        if (!isSpinning) {
            invalidate()
        }
    }

    /**
     * @return the color of the spinning bar
     */
    fun getBarColor(): Int {
        return barColor
    }

    /**
     * Sets the color of the spinning bar
     *
     * @param barColor The spinning bar color
     */
    fun setBarColor(barColor: Int) {
        this.barColor = barColor
        setupPaints()
        if (!isSpinning) {
            invalidate()
        }
    }

    /**
     * @return the color of the wheel's contour
     */
    fun getRimColor(): Int {
        return rimColor
    }

    /**
     * Sets the color of the wheel's contour
     *
     * @param rimColor the color for the wheel
     */
    fun setRimColor(rimColor: Int) {
        this.rimColor = rimColor
        setupPaints()
        if (!isSpinning) {
            invalidate()
        }
    }

    /**
     * @return the base spinning speed, in full circle turns per timestamp
     * (1.0 equals on full turn in one timestamp), this value also is applied for
     * the smoothness when setting a progress
     */
    fun getSpinSpeed(): Float {
        return spinSpeed / 360.0f
    }

    /**
     * Sets the base spinning speed, in full circle turns per timestamp
     * (1.0 equals on full turn in one timestamp), this value also is applied for
     * the smoothness when setting a progress
     *
     * @param spinSpeed the desired base speed in full turns per timestamp
     */
    fun setSpinSpeed(spinSpeed: Float) {
        this.spinSpeed = spinSpeed * 360.0f
    }

    /**
     * @return the width of the wheel's contour in pixels
     */
    fun getRimWidth(): Int {
        return rimWidth
    }

    /**
     * Sets the width of the wheel's contour
     *
     * @param rimWidth the width in pixels
     */
    fun setRimWidth(rimWidth: Int) {
        this.rimWidth = rimWidth
        if (!isSpinning) {
            invalidate()
        }
    }

    interface ProgressCallback {
        /**
         * Method to call when the progress reaches a value
         * in order to avoid float precision issues, the progress
         * is rounded to a float with two decimals.
         *
         *
         * In indeterminate mode, the callback is called each time
         * the wheel completes an animation cycle, with, the progress value is -1.0f
         *
         * @param progress a double value between 0.00 and 1.00 both included
         */
        fun onProgressUpdate(progress: Float)
    }

    internal class WheelSavedState : BaseSavedState {
        var mProgress: Float = 0.toFloat()
        var mTargetProgress: Float = 0.toFloat()
        var isSpinning: Boolean = false
        var spinSpeed: Float = 0.toFloat()
        var barWidth: Int = 0
        var barColor: Int = 0
        var rimWidth: Int = 0
        var rimColor: Int = 0
        var circleRadius: Int = 0
        var linearProgress: Boolean = false
        var fillRadius: Boolean = false

        constructor(superState: Parcelable?) : super(superState)

        private constructor(`in`: Parcel) : super(`in`) {
            this.mProgress = `in`.readFloat()
            this.mTargetProgress = `in`.readFloat()
            this.isSpinning = `in`.readByte().toInt() != 0
            this.spinSpeed = `in`.readFloat()
            this.barWidth = `in`.readInt()
            this.barColor = `in`.readInt()
            this.rimWidth = `in`.readInt()
            this.rimColor = `in`.readInt()
            this.circleRadius = `in`.readInt()
            this.linearProgress = `in`.readByte().toInt() != 0
            this.fillRadius = `in`.readByte().toInt() != 0
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(this.mProgress)
            out.writeFloat(this.mTargetProgress)
            out.writeByte((if (isSpinning) 1 else 0).toByte())
            out.writeFloat(this.spinSpeed)
            out.writeInt(this.barWidth)
            out.writeInt(this.barColor)
            out.writeInt(this.rimWidth)
            out.writeInt(this.rimColor)
            out.writeInt(this.circleRadius)
            out.writeByte((if (linearProgress) 1 else 0).toByte())
            out.writeByte((if (fillRadius) 1 else 0).toByte())
        }

        companion object {
            //required field that makes Parcelables from a Parcel
            @JvmField
            val CREATOR: Parcelable.Creator<WheelSavedState> =
                object : Parcelable.Creator<WheelSavedState> {
                    override fun createFromParcel(`in`: Parcel): WheelSavedState {
                        return WheelSavedState(`in`)
                    }

                    override fun newArray(size: Int): Array<WheelSavedState?> {
                        return arrayOfNulls(size)
                    }
                }
        }
    }
}
