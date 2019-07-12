package com.app.oniontray.CustomViews;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;


class ReSizeAnimationView extends Animation {

    private final boolean fun;
    private final int mFromHeight;
    private final int mToHeight;
    private final View mView;

    public ReSizeAnimationView(View view, int fromHeight, int toHeight, boolean function) {
        this.mView = view;
        this.mFromHeight = fromHeight;
        this.mToHeight = toHeight;
        this.fun = function;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        int newHeight;

//        Log.e("mView.getHeight(false)",""+mView.getHeight());
//        Log.e("mToHeight",""+mToHeight);

        if (mView.getHeight() != mToHeight && !fun) {
//            Log.e("mView.getHeight(false)",""+mView.getHeight());
            newHeight = (int) (mFromHeight + ((mToHeight - mFromHeight) * interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }else if(mView.getHeight() != mToHeight && fun){

//            Log.e("mView.getHeight(true)",""+mView.getHeight());
//            Log.e("mFromHeight",""+mFromHeight);
//            Log.e("mToHeight",""+mToHeight);
//            Log.e("interpolatedTime1",""+interpolatedTime);
//            Log.e("interpolatedTime2",""+(1-interpolatedTime));
//            Log.e("mFrom*interpo",""+(mToHeight/5 -(mFromHeight * (1-interpolatedTime))));
//            Log.e("mto*interpo",""+(mFromHeight/5 - (mToHeight * (1-interpolatedTime))));

            newHeight = (int) (mFromHeight + ((mToHeight - mFromHeight) * (1-interpolatedTime)));
//            newHeight = (int)(mToHeight/5 - (mFromHeight * (1-interpolatedTime)));
//            Log.e("newHeight(true)",""+newHeight);
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }

    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
