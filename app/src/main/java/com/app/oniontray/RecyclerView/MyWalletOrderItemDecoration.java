package com.app.oniontray.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by NEXTBRAIN on 3/12/2017.
 */

public class MyWalletOrderItemDecoration extends RecyclerView.ItemDecoration {

    private final int mItemOffset;

    private MyWalletOrderItemDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public MyWalletOrderItemDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Left , Top, Right, Bottom

        outRect.set(0, 0, 0, mItemOffset);

    }

}
