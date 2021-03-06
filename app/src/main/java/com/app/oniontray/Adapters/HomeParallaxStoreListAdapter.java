package com.app.oniontray.Adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.StoProdBanner;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class HomeParallaxStoreListAdapter<StoreList_Data> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private float mScrollMultiplier = 0.5f;

    public static class VIEW_TYPES {
        public static final int NORMAL = 1;
        public static final int HEADER = 2;
        public static final int FIRST_VIEW = 3;
        public static final int LISTHEADER = 4;
    }


    public abstract void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, HomeParallaxStoreListAdapter<StoreList_Data> adapter, int i);

    public abstract RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, HomeParallaxStoreListAdapter<StoreList_Data> adapter, int i);

    public abstract int getItemCountImpl(HomeParallaxStoreListAdapter<StoreList_Data> adapter);

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
    private HomeParallaxStoreListAdapter.CustomRelativeWrapper mHeader;
    private HomeParallaxStoreListAdapter.OnClickEvent mOnClickEvent;
    private HomeParallaxStoreListAdapter.OnParallaxScroll mParallaxScroll;
    private RecyclerView mRecyclerView;
    private boolean mShouldClipView = true;
    private Context mContext;


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
    public void setParallaxHeader(View header, final RecyclerView view) {

        mRecyclerView = view;
        mHeader = new HomeParallaxStoreListAdapter.CustomRelativeWrapper(header.getContext(), mShouldClipView);
        mHeader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mHeader.addView(header, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        FrameLayout headerImage = (FrameLayout) header.findViewById(R.id.sto_prod_catego_review_banner_layout);

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

    private void StoreReviewBanner() {

        APIService storeList = Webdata.getRetrofit().create(APIService.class);
        storeList.getStoProdBanner().enqueue(new Callback<StoProdBanner>() {
            @Override
            public void onResponse(Call<StoProdBanner> call, Response<StoProdBanner> response) {
//                Log.e("StoReviewBanner", "onResponse");
            }

            @Override
            public void onFailure(Call<StoProdBanner> call, Throwable t) {
//                Log.e("StoReviewBanner", "onFailure");
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

        if (i == HomeParallaxStoreListAdapter.VIEW_TYPES.HEADER && mHeader != null) {
            return new HomeParallaxStoreListAdapter.ViewHolder(mHeader);
        }

//        if (i == HomeParallaxStoreListAdapter.VIEW_TYPES.LISTHEADER) {
//            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_header, viewGroup, false);
//            return new HomeParallaxStoreListAdapter.ListViewHolder(view);
//        }

        if (i == HomeParallaxStoreListAdapter.VIEW_TYPES.FIRST_VIEW && mHeader != null && mRecyclerView != null) {
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

    public void setOnClickEvent(HomeParallaxStoreListAdapter.OnClickEvent onClickEvent) {
        mOnClickEvent = onClickEvent;
    }


    public boolean isShouldClipView() {
        return mShouldClipView;
    }

    /**
     * Defines if we will clip the layout or not. MUST BE CALLED BEFORE {@link
     * #setParallaxHeader(View, RecyclerView)}
     */
    public void setShouldClipView(boolean shouldClickView) {
        mShouldClipView = shouldClickView;
    }

    public void setOnParallaxScroll(HomeParallaxStoreListAdapter.OnParallaxScroll parallaxScroll) {
        mParallaxScroll = parallaxScroll;
        mParallaxScroll.onParallaxScroll(0, 0, mHeader);
    }

    public HomeParallaxStoreListAdapter(List<StoreList_Data> data, Context context) {
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
            return StoreProdReviewAdapter.VIEW_TYPES.FIRST_VIEW;
//        if (position == 1)
//            return StoreProdReviewAdapter.VIEW_TYPES.LISTHEADER;
        return position == 0 && mHeader != null ? HomeParallaxStoreListAdapter.VIEW_TYPES.HEADER : HomeParallaxStoreListAdapter.VIEW_TYPES.NORMAL;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        public final TextView listHeaderText;

        public ListViewHolder(View itemView) {
            super(itemView);
            listHeaderText = (TextView) itemView.findViewById(R.id.listHeaderText);
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
