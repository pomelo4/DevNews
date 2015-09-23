package com.pomelo.devnews.view.matchview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.pomelo.devnews.R;
import com.pomelo.devnews.view.matchview.util.MatchView;

/**
 * Description:MatchTextView
 * User: Lj
 * Date: 2014-12-03
 * Time: 10:48
 * FIXME
 */
public class MatchTextView extends MatchView {

    /**
     * 内容
     */
    String mContent;
    float mTextSize;
    int mTextColor;

    public MatchTextView(Context context) {
        super(context);
        init();
    }

    public MatchTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public MatchTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.match);
        //获取尺寸属性值,默认大小为：25
        mTextSize = a.getDimension(R.styleable.match_textSize, 25);
        //获取颜色属性值,默认颜色为：Color.WHITE
        mTextColor = a.getColor(R.styleable.match_textColor, Color.WHITE);
        //获取内容
        mContent = a.getString(R.styleable.match_text);
        init();
    }

    void init() {
        this.setBackgroundColor(Color.TRANSPARENT);
        if (!TextUtils.isEmpty(mContent)) {
            setTextColor(mTextColor);
            setTextSize(mTextSize);
            initWithString(mContent);
            show();
        }
    }


    public void setText(String text) {
        this.mContent = text;
        init();
    }

}
