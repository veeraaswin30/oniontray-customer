package com.app.oniontray.Adapters;

import android.app.Activity;
import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.oniontray.R;



public class ProductDetailAdapter extends PagerAdapter {

    private final Context context;
    private final int[] mResources;
    private ImageView imageView;
    public ProductDetailAdapter(Context context, int[] mResources){
        this.context = context;
        this.mResources = mResources;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.product_detail_slide_img, container, false);

        imageView = (ImageView) viewItem.findViewById(R.id.product_detail_view_pager_imageview);
        imageView.setImageResource(mResources[position]);

        container.addView(viewItem);


        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return mResources.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub

        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView((View) object);
    }

}