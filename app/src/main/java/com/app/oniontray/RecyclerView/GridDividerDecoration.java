package com.app.oniontray.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.app.oniontray.Models.ProductCategories;
import com.app.oniontray.R;

import java.util.ArrayList;



/**
 * ItemDecoration implementation that applies and inset margin
 * around each child of the RecyclerView. It also draws item dividers
 * that are expected from a vertical list implementation, such as
 * ListView.
 */
public class GridDividerDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = {android.R.attr.listDivider};

    private final Drawable mDivider;
    private final int mInsets;

    private final ArrayList<ProductCategories> productCategoriesArrayList;

    public static boolean timeCondition = false;

//    public GridDividerDecoration(Context context) {
//        TypedArray a = context.obtainStyledAttributes(ATTRS);
//        mDivider = a.getDrawable(0);
//        a.recycle();
//
//        mInsets = context.getResources().getDimensionPixelSize(R.dimen.item_offset);
//    }
//
//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//
//
//        for (int i = 0; i < parent.getChildCount(); i++) {
//            View view = parent.getChildAt(i);
//            int position = parent.getChildAdapterPosition(view);
//            int viewType = parent.getAdapter().getItemViewType(position);
//            if (viewType == 0) {
//
//                drawHorizontal(c, parent);
//
//            }
//            else {
//
//                drawVertical(c, parent);
//                drawHorizontal(c, parent);
//
//            }
//        }
//
//    }
//
//    /** Draw dividers at each expected grid interval */
//    public void drawVertical(Canvas c, RecyclerView parent) {
//        if (parent.getChildCount() == 0) return;
//
//        final int childCount = parent.getChildCount();
//
//        for (int i = 1; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//            final RecyclerView.LayoutParams params =
//                    (RecyclerView.LayoutParams) child.getLayoutParams();
//
//            final int left = child.getLeft() - params.leftMargin - mInsets;
//            final int right = child.getRight() + params.rightMargin + mInsets;
//            final int top = child.getBottom() + params.bottomMargin + mInsets;
//            final int bottom = top + mDivider.getIntrinsicHeight();
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }
//
//    /** Draw dividers to the right of each child view */
//    public void drawHorizontal(Canvas c, RecyclerView parent) {
//        final int childCount = parent.getChildCount();
//
//        for (int i = 1; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//            final RecyclerView.LayoutParams params =
//                    (RecyclerView.LayoutParams) child.getLayoutParams();
//
//            final int left = child.getRight() + params.rightMargin + mInsets;
//            final int right = left + mDivider.getIntrinsicWidth();
//            final int top = child.getTop() - params.topMargin - mInsets;
//            final int bottom = child.getBottom() + params.bottomMargin + mInsets;
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }
//
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        //We can supply forced insets for each item view here in the Rect
////        outRect.set(mInsets, mInsets, mInsets, mInsets);
//
//        int position = parent.getChildAdapterPosition(view);
//        int viewType = parent.getAdapter().getItemViewType(position);
//
//        if(viewType == 0){
////            outRect.set(mDivider.getIntrinsicHeight(),mDivider.getIntrinsicHeight(),mDivider.getIntrinsicHeight(),mDivider.getIntrinsicHeight());
//            outRect.set(mInsets, mInsets, mInsets, mInsets);
//        }else {
////            outRect.set(mDivider.getIntrinsicHeight(),mDivider.getIntrinsicHeight(),mDivider.getIntrinsicHeight(),mDivider.getIntrinsicHeight());
//            outRect.set(mInsets, mInsets, mInsets, mInsets);
//        }
//    }

    public GridDividerDecoration(Context context, ArrayList<ProductCategories> productCategoriesArrayLists) {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();

        productCategoriesArrayList = productCategoriesArrayLists;
        mInsets = context.getResources().getDimensionPixelSize(R.dimen.item_offset);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        for (int i = 0; i < parent.getChildCount(); i++) {

            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            int viewType = parent.getAdapter().getItemViewType(position);

            if(viewType != 0){

                if (productCategoriesArrayList.get(viewType-1).getType() == 0) {

//                    Log.e("viewType Header",""+(viewType-1));

                    drawVertical(c, parent, i);
                    drawHorizontal(c, parent, i);
                } else {
//                    Log.e("viewType Item",""+(viewType-1));
                    drawVerticalType2(c, parent, i);
                    drawHorizontal(c, parent, i);
                }
            }
        }

    }

    /**
     * Draw dividers at each expected grid interval
     */
    private void drawVertical(Canvas c, RecyclerView parent, int position) {
        if (parent.getChildCount() == 0) return;

        final View child = parent.getChildAt(position);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

        final int left1 = child.getLeft() - params.leftMargin - mInsets;
        final int right1 = child.getRight() + params.rightMargin + mInsets;
        final int top1 = child.getTop() - mInsets + mDivider.getIntrinsicHeight();
        final int bottom1 = top1 + mInsets  + 20;

//        Log.e("left1",""+left1);
//        Log.e("right1",""+right1);
//        Log.e("top1",""+top1);
//        Log.e("bottom1",""+bottom1);

        mDivider.setBounds(left1, top1, right1, bottom1);
        mDivider.draw(c);


//        final int left = child.getLeft() - params.leftMargin - mInsets;
//        final int right = child.getRight() + params.rightMargin + mInsets;
//        final int top = child.getBottom() + params.bottomMargin + mInsets;
//        final int bottom = top + mDivider.getIntrinsicHeight();
//
//        mDivider.setBounds(left, top, right, bottom);
//        mDivider.draw(c);

        final int left = child.getLeft() - params.leftMargin - mInsets;
        final int right = child.getRight() + params.rightMargin + mInsets;
        final int top = child.getBottom() + params.topMargin + mInsets + (mInsets / 2);
        final int bottom = top + mDivider.getIntrinsicHeight();

        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(c);
    }

    /**
     * Draw dividers at each expected grid interval
     */
    private void drawVerticalType2(Canvas c, RecyclerView parent, int position) {
        if (parent.getChildCount() == 0) return;

        final View child = parent.getChildAt(position);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

//        final int left1 = child.getLeft() - params.leftMargin - mInsets;
//        final int right1 = child.getRight() + params.rightMargin + mInsets;
//        final int top1 = child.getTop();
//        final int bottom1 = top1 + mInsets + mDivider.getIntrinsicHeight();
//
//        mDivider.setBounds(left1, top1, right1, bottom1);
//        mDivider.draw(c);

        final int left = child.getLeft() - params.leftMargin - mInsets;
        final int right = child.getRight() + params.rightMargin + mInsets;
        final int top = child.getBottom() + params.topMargin + mInsets + (mInsets / 2);
        final int bottom = top + mDivider.getIntrinsicHeight();

        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(c);

    }


    /**
     * Draw dividers to the right of each child view
     */
    private void drawHorizontal(Canvas c, RecyclerView parent, int position) {
        if (parent.getChildCount() == 0) return;

        final View child = parent.getChildAt(position);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

//            final int left1 = child.getRight() + params.rightMargin + mInsets;
//            final int right1 = left1 + mDivider.getIntrinsicWidth();
//            final int top1 = child.getTop();
//            final int bottom1 = top1 + mDivider.getIntrinsicHeight();

//            mDivider.setBounds(left1, top1, right1, bottom1);
//            mDivider.draw(c);

        final int left = child.getRight() + params.rightMargin + mInsets;
        final int right = left + mDivider.getIntrinsicWidth();
        final int top = child.getTop() - params.topMargin;
        final int bottom = child.getBottom() + params.bottomMargin + (mInsets + mInsets);

        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(c);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //We can supply forced insets for each item view here in the Rect
        if(parent.getChildAdapterPosition(view) == 0){
            return;
        }else {
            outRect.set(mInsets, mInsets, mInsets, mInsets);
        }


    }

}