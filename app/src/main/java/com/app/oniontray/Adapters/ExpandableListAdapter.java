package com.app.oniontray.Adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.oniontray.R;
import com.app.oniontray.RequestModels.CityLocationList;
import com.app.oniontray.RequestModels.LocationList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    private List<CityLocationList> cityLocationList;
    private List<CityLocationList> searchLoactionArrayList;
    // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    public ExpandableListAdapter(Context context, List<CityLocationList> cityDatumArrayList) {

        this._context = context;
        this.cityLocationList = new ArrayList<>();
        this.cityLocationList.addAll(cityDatumArrayList);
        this.searchLoactionArrayList = new ArrayList<>();
        this.searchLoactionArrayList.addAll(cityDatumArrayList);
    }

    @Override
    public LocationList getChild(int groupPosition, int childPosititon) {
        return this.cityLocationList.get(groupPosition).getLocationList().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_sub_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.ListItem);

        txtListChild.setText(getChild(groupPosition,childPosition).getZoneName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.cityLocationList.get(groupPosition).getLocationList().size();
    }

    @Override
    public CityLocationList getGroup(int groupPosition) {
        return this.cityLocationList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.cityLocationList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
//        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_header, null);

        }
        ImageView indicator = (ImageView)convertView.findViewById(R.id.indicator);

        if(getChildrenCount(groupPosition)== 0)
        {
            indicator.setVisibility(View.GONE);
        }else
        {
            indicator.setVisibility( View.VISIBLE );
            indicator.setImageResource( isExpanded ? R.drawable.ic_home_up_arrow_ic : R.drawable.ic_home_down_arrow_ic );
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.ListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(getGroup(groupPosition).getCityName());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query){

        query = query.toLowerCase();
//        Log.v("MyListAdapter", String.valueOf(cityLocationList.size()));
        cityLocationList.clear();

        if(query.isEmpty()){
            cityLocationList.addAll(searchLoactionArrayList);
        }
        else {

            for(CityLocationList cityList: searchLoactionArrayList){

                List<LocationList> locationlist = cityList.getLocationList();
                ArrayList<LocationList> newList = new ArrayList<>();
                for(LocationList location: locationlist){
                    if(location.getZoneName().toLowerCase().contains(query)){
                        newList.add(location);
                    }
                }
                if(newList.size() > 0){
                    CityLocationList citylocation = new CityLocationList();
                    citylocation.setCityId(cityList.getCityId());
                    citylocation.setCityName(cityList.getCityName());
                    citylocation.setLocationList(newList);
                    cityLocationList.add(citylocation);
                }
            }
        }

//        Log.v("MyListAdapter", String.valueOf(cityLocationList.size()));
        notifyDataSetChanged();

    }
}
