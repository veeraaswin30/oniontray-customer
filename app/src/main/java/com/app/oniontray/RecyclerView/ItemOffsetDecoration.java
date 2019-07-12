package com.app.oniontray.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private final int mItemOffset;

    private ItemOffsetDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        // Left , Top, Right, Bottom

        if(parent.getChildAdapterPosition(view) == 0){
            outRect.set(0, 0, 0, mItemOffset);
        }else {

            if((parent.getChildAdapterPosition(view)%2)==0){
                // even
                outRect.set(mItemOffset, mItemOffset, mItemOffset*2, mItemOffset);
            }else{
                // odd
                outRect.set(mItemOffset*2, mItemOffset, mItemOffset, mItemOffset);
            }

//            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }

    }
}
