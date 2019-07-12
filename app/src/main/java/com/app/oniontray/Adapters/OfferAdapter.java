package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.Offer;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    private final ArrayList<Offer> Offers;

    public OfferAdapter(Context context, ArrayList<Offer> Offers) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.Offers = Offers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.offers_page_row_item_lay, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Glide.with(context).load(Offers.get(position).getCouponImage()).fitCenter().bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 5, 0)).error(R.color.app_background_color).into(holder._Offers_image);

        holder._Offers_Store_text.setText(Offers.get(position).getCouponTitle().substring(0, 1).toUpperCase() + Offers.get(position).getCouponTitle().substring(1));
        holder._Offers_Valid.setText(""+context.getString(R.string.promo_code_txt)+" "+Offers.get(position).getCouponCode());


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd");


//        Log.e("end dte",Offers.get(position).getEndDate());

        try {
            holder._Offers_validity.setText(context.getResources().getString(R.string.available_till)+ " " +dateFormat.format(dateParse.parse(Offers.get(position).getEndDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return Offers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView _Offers_image;

        final TextView _Offers_Store_text;
        final TextView _Offers_Valid,_Offers_validity;

        public MyViewHolder(final View itemView) {
            super(itemView);

            _Offers_image = (ImageView) itemView.findViewById(R.id.offers_load_image);

            _Offers_Store_text = (TextView) itemView.findViewById(R.id.offers_store_name);
            _Offers_Valid = (TextView) itemView.findViewById(R.id.offers_valid_promo_code);
            _Offers_validity = (TextView) itemView.findViewById(R.id.offers_validity);

        }

    }
}
