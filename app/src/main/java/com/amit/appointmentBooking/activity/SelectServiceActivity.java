package com.amit.appointmentBooking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amit.appointmentBooking.R;
import com.amit.appointmentBooking.models.Hospital;
import com.amit.appointmentBooking.models.Option;
import com.razorpay.PaymentResultListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectServiceActivity extends AppCompatActivity implements PaymentResultListener, PatientDetailDialog.DataTraveller {

    static String hospital_name;
    private ArrayList<Option> optionList;
    private TextView totalAmount;
    private double amount;
    private String selectedService;
    private String name;
    private String phone;
    private String date;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Select services");
        setContentView(R.layout.activity_select_service);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Hospital hospital = (Hospital) getIntent().getSerializableExtra("service");
        selectedService = getIntent().getStringExtra("selectedservice");
        int selectedPos = getIntent().getIntExtra("selectedpos", 0);
        optionList = hospital.getOptions();
        hospital_name = hospital.getTitle();


        LinearLayout checkBoxLayout = findViewById(R.id.lyt_checkBox);
        totalAmount = findViewById(R.id.totalAmount);
        TextView serviceDescription = findViewById(R.id.txt_service_info);
        Button btn_proceed = findViewById(R.id.btn_proceed);
        if (optionList.get(selectedPos).getDescription() != null && optionList.get(selectedPos).getDescription().trim().length() > 0)
            serviceDescription.setText(optionList.get(selectedPos).getDescription());
        else
            serviceDescription.setText(getResources().getString(R.string.content_not_available));
        otherServices();

        amount = hospital.getPrice();
        totalAmount.setText(getResources().getString(R.string.total_amount) + amount);
        for (int i = 0; i < optionList.size(); i++) {
            final CheckBox checkBox = new CheckBox(this);
            checkBox.setTextSize(20);
            checkBox.setId(i);
            checkBox.setText(optionList.get(i).getTitle() + " : " + optionList.get(i).getPrice() + " Rs.");
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int id = checkBox.getId();
                    double price = optionList.get(id).getPrice();
                    if (isChecked) {
                        amount += price;
                    } else {
                        amount -= price;
                    }
                    totalAmount.setText(getResources().getString(R.string.total_amount) + amount);
                }
            });
            checkBoxLayout.addView(checkBox);
        }

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientDetailDialog activity = new PatientDetailDialog(SelectServiceActivity.this, amount);
                activity.show();
                activity.setCancelable(false);

            }
        });
    }

    private void otherServices() {
        for (Iterator<Option> it = optionList.iterator(); it.hasNext(); ) {
            Option model = it.next();
            if (model.getTitle().equals(selectedService)) {
                it.remove();
                break;
            }
        }
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

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, razorpayPaymentID, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, FinalConfirmationActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("date", date);
        intent.putExtra("location", location);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, code + " " + response, Toast.LENGTH_LONG).show();
    }

    @Override
    public void passData(String name, String phone, String date, String location) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.location = location;
    }
}
