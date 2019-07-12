package com.app.oniontray.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


public class HomeMenuStoresItemOffsetDecor extends RecyclerView.ItemDecoration {

    private final int mItemOffset;

    private HomeMenuStoresItemOffsetDecor(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public HomeMenuStoresItemOffsetDecor(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        // Left , Top, Right, Bottom

        if(parent.getChildAdapterPosition(view) == 0){
            outRect.set(0, 0, 0, 0);
        }else {
            outRect.set(0, 0, 0, mItemOffset);

        }

    }
}
