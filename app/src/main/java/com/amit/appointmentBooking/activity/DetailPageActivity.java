package com.amit.appointmentBooking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amit.appointmentBooking.R;
import com.amit.appointmentBooking.SolutionDialog;
import com.amit.appointmentBooking.SymptomsDialog;
import com.amit.appointmentBooking.adapter.InstitutionAdapter;
import com.amit.appointmentBooking.listener.RecyclerTouchListener;
import com.amit.appointmentBooking.models.Hospital;
import com.amit.appointmentBooking.models.DiseaseDetailsModel;
import com.amit.appointmentBooking.models.Option;
import com.amit.appointmentBooking.models.Solutions;
import com.amit.appointmentBooking.models.Symptoms;

import java.util.ArrayList;
import java.util.List;

public class DetailPageActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<Hospital> hospitalList;
    private List<Symptoms> symptomsList;
    private ArrayList<Solutions> solutionsList;
    private TextView description;
    private TextView hospital_description;
    private TextView txt_hospital_name;
    private CheckBox facility1;
    private CheckBox facility2;
    private String selectedFacility = "";
    private int selectedPos = -1;
    private Hospital hospital;
    private RelativeLayout lyt_below;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DiseaseDetailsModel diseaseDetailsModel = (DiseaseDetailsModel) getIntent().getSerializableExtra("selected_data");
        setTitle(diseaseDetailsModel.getTitle());
        initViews();

        hospitalList = diseaseDetailsModel.getHospitals();
        description.setText(diseaseDetailsModel.getLong_description());
        symptomsList = diseaseDetailsModel.getSymptoms();
        solutionsList = diseaseDetailsModel.getSolutions();
        InstitutionAdapter adapter = new InstitutionAdapter(hospitalList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                hospital = hospitalList.get(position);
                setData(hospital);
                lyt_below.setVisibility(View.VISIBLE);
                facility1.setChecked(false);
                facility2.setChecked(false);
                Animation animation = AnimationUtils.loadAnimation(DetailPageActivity.this, R.anim.slide_down);
                lyt_below.startAnimation(animation);
            }
        }));

        facility1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectedFacility = facility1.getText().toString();
                    selectedPos = 0;
                } else {
                    selectedFacility = "";
                    selectedPos = -1;
                }
            }
        });

        facility2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectedFacility = facility2.getText().toString();
                    selectedPos = 1;
                } else {
                    selectedFacility = "";
                    selectedPos = -1;
                }
            }
        });
    }

    private void setData(Hospital hospital) {
        ArrayList<Option> optionList = hospital.getOptions();

        hospital_description.setText(hospital.getDescription());
        txt_hospital_name.setText(hospital.getTitle());

        facility1.setText(optionList.get(0).getTitle());
        facility2.setText(optionList.get(1).getTitle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        lyt_below.setVisibility(View.GONE);
    }

    private void initViews() {

        lyt_below = findViewById(R.id.lyt_below);
        description = findViewById(R.id.detail_description);
        TextView txt_symtoms = findViewById(R.id.txt_symtoms);
        TextView txt_solution = findViewById(R.id.txt_solution);
        recyclerView = findViewById(R.id.recycler_view);

        hospital_description = findViewById(R.id.institute_description);
        txt_hospital_name = findViewById(R.id.txt_hospital_name);
        TextView txt_make_booking = findViewById(R.id.txt_make_booking);

        facility1 = findViewById(R.id.facility1);
        facility2 = findViewById(R.id.facility2);

        txt_make_booking.setOnClickListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        txt_symtoms.setOnClickListener(this);
        txt_solution.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_symtoms:
                SymptomsDialog symptomsDialog = new SymptomsDialog(this, symptomsList);
                symptomsDialog.show();
                break;

            case R.id.txt_solution:
                SolutionDialog solutionDialog = new SolutionDialog(this, solutionsList);
                solutionDialog.setCancelable(false);
                solutionDialog.show();
                break;

            case R.id.txt_make_booking:
                if (selectedFacility.equals("") || selectedFacility.length() <= 0) {
                    Toast.makeText(this, "Facility not selected", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(this, SelectServiceActivity.class);
                    intent.putExtra("service", hospital);
                    intent.putExtra("selectedservice", selectedFacility);
                    intent.putExtra("selectedpos", selectedPos);
                    startActivity(intent);
                }
                break;

            default:
                break;
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
}
