package com.qbw.customview.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
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


public class TitleBar extends FrameLayout implements View.OnClickListener {

    private Listener mListener;

    private View mViewStatus;

    private ViewGroup mVgTitle;

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

        mViewStatus = view.findViewById(R.id.view_status);
        mVgTitle = (ViewGroup) view.findViewById(R.id.layout_title);
        mTxtTitle = (TextView) view.findViewById(R.id.txt_title);
        mVgLeft = (ViewGroup) view.findViewById(R.id.layout_left);
        mTxtLeft = (TextView) view.findViewById(R.id.txt_left);
        mImgLeft = (ImageView) view.findViewById(R.id.img_left);
        mVgRight = (ViewGroup) view.findViewById(R.id.layout_right);
        mTxtRight = (TextView) view.findViewById(R.id.txt_right);
        mImgRight = (ImageView) view.findViewById(R.id.img_right);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);

        Drawable sbg = typedArray.getDrawable(R.styleable.TitleBar_tb_status_background);
        if (sbg != null) {
            mViewStatus.setBackgroundDrawable(sbg);
        }
        mViewStatus.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_status_visible,
                                                        false) ? View.VISIBLE : View.GONE);
        int sheight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_status_height, -1);
        if (sheight == -1) {
            sheight = (int) getContext().getResources().getDimension(R.dimen.tb_status_height);
        }
        ViewGroup.LayoutParams paramsSh = mViewStatus.getLayoutParams();
        paramsSh.height = sheight;
        mViewStatus.setLayoutParams(paramsSh);

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
        boolean isBold = typedArray.getBoolean(R.styleable.TitleBar_tb_title_bold, true);
        if (isBold) {
            mTxtTitle.setTypeface(null, Typeface.BOLD);
        }

        mVgLeft.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_left_visible,
                                                    true) ? View.VISIBLE : View.GONE);
        mTxtLeft.setText(typedArray.getString(R.styleable.TitleBar_tb_left_text));
        mTxtLeft.setTextColor(typedArray.getColor(R.styleable.TitleBar_tb_left_text_color,
                                                  defaultColor));

        Drawable drawableLeft = typedArray.getDrawable(R.styleable.TitleBar_tb_left_image);
        mImgLeft.setImageDrawable(drawableLeft);

        int leftMargin = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_left_margin, 0);
        if (leftMargin != 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mVgLeft.getLayoutParams();
            params.leftMargin = leftMargin;
            mVgLeft.setLayoutParams(params);
        }

        mVgRight.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_right_visible,
                                                     true) ? View.VISIBLE : View.GONE);
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
        int theight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_title_height, -1);
        if (theight == -1) {
            theight = (int) getContext().getResources().getDimension(R.dimen.tb_title_height);
        }
        ViewGroup.LayoutParams paramsTh = mVgTitle.getLayoutParams();
        paramsTh.height = theight;
        mVgTitle.setLayoutParams(paramsTh);
        mVgLeft.setMinimumWidth(theight);
        mVgRight.setMinimumWidth(theight);

        if ((leftMargin != 0 || rightMargin != 0) && leftMargin != rightMargin) {
            LinearLayout.LayoutParams paramsT = (LinearLayout.LayoutParams) mTxtTitle.getLayoutParams();
            if (leftMargin > rightMargin) {
                paramsT.rightMargin = leftMargin - rightMargin;
            } else {
                paramsT.leftMargin = rightMargin - leftMargin;
            }
            mTxtTitle.setLayoutParams(paramsT);
        }

        mVgLeft.setOnClickListener(this);
        mVgRight.setOnClickListener(this);
        mTxtTitle.setOnClickListener(this);

        typedArray.recycle();
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) {
            return;
        }
        long vid = v.getId();
        if (vid == R.id.layout_left) {
            mListener.onLeftAreaClick();
        } else if (vid == R.id.layout_right) {
            mListener.onRightAreaClick();
        } else if (vid == R.id.txt_title) {
            mListener.onCenterAreaClick();
        }
    }

    public interface Listener {
        void onLeftAreaClick();

        void onRightAreaClick();

        void onCenterAreaClick();
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

}
