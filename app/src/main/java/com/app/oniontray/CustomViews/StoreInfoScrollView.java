package com.app.oniontray.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.app.oniontray.Interface.StoreInfoScrollViewListener;

public class StoreInfoScrollView extends ScrollView {

    private StoreInfoScrollViewListener scrollViewListener = null;

    public StoreInfoScrollView(Context context) {
        super(context);
    }

    public StoreInfoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StoreInfoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StoreInfoScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollViewListener(StoreInfoScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}
