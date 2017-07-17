package com.qbw.customview.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author qinbaowei
 * @createtime 2017/04/14 11:47
 * @email qbaowei@qq.com
 * @description
 */


public class TitleBar extends FrameLayout {

    private Listener mListener;

    private TextView mTxtTitle;

    private ViewGroup mVgLeft;
    private TextView mTxtLeft;
    private ImageView mImgLeft;

    private ViewGroup mVgRight;
    private TextView mTxtRight;
    private ImageView mImgRight;

    public TitleBar(Context context) {
        super(context);
        init(null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        View view = LayoutInflater.from(getContext())
                                  .inflate(R.layout.tb_view_titlebar, this, true);

        mTxtTitle = (TextView) view.findViewById(R.id.txt_title);
        mVgLeft = (ViewGroup) view.findViewById(R.id.layout_left);
        mTxtLeft = (TextView) view.findViewById(R.id.txt_left);
        mImgLeft = (ImageView) view.findViewById(R.id.img_left);
        mVgRight = (ViewGroup) view.findViewById(R.id.layout_right);
        mTxtRight = (TextView) view.findViewById(R.id.txt_right);
        mImgRight = (ImageView) view.findViewById(R.id.img_right);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);

        Drawable bg = typedArray.getDrawable(R.styleable.TitleBar_tb_backgroupd);
        if (bg != null) {
            view.setBackgroundDrawable(bg);
        }

        int defaultColor = getResources().getColor(android.R.color.white);

        mTxtTitle.setText(typedArray.getString(R.styleable.TitleBar_tb_title));
        mTxtTitle.setTextColor(typedArray.getColor(R.styleable.TitleBar_tb_title_color,
                                                   defaultColor));
        mTxtTitle.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_title_visible,
                                                      true) ? View.VISIBLE : View.INVISIBLE);

        mVgLeft.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_left_visible,
                                                    true) ? View.VISIBLE : View.GONE);
        mTxtLeft.setText(typedArray.getString(R.styleable.TitleBar_tb_left_text));
        mTxtLeft.setTextColor(typedArray.getColor(R.styleable.TitleBar_tb_left_text_color,
                                                  defaultColor));

        Drawable drawableLeft = typedArray.getDrawable(R.styleable.TitleBar_tb_left_image);
        mImgLeft.setImageDrawable(drawableLeft);


        mImgLeft.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_back_visible,
                                                     true) ? View.VISIBLE : View.GONE);

        int leftMargin = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_left_margin, 0);
        if (leftMargin != 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mVgLeft.getLayoutParams();
            params.leftMargin = leftMargin;
            mVgLeft.setLayoutParams(params);
        }

        mVgRight.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_right_visible,
                                                     false) ? View.VISIBLE : View.GONE);
        mTxtRight.setText(typedArray.getString(R.styleable.TitleBar_tb_right_text));
        mTxtRight.setTextColor(typedArray.getColor(R.styleable.TitleBar_tb_right_text_color,
                                                   defaultColor));
        mImgRight.setImageDrawable(typedArray.getDrawable(R.styleable.TitleBar_tb_right_image));

        int rightMargin = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_right_margin, 0);
        if (rightMargin != 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mVgRight.getLayoutParams();
            params.rightMargin = rightMargin;
            mVgRight.setLayoutParams(params);
        }

        typedArray.recycle();
    }

    public interface Listener {
        void onLeftAreaClick();

        void onRightAreaClick();
    }

    public void setListener(Listener listener) {
        mListener = listener;
        if (mListener != null) {
            mVgLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onLeftAreaClick();
                }
            });
            mVgRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onRightAreaClick();
                }
            });
        } else {
            mVgLeft.setOnClickListener(null);
            mVgRight.setOnClickListener(null);
        }
    }

}
