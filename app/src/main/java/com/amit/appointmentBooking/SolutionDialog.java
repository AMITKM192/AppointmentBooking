package com.amit.appointmentBooking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amit.appointmentBooking.models.Solutions;

import java.util.List;

public class SolutionDialog extends Dialog {

    private List<Solutions> mSolutions;
    private TextView solution1, solution2;
    private Button done;


    public SolutionDialog(Context context, List<Solutions> solutions) {
        super(context);
        mSolutions = solutions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solution_dialog);
        setCancelable(false);
        initViews();
        solution1.setText(mSolutions.get(0).getSolution());
        solution2.setText(mSolutions.get(1).getSolution());
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initViews() {
        solution1 = findViewById(R.id.solution1);
        solution2 = findViewById(R.id.solution2);
        done = findViewById(R.id.done);
    }
}