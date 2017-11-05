package com.sample.qinbaowei.titlebarsample;

import android.app.Activity;
import android.os.Bundle;

import com.qbw.customview.titlebar.TitleBar;

public class MainActivity extends Activity implements TitleBar.Listener {

    private TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setListener(this);
    }

    @Override
    public void onLeftAreaClick() {
        finish();
    }

    @Override
    public void onRightAreaClick() {

    }

    @Override
    public void onCenterAreaClick() {

    }
}
