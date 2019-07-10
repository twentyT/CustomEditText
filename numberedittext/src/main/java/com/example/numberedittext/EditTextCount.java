package com.example.numberedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;


/**
 * Author by DanMaoHua, Email 583032574@qq.com, Date on 2018/10/15.
 * PS: Not easy to write code, please indicate.
 */
@SuppressLint("AppCompatCustomView")
public class EditTextCount extends EditText implements View.OnFocusChangeListener, TextWatcher {
    private String TEXT = "0/600";//index显示文本
    private int indexColor;//index字体颜色
    private int indexSize;//index字体大小
    private int indexMax;//index最大值
    private int indexMarginBottom;//index距离底部的高度
    private int indexMarginRight;//index距离右边的高度
    private int defaultHeight;//index默认edittext的高度
    private Typeface indexTextStyle;//index字体样式

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获得内容面板
        Layout mLayout = getLayout();
        // 获得内容面板的高度
        mLayoutHeight = mLayout.getHeight();
        // 获取上内边距
        paddingTop = getTotalPaddingTop();
        // 获取下内边距
        paddingBottom = getTotalPaddingBottom();
        // 获得控件的实际高度
        mHeight = getHeight();
        Paint paint = new Paint();
        // 去锯齿
        paint.setAntiAlias(true);
        paint.setColor(indexColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(indexTextStyle);
        paint.setStrokeWidth(1);
        paint.setTextSize(indexSize);
        paint.setShader(null);
        canvas.drawText(TEXT, canvas.getWidth() - paint.measureText(TEXT) - indexMarginRight, (mLayoutHeight + paddingTop + paddingBottom) > mHeight ? (mLayoutHeight + paddingTop + paddingBottom - indexMarginBottom) : (mHeight - indexMarginBottom), paint);
//        WisdomLogcat.e("内容面板高度：" + mLayoutHeight + "实际高度：" + mHeight + "top：" + paddingTop + "bottom：" + paddingBottom + "字体长度:" + paint.measureText(TEXT));
//        WisdomLogcat.e("width" + canvas.getWidth() + "height" + canvas.getHeight() + "left" + getLeft() + "right" + getRight() + "top" + getTop() + "bottom" + getBottom() + "x" + getX() + "Y" + getY() + "getMeasuredHeight" + getMeasuredHeight() + "getMeasureWidth" + getMeasuredWidth() + "ScaleX" + getScaleX() + "ScaleY" + getScaleY());
    }

    private int mLayoutHeight, paddingTop, paddingBottom, mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, defaultHeight + getPaddingBottom() + getPaddingTop());
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, defaultHeight + getPaddingBottom() + getPaddingTop());
        }


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public EditTextCount(Context context) {
        super(context);
    }

    @SuppressLint("ResourceAsColor")
    public EditTextCount(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextCount);
        indexColor = typedArray.getColor(R.styleable.EditTextCount_indexColor, R.color.colorPrimary);
        indexSize = typedArray.getInteger(R.styleable.EditTextCount_indexSize, 25);
        indexMax = typedArray.getInteger(R.styleable.EditTextCount_indexMaxNumber, 600);
        indexMarginBottom = typedArray.getInteger(R.styleable.EditTextCount_indexMarginBottom, 25);
        indexMarginRight = typedArray.getInteger(R.styleable.EditTextCount_indexMarginRight, 25);
        defaultHeight = typedArray.getInteger(R.styleable.EditTextCount_defaultHeight, 300);
        TEXT = "0/" + indexMax;
        int textType = typedArray.getInteger(R.styleable.EditTextCount_textType, 0);
        switch (textType) {
            case 0:
                indexTextStyle = Typeface.DEFAULT;
                break;
            case 1:
                indexTextStyle = Typeface.DEFAULT_BOLD;
                break;
            case 2:
                indexTextStyle = Typeface.MONOSPACE;
                break;
            case 3:
                indexTextStyle = Typeface.SANS_SERIF;
                break;
            case 4:
                indexTextStyle = Typeface.SERIF;
                break;
            default:
                break;
        }
    }

    public EditTextCount(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressLint("NewApi")
    public EditTextCount(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int textSize = s.length();
        if (textSize > indexMax) {
            indexMax = indexMax * 2;
        }
        TEXT = textSize + "/" + indexMax;
        invalidate();
//        requestLayout();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
