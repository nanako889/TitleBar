package com.qbw.customview.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleBar extends FrameLayout implements View.OnClickListener {

    private Listener mListener;

    private View mViewStatus;

    private ViewGroup mVgTitle;

    private TextView mTxtTitle;
    private TextView mTvSubTitle;
    private ViewGroup mVgSubTitleLayout;

    private ViewGroup mVgLeft;
    private TextView mTxtLeft;
    private ImageView mImgLeft;

    private ViewGroup mVgRight;
    private TextView mTxtRight;
    private ImageView mImgRight;

    private View mVBottomLine;

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
        mTvSubTitle = (TextView) view.findViewById(R.id.tv_sub_title);
        mVgSubTitleLayout = (ViewGroup) view.findViewById(R.id.layout_sub_title);
        mVBottomLine = view.findViewById(R.id.v_bottom_line);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);

        Drawable sbg = typedArray.getDrawable(R.styleable.TitleBar_tb_status_background);
        if (sbg != null) {
            mViewStatus.setBackgroundDrawable(sbg);
        }
        mViewStatus.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_status_visible,
                                                        false) ? View.VISIBLE : View.GONE);
        if (!adjustStatusHeight()) {
            int sheight = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_status_height,
                                                           -1);
            if (sheight == -1) {
                sheight = (int) getContext().getResources().getDimension(R.dimen.tb_status_height);
            }
            ViewGroup.LayoutParams paramsSh = mViewStatus.getLayoutParams();
            paramsSh.height = sheight;
            mViewStatus.setLayoutParams(paramsSh);
        }

        Drawable bg = typedArray.getDrawable(R.styleable.TitleBar_tb_backgroupd);
        if (bg != null) {
            view.setBackgroundDrawable(bg);
        }

        int defaultColor = getResources().getColor(android.R.color.white);

        mTxtTitle.setText(typedArray.getString(R.styleable.TitleBar_tb_title));
        mTxtTitle.setTextColor(typedArray.getColor(R.styleable.TitleBar_tb_title_color,
                                                   defaultColor));
        mTxtTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                              typedArray.getDimension(R.styleable.TitleBar_tb_title_textsize, 55));
        mTxtTitle.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_title_visible,
                                                      true) ? View.VISIBLE : View.INVISIBLE);
        boolean isBold = typedArray.getBoolean(R.styleable.TitleBar_tb_title_bold, true);
        if (isBold) {
            mTxtTitle.setTypeface(null, Typeface.BOLD);
        }
        mVgSubTitleLayout.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_sub_title_visible
                , false) ? VISIBLE : GONE);
        mTvSubTitle.setText(typedArray.getString(R.styleable.TitleBar_tb_sub_title));
        int defaultSubTitleColor = Color.parseColor("#999999");
        mTvSubTitle.setTextColor(typedArray.getColor(R.styleable.TitleBar_tb_sub_title_color,
                                                     defaultSubTitleColor));
        mTvSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                typedArray.getDimension(R.styleable.TitleBar_tb_sub_title_size,
                                                        22));
        mVgLeft.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_left_visible,
                                                    false) ? View.VISIBLE : View.GONE);
        mTxtLeft.setText(typedArray.getString(R.styleable.TitleBar_tb_left_text));
        mTxtLeft.setTextColor(typedArray.getColor(R.styleable.TitleBar_tb_left_text_color,
                                                  defaultColor));

        Drawable drawableLeft = typedArray.getDrawable(R.styleable.TitleBar_tb_left_image);
        mImgLeft.setImageDrawable(drawableLeft);
        mImgLeft.setVisibility(typedArray.getBoolean(R.styleable.TitleBar_tb_left_image_visible,
                                                     false) ? View.VISIBLE : View.GONE);

        int leftMargin = typedArray.getDimensionPixelSize(R.styleable.TitleBar_tb_left_margin, 0);
        if (leftMargin != 0) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mVgLeft.getLayoutParams();
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
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mVgRight.getLayoutParams();
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

        boolean showBottomLine = typedArray.getBoolean(R.styleable.TitleBar_tb_bottom_line_visible, false);
        mVBottomLine.setVisibility(showBottomLine ? VISIBLE : GONE);
        int bottomLineColor = typedArray.getColor(R.styleable.TitleBar_tb_bottom_line_color, 0);
        mVBottomLine.setBackgroundColor(bottomLineColor);

        mVgLeft.setOnClickListener(this);
        mTxtLeft.setOnClickListener(this);
        mImgLeft.setOnClickListener(this);
        mVgRight.setOnClickListener(this);
        mTxtRight.setOnClickListener(this);
        mImgRight.setOnClickListener(this);
        mTxtTitle.setOnClickListener(this);

        typedArray.recycle();
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) {
            return;
        }
        long vid = v.getId();
        if (vid == R.id.layout_left || vid == R.id.txt_left || vid == R.id.img_left) {
            mListener.onLeftAreaClick();
        } else if (vid == R.id.layout_right || vid == R.id.txt_right || vid == R.id.img_right) {
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

    public void setRightVisible(boolean b) {
        mVgRight.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    public void setLeftVisible(boolean b) {
        mVgLeft.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    public void setRightTextColor(int color) {
        mTxtRight.setTextColor(color);
    }

    public void setTitle(String title) {
        mTxtTitle.setText(title);
    }

    public void setTitle(int resTitle) {
        mTxtTitle.setText(resTitle);
    }

    public void setSubTitle(String subTitle) {
        mTvSubTitle.setText(subTitle);
    }

    public void setSubTitle(int subTitle) {
        mTvSubTitle.setText(subTitle);
    }

    public void setSubTitleVisible(boolean b) {
        mVgSubTitleLayout.setVisibility(b ? VISIBLE : GONE);
    }

    public boolean adjustStatusHeight() {
        int height = getStatusHeight(getContext());
        if (height != -1) {
            ViewGroup.LayoutParams paramsSh = mViewStatus.getLayoutParams();
            paramsSh.height = height;
            mViewStatus.setLayoutParams(paramsSh);
            return true;
        }
        return false;
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                                               .get(object)
                                               .toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
