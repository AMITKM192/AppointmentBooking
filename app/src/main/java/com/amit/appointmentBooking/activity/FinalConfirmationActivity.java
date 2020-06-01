package com.amit.appointmentBooking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amit.appointmentBooking.R;

public class FinalConfirmationActivity extends AppCompatActivity {

    private TextView name, phoneno, service, date, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        initViews();
        name.setText(getIntent().getStringExtra("name"));
        phoneno.setText(getIntent().getStringExtra("phone"));
        date.setText(getIntent().getStringExtra("date"));
        service.setText(HomeActivity.diseaseSelected);
        String confirmation = SelectServiceActivity.hospital_name + "," + getIntent().getStringExtra("location");
        details.setText(confirmation);
    }

    private void initViews() {
        name = findViewById(R.id.name);
        phoneno = findViewById(R.id.phone_no);
        service = findViewById(R.id.service);
        date = findViewById(R.id.date);
        details = findViewById(R.id.details);
        Button btn_share = findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Dear " + getIntent().getStringExtra("name") + ", your Appointment for " + HomeActivity.diseaseSelected + " has been booked, Thanks for choosing us";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}