package com.app.oniontray.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.AddressList;
import com.app.oniontray.RequestModels.DeleteAddress;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NEXTBRAIN on 3/13/2017.
 */

public class PicMyLocAddressAdapter extends RecyclerView.Adapter<PicMyLocAddressAdapter.MyLocAddrViewHolder> {

    private Context context;

    private ArrayList<AddressList> addressLists;

    private LoginPrefManager loginPrefManager;
    private ProgressDialog progressDialog;

    public PicMyLocAddressAdapter(Context context, ArrayList<AddressList> addressLists) {
        this.context = context;
        this.addressLists = addressLists;

        this.loginPrefManager = new LoginPrefManager(context);
        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    public MyLocAddrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicMyLocAddressAdapter.MyLocAddrViewHolder(LayoutInflater.from(context).inflate(R.layout.pic_my_loc_addr_addapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyLocAddrViewHolder holder, final int position) {

//        Log.e("getAddressType", "" + addressLists.get(position).getAddressType());

        holder.my_address_type_txt_view.setText("" + addressLists.get(position).getAddressType().trim());
        holder.my_addres_text_view.setText("" + addressLists.get(position).getAddress().trim());

        holder.my_addres_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(picMyLocAddressAdapterInterface != null){
                    picMyLocAddressAdapterInterface.PicMyLocClickEventInterface(addressLists.get(position));
                }

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(picMyLocAddressAdapterInterface != null){
                    picMyLocAddressAdapterInterface.PicMyLocClickEventInterface(addressLists.get(position));
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return addressLists.size();
    }


    public void UpdateMyAddressAdapter(ArrayList<AddressList> addressLists) {
        this.addressLists = addressLists;
        notifyDataSetChanged();
    }

    public class MyLocAddrViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout my_addr_delete_icon_layout;

        private TextView my_address_type_txt_view;

        private TextView my_addres_text_view;

        private ImageView my_addres_delete_ic;

        private LinearLayout pic_my_loc_list_addr_item_lay;

        private View itemView;

        public MyLocAddrViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;

            this.my_addr_delete_icon_layout = (LinearLayout) itemView.findViewById(R.id.my_addr_delete_icon_layout);
            this.my_addr_delete_icon_layout.setVisibility(View.GONE);

            this.my_address_type_txt_view = (TextView) itemView.findViewById(R.id.inputradiovalue);

            this.my_addres_text_view = (TextView) itemView.findViewById(R.id.inputPersonName);

            this.my_addres_delete_ic = (ImageView) itemView.findViewById(R.id.input_add_del);

            this.pic_my_loc_list_addr_item_lay = (LinearLayout) itemView.findViewById(R.id.pic_my_loc_list_addr_item_lay);

        }
    }


    private void DeleteAddressConfirmationDialog(final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + context.getString(R.string.message));

        alertDialog.setMessage("" + context.getString(R.string.err_msg_delete_address_confirm_txt));

        alertDialog.setNegativeButton("" + context.getString(R.string.cancel_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("" + context.getString(R.string.ok_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ConfirmDeleteAddress(position);
                    }
                });
        alertDialog.show();

    }

    private void ConfirmDeleteAddress(final int position) {

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService deleteadd = Webdata.getRetrofit().create(APIService.class);
        deleteadd.delete_address(loginPrefManager.getStringValue("Lang_code"), loginPrefManager.getStringValue("user_id"),
                String.valueOf(addressLists.get(position).getAddressId())).enqueue(new Callback<DeleteAddress>() {
            @Override
            public void onResponse(Call<DeleteAddress> call, Response<DeleteAddress> response) {
                progressDialog.dismiss();
                if (response.body().getResponse().getHttpCode() == 200) {
                    Log.e("response", response.body().getResponse().getMessage());
                    addressLists.remove(position);
                    notifyDataSetChanged();



                } else if (response.body().getResponse().getHttpCode() == 400) {
                    Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAddress> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error", t.toString());
            }
        });

    }


    public PicMyLocAddressAdapterInterface picMyLocAddressAdapterInterface;

    public void PicMyLocAddrAddapterInterfaceMethod(PicMyLocAddressAdapterInterface picMyLocAddressAdapterInterface){
        this.picMyLocAddressAdapterInterface = picMyLocAddressAdapterInterface;
    }

    public interface PicMyLocAddressAdapterInterface{
        void PicMyLocAddEmptyViewInterface(int array_size);
        void PicMyLocClickEventInterface(AddressList addressList);
    }


}
