package com.app.oniontray.Adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.app.oniontray.R;

import java.util.ArrayList;
import java.util.List;

public abstract class ParallaxRecyclerAdapterForStoreList<StoreList_Data> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private float mScrollMultiplier = 0.5f;
    private final Context mContext;

    public static class VIEW_TYPES {
        public static final int NORMAL = 1;
        public static final int HEADER = 2;
        public static final int FIRST_VIEW = 3;
    }


    public abstract void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapterForStoreList<StoreList_Data> adapter, int i);

    public abstract RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapterForStoreList<StoreList_Data> adapter, int i);

    public abstract int getItemCountImpl(ParallaxRecyclerAdapterForStoreList<StoreList_Data> adapter);

    public interface OnClickEvent {
        /**
         * Event triggered when you click on a item of the adapter
         *
         * @param v        view
         * @param position position on the array
         */
        void onClick(View v, int position);
    }

    public interface OnParallaxScroll {
        /**
         * Event triggered when the parallax is being scrolled.
         */
        void onParallaxScroll(float percentage, float offset, View parallax);
    }

    private List<StoreList_Data> mData;
    private CustomRelativeWrapper mHeader;
    private OnClickEvent mOnClickEvent;
    private OnParallaxScroll mParallaxScroll;
    private RecyclerView mRecyclerView;
    private boolean mShouldClipView = true;

    /**
     * Translates the adapter in Y
     *
     * @param of offset in px
     */
    private void translateHeader(float of) {
        float ofCalculated = of * mScrollMultiplier;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && of < mHeader.getHeight()) {
            mHeader.setTranslationY(ofCalculated);
        } else if (of < mHeader.getHeight()) {
            TranslateAnimation anim = new TranslateAnimation(0, 0, ofCalculated, ofCalculated);
            anim.setFillAfter(true);
            anim.setDuration(0);
            mHeader.startAnimation(anim);
        }
        mHeader.setClipY(Math.round(ofCalculated));
        if (mParallaxScroll != null) {
            final RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(0);
            float left;
            if (holder != null) {
                left = Math.min(1, ((ofCalculated) / (mHeader.getHeight() * mScrollMultiplier)));
            } else {
                left = 1;
            }
            mParallaxScroll.onParallaxScroll(left, of, mHeader);
        }
    }

    /**
     * Set the view as store_listing_header.
     *
     * @param header The inflated store_listing_header
     * @param view   The RecyclerView to set scroll listeners
     */
    public void setParallaxHeader(View header, final RecyclerView view, String imagePath) {

        mRecyclerView = view;
        mHeader = new CustomRelativeWrapper(header.getContext(), mShouldClipView);
        mHeader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mHeader.addView(header, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ImageView headerImage = (ImageView) header.findViewById(R.id.sto_list_banner_img);

        if(imagePath.isEmpty()){
/*
            Glide.with(mContext).load(R.drawable.temp_sto_img).into(headerImage);
*/
        }else{
            Glide.with(mContext).load(""+imagePath).into(headerImage);
        }

        view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeader != null) {
                    translateHeader(mRecyclerView.getLayoutManager().getChildAt(0) == mHeader ?
                            mRecyclerView.computeVerticalScrollOffset() : mHeader.getHeight());
                }
            }
        });

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (mHeader != null) {
            if (i == 0) {
                return;
            }
            onBindViewHolderImpl(viewHolder, this, i - 1);
        } else {
            onBindViewHolderImpl(viewHolder, this, i);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        if (i == VIEW_TYPES.HEADER && mHeader != null) {

            return new ViewHolder(mHeader);
        }
        if (i == VIEW_TYPES.FIRST_VIEW && mHeader != null && mRecyclerView != null) {
            final RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(0);
            if (holder != null) {
                translateHeader(-holder.itemView.getTop());
            }
        }
        final RecyclerView.ViewHolder holder = onCreateViewHolderImpl(viewGroup, this, i);
        if (mOnClickEvent != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickEvent.onClick(v, holder.getAdapterPosition() - (mHeader == null ? 0 : 1));
                }
            });
        }
        return holder;
    }

    /**
     * @return true if there is a store_listing_header on this adapter, false otherwise
     */
    public boolean hasHeader() {
        return mHeader != null;
    }

    public void setOnClickEvent(OnClickEvent onClickEvent) {
        mOnClickEvent = onClickEvent;
    }


    public boolean isShouldClipView() {
        return mShouldClipView;
    }

    /**
     * Defines if we will clip the layout or not. MUST BE CALLED BEFORE {@link
     * #setParallaxHeader(View, RecyclerView, String)}
     */
    public void setShouldClipView(boolean shouldClickView) {
        mShouldClipView = shouldClickView;
    }

    public void setOnParallaxScroll(OnParallaxScroll parallaxScroll) {
        mParallaxScroll = parallaxScroll;
        mParallaxScroll.onParallaxScroll(0, 0, mHeader);
    }

    public ParallaxRecyclerAdapterForStoreList(List<StoreList_Data> data, Context context) {
        mData = data;
        mContext = context;
    }


    public List<StoreList_Data> getData() {
        return mData;
    }

    public void setData(List<StoreList_Data> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return this.mData.get(position);
    }

    public void addItem(StoreList_Data item, int position) {
        mData.add(position, item);
        notifyItemInserted(position + (mHeader == null ? 0 : 1));
    }

    public void removeItem(StoreList_Data item) {
        int position = mData.indexOf(item);
        if (position < 0)
            return;
        mData.remove(item);
        notifyItemRemoved(position + (mHeader == null ? 0 : 1));
    }


    public int getItemCount() {
        return getItemCountImpl(this) + (mHeader == null ? 0 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1)
            return VIEW_TYPES.FIRST_VIEW;
        return position == 0 && mHeader != null ? VIEW_TYPES.HEADER : VIEW_TYPES.NORMAL;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    static class CustomRelativeWrapper extends RelativeLayout {

        private int mOffset;
        private final boolean mShouldClip;

        public CustomRelativeWrapper(Context context, boolean shouldClick) {
            super(context);
            mShouldClip = shouldClick;
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            if (mShouldClip) {
                canvas.clipRect(new Rect(getLeft(), getTop(), getRight(), getBottom() + mOffset));
            }
            super.dispatchDraw(canvas);
        }

        public void setClipY(int offset) {
            mOffset = offset;
            invalidate();
        }
    }

    /**
     * Set parallax scroll multiplier.
     *
     * @param mul The multiplier
     */
    public void setScrollMultiplier(float mul) {
        this.mScrollMultiplier = mul;
    }

    /**
     * Get the current parallax scroll multiplier.
     */
    public float getScrollMultiplier() {
        return this.mScrollMultiplier;
    }

    public void setFilter(List<StoreList_Data> filterdata) {
        mData = new ArrayList<>();
        mData.addAll(filterdata);
        notifyDataSetChanged();
    }

}
