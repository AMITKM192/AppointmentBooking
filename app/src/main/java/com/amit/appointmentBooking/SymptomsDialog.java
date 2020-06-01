package com.amit.appointmentBooking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import com.amit.appointmentBooking.models.Symptoms;

import java.util.List;

public class SymptomsDialog extends Dialog {

    private CheckBox checkBox1, checkBox2, checkBox3;
    private Button done;
    private List<Symptoms> mSymptoms;


    public SymptomsDialog(Context context, List<Symptoms> symptoms) {
        super(context);
        mSymptoms = symptoms;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_symptoms);
        setCancelable(false);
        initViews();
        setdata();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void setdata() {
        checkBox1.setText(mSymptoms.get(0).getSymptom());
        checkBox2.setText(mSymptoms.get(1).getSymptom());
        checkBox3.setText(mSymptoms.get(2).getSymptom());
    }

    private void initViews() {
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        done = findViewById(R.id.done);
    }
}
