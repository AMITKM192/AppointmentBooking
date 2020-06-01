package com.amit.appointmentBooking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.amit.appointmentBooking.R;
import com.amit.appointmentBooking.Utility;
import com.amit.appointmentBooking.adapter.HomeActivityAdapter;
import com.amit.appointmentBooking.listener.RecyclerTouchListener;
import com.amit.appointmentBooking.models.DiseaseDetailsModel;
import com.razorpay.Checkout;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public static String diseaseSelected;
    private ArrayList<DiseaseDetailsModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(getApplicationContext());
        setContentView(R.layout.activity_home);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Gson gson = new Gson();
        String data = Utility.getLocalData(this, "mockdata.json");

        Type type = new TypeToken<ArrayList<DiseaseDetailsModel>>() {
        }.getType();
        dataList = gson.fromJson(data, type);
        HomeActivityAdapter adapter = new HomeActivityAdapter(dataList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DiseaseDetailsModel diseaseDetailsModel = dataList.get(position);
                diseaseSelected = diseaseDetailsModel.getTitle();
                Intent intent = new Intent(HomeActivity.this, DetailPageActivity.class);
                intent.putExtra("selected_data", diseaseDetailsModel);
                startActivity(intent);
            }
        }));
    }
}
