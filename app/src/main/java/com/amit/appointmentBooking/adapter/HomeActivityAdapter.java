package com.amit.appointmentBooking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amit.appointmentBooking.R;
import com.amit.appointmentBooking.models.DiseaseDetailsModel;

import java.util.ArrayList;

public class HomeActivityAdapter extends RecyclerView.Adapter<HomeActivityAdapter.MyViewHolder> {

    private ArrayList<DiseaseDetailsModel> diseaseDetailsModelList;

    public HomeActivityAdapter(ArrayList<DiseaseDetailsModel> diseaseDetailsModelList) {
        this.diseaseDetailsModelList = diseaseDetailsModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DiseaseDetailsModel diseaseDetailsModel = diseaseDetailsModelList.get(position);
        holder.title.setText(diseaseDetailsModel.getTitle());
        holder.description.setText(diseaseDetailsModel.getShort_description());
    }

    @Override
    public int getItemCount() {
        return diseaseDetailsModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.txt_header);
            description = view.findViewById(R.id.txt_description);
        }
    }
}
