package com.app.oniontray.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.app.oniontray.Activites.AddAddressActivity;
import com.app.oniontray.LocalizationActivity.LanguageSetting;


public class FavListItemOffsetDecor extends RecyclerView.ItemDecoration {

    private final int mItemOffset;
    private Context context;

    private FavListItemOffsetDecor(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public FavListItemOffsetDecor(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
        this.context=context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        String language = String.valueOf(LanguageSetting.getLanguage(context));


        if (language.equals("en")) {

            // Left , Top, Right, Bottom

            if(parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1){

                if((parent.getChildAdapterPosition(view)%2) == 0){
                    // even
                    outRect.set(mItemOffset*2, mItemOffset*2, mItemOffset, mItemOffset);
                }else{
                    // odd
                    outRect.set(mItemOffset, mItemOffset*2, mItemOffset*2, mItemOffset);
                }

            }else {

                if((parent.getChildAdapterPosition(view)%2) == 0){
                    // even
                    outRect.set(mItemOffset*2, mItemOffset, mItemOffset, mItemOffset);
                }else{
                    // odd
                    outRect.set(mItemOffset, mItemOffset, mItemOffset*2, mItemOffset);
                }

            }

        }else {

            // Left , Top, Right, Bottom

            if(parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1){

                if((parent.getChildAdapterPosition(view)%2) == 0){
                    // even
                    outRect.set(mItemOffset, mItemOffset*2, mItemOffset*2, mItemOffset);
                }else{
                    // odd
                    outRect.set(mItemOffset*2, mItemOffset*2, mItemOffset, mItemOffset);
                }

            }else {

                if((parent.getChildAdapterPosition(view)%2) == 0){
                    // even
                    outRect.set(mItemOffset, mItemOffset, mItemOffset*2, mItemOffset);
                }else{
                    // odd
                    outRect.set(mItemOffset*2, mItemOffset, mItemOffset, mItemOffset);
                }

            }

        }





    }
}
