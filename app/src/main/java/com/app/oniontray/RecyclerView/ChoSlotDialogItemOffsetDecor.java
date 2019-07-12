package com.app.oniontray.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


public class ChoSlotDialogItemOffsetDecor extends RecyclerView.ItemDecoration {

    private final int mItemOffset;

    private ChoSlotDialogItemOffsetDecor(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public ChoSlotDialogItemOffsetDecor(@NonNull Context context) {
        this(context.getResources().getDimensionPixelSize(com.app.oniontray.R.dimen.pro_to_check_line_height));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Left , Top, Right, Bottom

        outRect.set(0, 0, 0, mItemOffset);

    }

}
