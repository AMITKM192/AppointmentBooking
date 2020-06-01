package com.amit.appointmentBooking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amit.appointmentBooking.R;
import com.amit.appointmentBooking.models.Recommendservice;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    private List<Recommendservice> recommendservices;

    public PaymentAdapter(Context context, List<Recommendservice> recommendservices) {
        this.recommendservices = recommendservices;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pay_deposite_item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Recommendservice recommendservice = recommendservices.get(position);
        holder.title.setText(recommendservice.getTitle());
    }

    @Override
    public int getItemCount() {
        return recommendservices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        private MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }
    }
}
