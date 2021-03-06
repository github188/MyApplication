package com.example.kevin_zhuang.myapplication.custom_view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.example.kevin_zhuang.myapplication.R;

/**
 * <b>Project:</b> MyApplication<br>
 * <b>Create Date:</b> 2017/2/24<br>
 * <b>Author:</b> kevin_zhuang<br>
 * <b>Description:</b> <br>
 */
public class CornerView extends ImageView {
    private static final String TAG = CornerView.class.getSimpleName();

    /**
     * 图片
     */
    private Bitmap mSrc;

    /**
     * 圆角的大小
     */
    private int mRadius;

    /**
     * 控件的宽度
     */
    private int mWidth;
    /**
     * 控件的高度
     */
    private int mHeight;

    public CornerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerView(Context context) {
        this(context, null);
    }

    /**
     * 初始化一些自定义的参数
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CornerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray
                a =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyle, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CornerView_src:
                    mSrc = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
                case R.styleable.CornerView_borderRadius:
                    mRadius = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));// 默认为10DP
                    break;
            }
        }
        a.recycle();
    }


    @Override
    public void setImageDrawable(Drawable drawable) {

//        BitmapDrawable bd = (BitmapDrawable)drawable;
//        mSrc = bd.getBitmap();
//        invalidate();

        //不行
//        if(drawable==null){
//            return;
//        }
//        int w = drawable.getIntrinsicWidth();
//        int h = drawable.getIntrinsicHeight();
//        Bitmap.Config config =
//                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//                                                            : Bitmap.Config.RGB_565;
//        mSrc = Bitmap.createBitmap(w, h, config);
//        invalidate();
//        super.setImageDrawable(drawable);
    }



    /**
     * 计算控件的高度和宽度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mWidth = specSize;
        } else {
            // 由图片决定的宽
//            int desireByImg = getPaddingLeft() + getPaddingRight() + mSrc.getWidth();
            int desireByImg = mSrc.getWidth();
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mWidth = Math.min(desireByImg, specSize);
            }
        }

        /***
         * 设置高度
         */

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        } else {
//            int desire = getPaddingTop() + getPaddingBottom() + mSrc.getHeight();
            int desire = mSrc.getHeight();
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);

    }


    @Override
    public void setBackgroundResource(int resid) {
//        super.setBackgroundResource(resid);
        mSrc = BitmapFactory.decodeResource(getResources(),resid);
        invalidate();
    }


    @Override
    public void setBackground(Drawable background) {
            Log.d(TAG,"setBackground");
//        super.setBackground(background);
    }


    @Override
    public void setBackgroundColor(int color) {
        Log.d(TAG,"setBackground color");

//        super.setBackgroundColor(color);
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {

//        switch (type) {
            // 如果是TYPE_CIRCLE绘制圆形
//            case TYPE_CIRCLE:

//                int min = Math.min(mWidth, mHeight);
//                /**
//                 * 长度如果不一致，按小的值进行压缩
//                 */
//                mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
//
//                canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
//                break;
//            case TYPE_ROUND:
                canvas.drawBitmap(createRoundConerImage(mSrc), 0, 0, null);
//                break;

//        }

    }

    /**
     * 根据原图和变长绘制圆形图片
     *
     * @param source
     * @param min
     *
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN，参考上面的说明
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 根据原图添加圆角
     *
     * @param source
     *
     * @return
     */
    private Bitmap createRoundConerImage(Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}

