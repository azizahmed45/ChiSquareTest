package com.mrgreenapps.chisquaretest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ValueListAdapter extends BaseAdapter {

    private Context context;

    private List<Double> observedValueList = new ArrayList<>();
    private List<Double> expectedValueList = new ArrayList<>();

    public ValueListAdapter(Context context){
        this.context = context;
    }

    public ValueListAdapter(Context context, List<Double> observedValueList, List<Double> expectedValueList){
        this.context = context;
        observedValueList = observedValueList;
        expectedValueList = expectedValueList;
    }


    @Override
    public int getCount() {
        return observedValueList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_value_single_value, parent, false);

        TextView serialField = view.findViewById(R.id.serial);
        TextView observedValueField = view.findViewById(R.id.observed_value_field);
        TextView expectedValueField = view.findViewById(R.id.expected_value_field);

        serialField.setText(String.valueOf(position + 1));
        observedValueField.setText(String.valueOf(observedValueList.get(position)));
        expectedValueField.setText(String.valueOf(expectedValueList.get(position)));

        return view;
    }

    public void setItems(List<Double> observedValueList, List<Double> expectedValueList){
        this.observedValueList = observedValueList;
        this.expectedValueList = expectedValueList;

        notifyDataSetChanged();
    }

    public void clearList(){
        observedValueList.clear();
        expectedValueList.clear();

        notifyDataSetChanged();
    }
}
