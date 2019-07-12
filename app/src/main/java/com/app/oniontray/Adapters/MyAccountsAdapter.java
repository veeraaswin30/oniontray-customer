package com.app.oniontray.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.R;

import java.util.ArrayList;

/**
 * Created by NEXTBRAIN on 2/16/2017.
 */

public class MyAccountsAdapter extends RecyclerView.Adapter<MyAccountsAdapter.MyAccountsViewHolder> {

    private Context context;

    private ArrayList<Drawable> imgArr_res;
    private ArrayList<String> imgArr_name;


    public MyAccountsAdapter(Context context, ArrayList<Drawable> imgArr_res, ArrayList<String> imgArr_name) {
        this.context = context;

        this.imgArr_res = imgArr_res;
        this.imgArr_name = imgArr_name;
    }

    @Override
    public MyAccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAccountsAdapter.MyAccountsViewHolder(LayoutInflater.from(context).inflate(R.layout.my_accounts_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAccountsViewHolder holder, final int position) {



        //Glide.with(context).load(imgArr_res.get(position)).fitCenter().error(R.color.app_background_color).into(holder.my_accounts_row_img_view);



        holder.my_accounts_row_name_txt_view.setText("" + imgArr_name.get(position).toString());
        holder.my_accounts_row_img_view.setImageDrawable(imgArr_res.get(position));

        //holder.my_accounts_row_img_view.setImageResource(imgArr_res.get(position));

        holder.root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myAccountsInterface != null){
                    myAccountsInterface.SelectedAccountsMethod(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return imgArr_res.size();
    }

    public class MyAccountsViewHolder extends RecyclerView.ViewHolder {

        View root_view;
        TextView my_accounts_row_name_txt_view;
        ImageView my_accounts_row_img_view;

        public MyAccountsViewHolder(View itemView) {
            super(itemView);

            root_view = itemView;
            my_accounts_row_name_txt_view = (TextView) itemView.findViewById(R.id.my_accounts_row_name_txt_view);

            my_accounts_row_img_view = (ImageView) itemView.findViewById(R.id.my_accounts_row_img_view);

        }
    }

    public interface MyAccountsInterface {
        void SelectedAccountsMethod(int position);
    }

    private MyAccountsInterface myAccountsInterface;

    public void MyAccountsInterfaceMethod(MyAccountsInterface myAccountsInterface) {
        this.myAccountsInterface = myAccountsInterface;
    }


}
