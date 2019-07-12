package com.app.oniontray.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
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


public class AddressAdapter extends BaseAdapter {

    private final LayoutInflater inflater;

    private final Context context;

    private final ArrayList<AddressList> addresses;
    private LoginPrefManager preferenceAccess;
    private DDProgressBarDialog progress;


    public AddressAdapter(Context applicationContext, ArrayList<AddressList> addresses) {
        this.context = applicationContext;
        inflater = LayoutInflater.from(this.context);
        this.addresses = addresses;

    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {

        public TextView radio_text, input_name_text;
        ImageView delete_img;
    }

    /******
     * Depends upon data size called for each row , Create each ListView row
     *****/
    public View getView(final int position, View convertView, ViewGroup parent) {

        View addView = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            addView = inflater.inflate(R.layout.address_listing_row_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.radio_text = (TextView) addView.findViewById(R.id.inputradiovalue);
            holder.input_name_text = (TextView) addView.findViewById(R.id.inputPersonName);
            holder.delete_img = (ImageView) addView.findViewById(R.id.input_add_del);

            /************  Set holder with LayoutInflater ************/
            addView.setTag(holder);
        } else {
            holder = (ViewHolder) addView.getTag();
        }

        holder.radio_text.setText(addresses.get(position).getAddressType());
        holder.input_name_text.setText(addresses.get(position).getAddress());
        preferenceAccess = new LoginPrefManager(context);
        progress = new DDProgressBarDialog(context);

        holder.delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAddressConfirmationDialog(position);
            }
        });

        return addView;
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


    private void ConfirmDeleteAddress(final int position){

        if (progress != null) {
            progress.show();
        }

        APIService deleteadd = Webdata.getRetrofit().create(APIService.class);
        deleteadd.delete_address(preferenceAccess.getStringValue("Lang_code"), preferenceAccess.getStringValue("user_id"), String.valueOf(addresses.get(position).getAddressId())).enqueue(new Callback<DeleteAddress>() {
            @Override
            public void onResponse(Call<DeleteAddress> call, Response<DeleteAddress> response) {
                progress.dismiss();
                if (response.body().getResponse().getHttpCode() == 200) {
//                    Log.e("response", response.body().getResponse().getMessage());
                    addresses.remove(position);
                    notifyDataSetChanged();
                } else if (response.body().getResponse().getHttpCode() == 400) {
                    Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAddress> call, Throwable t) {
                progress.dismiss();
//                Log.e("error", t.toString());
            }
        });

    }



}



