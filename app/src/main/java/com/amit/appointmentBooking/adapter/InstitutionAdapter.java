package com.amit.appointmentBooking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amit.appointmentBooking.R;
import com.amit.appointmentBooking.models.Hospital;

import java.util.List;

public class InstitutionAdapter extends RecyclerView.Adapter<InstitutionAdapter.MyViewHolder> {

    private List<Hospital> hospitalList;

    public InstitutionAdapter(List<Hospital> hospitalList) {
        this.hospitalList = hospitalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.institute_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Hospital hospital = hospitalList.get(position);
        holder.name.setText(hospital.getTitle());
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        private MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.txt_institute_name);
        }
    }
}
