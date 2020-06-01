package com.amit.appointmentBooking.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.amit.appointmentBooking.R;
import com.amit.appointmentBooking.Utility;
import com.amit.appointmentBooking.adapter.PaymentAdapter;
import com.amit.appointmentBooking.models.Recommendation;
import com.amit.appointmentBooking.models.Recommendservice;
import com.razorpay.Checkout;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

class PatientDetailDialog extends Dialog {

    private final Context context;
    private List<String> locationList = new ArrayList<>();
    private List<Recommendation> recommendationList;
    private List<Recommendservice> recommendserviceArrayList = new ArrayList<>();
    private Gson gson = new Gson();

    private RecyclerView recommend_rv;
    private String service = "";
    private String[] gender = {"Male", "Female"};

    private EditText name;
    private EditText phone;
    private EditText date;
    private Calendar myCalendar = Calendar.getInstance();

    private double amount;
    private String location;
    private DataTraveller traveller;

    public PatientDetailDialog(@NonNull Context context, double amount) {
        super(context);
        this.context = context;
        this.amount = amount;
        traveller = (DataTraveller) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        setTitle("Patient Detail");
        service = HomeActivity.diseaseSelected;

        MaterialBetterSpinner location_spinner = findViewById(R.id.location_spinner);
        MaterialBetterSpinner gender_spinner = findViewById(R.id.gender_spinner);
        recommend_rv = findViewById(R.id.recommend_rv);


        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        EditText age = findViewById(R.id.age);
        EditText email = findViewById(R.id.email);
        Button paynow = findViewById(R.id.paynow);
        Button cancel = findViewById(R.id.cancel);
        date = findViewById(R.id.date);

        final String data = Utility.getLocalData(context, "suggestions.json");

        final Type type = new TypeToken<List<Recommendation>>() {
        }.getType();
        recommendationList = gson.fromJson(data, type);

        for (int i = 0; i < recommendationList.size(); i++) {
            locationList.add(recommendationList.get(i).getLocation());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, locationList);
        location_spinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, gender);
        gender_spinner.setAdapter(genderAdapter);

        location_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recommendserviceArrayList.clear();
                location = locationList.get(position);
                for (int i = 0; i < recommendationList.size(); i++) {
                    if (location.equals(recommendationList.get(i).getLocation())) {
                        for (int k = 0; k < recommendationList.get(i).getRecommendservice().size(); k++) {
                            if (service.equals(recommendationList.get(i).getRecommendservice().get(k).getType())) {
                                recommendserviceArrayList.add(recommendationList.get(i).getRecommendservice().get(k));
                            }
                        }
                    }
                }

                PaymentAdapter adapter = new PaymentAdapter(context, recommendserviceArrayList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                recommend_rv.setLayoutManager(mLayoutManager);
                recommend_rv.setItemAnimator(new DefaultItemAnimator());
                recommend_rv.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

                recommend_rv.setAdapter(adapter);
                recommend_rv.setVisibility(View.VISIBLE);
            }
        });

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkforEmptyString(name.getText().toString()) && checkforEmptyString(phone.getText().toString()) &&
                        checkforEmptyString(date.getText().toString()) && checkforEmptyString(location)) {
                    traveller.passData(name.getText().toString(), phone.getText().toString(), date.getText().toString(), location);
                    startPayment(name.getText().toString());
                } else {
                    Toast.makeText(context, "Kindly provide all details", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog;
                dialog = new DatePickerDialog(context, dateSelector, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener dateSelector = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            view.setMinDate(System.currentTimeMillis() - 1000);
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            date.setText(sdf.format(myCalendar.getTime()));
        }

    };

    private void startPayment(String name) {

        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.mindtree);
        final Activity activity = (Activity) context;
        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", "Order #US_" + phone.getText().toString());
            options.put("currency", "INR");
            options.put("amount", amount * 100);
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(activity.getLocalClassName(), "Error in starting Razorpay Checkout", e);
        }
    }

    private boolean checkforEmptyString(String edttext) {
        return edttext != null && !edttext.equals("") && edttext.length() != 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    interface DataTraveller {
        void passData(String name, String phone, String date, String location);
    }
}