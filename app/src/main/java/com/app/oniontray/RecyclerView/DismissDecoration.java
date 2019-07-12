package com.app.oniontray.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @author David Medenjak on 3/4/2016.
 */
class DismissDecoration extends RecyclerView.ItemDecoration {
    private final int mColor;
    private final Bitmap mIcon;
    private final float mDensity;

    public DismissDecoration(@ColorInt int color, Bitmap icon, float density) {
        mColor = color;
        mIcon = icon;
        mDensity = density;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // no offset, just drawing background
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View view = parent.getChildAt(i);

            float translationX = view.getTranslationX();
            if (translationX == 0) {
                continue;
            }

            final int save = c.save();
            final int xpos;
            final int ypos = (int) (view.getTop() + view.getTranslationY() + (view.getHeight() / 2 - mIcon.getHeight() / 2));
            if (translationX < 0) {
                c.clipRect(view.getRight() + view.getTranslationX(),
                        view.getTop() + view.getTranslationY(),
                        view.getRight(),
                        view.getBottom() + view.getTranslationY());
                xpos = (int) (view.getRight() - 36 * mDensity - mIcon.getWidth() / 2);
            } else {
                c.clipRect(view.getLeft() + view.getTranslationX(),
                        view.getTop() + view.getTranslationY(),
                        view.getLeft(),
                        view.getBottom() + view.getTranslationY());
                xpos = (int) (view.getLeft() + 36 * mDensity - mIcon.getWidth() / 2);
            }
            c.drawColor(mColor);
            Paint color = new Paint();
            color.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));
            c.drawBitmap(mIcon, xpos, ypos, color);
            c.restoreToCount(save);
        }
    }
}
