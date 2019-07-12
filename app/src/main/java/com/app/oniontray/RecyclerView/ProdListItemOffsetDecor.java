package com.app.oniontray.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


public class ProdListItemOffsetDecor extends RecyclerView.ItemDecoration {

    private final int mItemOffset;

    private ProdListItemOffsetDecor(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public ProdListItemOffsetDecor(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        // Left , Top, Right, Bottom

        outRect.set(0, 0, 0, mItemOffset);

    }
}
