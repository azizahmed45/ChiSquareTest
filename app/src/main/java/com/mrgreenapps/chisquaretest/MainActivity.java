package com.mrgreenapps.chisquaretest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText observedValueField;
    private EditText expectedValueField;

    private Button calculateButton;
    private Button addButton;

    private ListView valueListView;
    private ValueListAdapter valueListAdapter;

    private Button clearButton;

    private EditText chiSquareValueField;
    private EditText chiSquareWithConditionValueField;

    private Button checkButton;

    private List<Double> observedValueList = new ArrayList<>();
    private List<Double> expectedValueList = new ArrayList<>();

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initAdmob();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double chiSquare = ChiSquareTest.chiSquare(observedValueList, expectedValueList);

                chiSquareValueField.setText(String.valueOf(chiSquare));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validity
                if(observedValueField.getText().toString().isEmpty()){
                    observedValueField.setError("Required");
                    observedValueField.requestFocus();
                    return;
                }
                if(expectedValueField.getText().toString().isEmpty()){
                    expectedValueField.setError("Required");
                    expectedValueField.requestFocus();
                    return;
                }

                try {

                    double observedValue = Double.valueOf(observedValueField.getText().toString());
                    double expectedValue = Double.valueOf(expectedValueField.getText().toString());


                    observedValueList.add(observedValue);
                    expectedValueList.add(expectedValue);

                    observedValueField.getText().clear();
                    expectedValueField.getText().clear();

                    //TODO add to listView
                    valueListAdapter.setItems(observedValueList, expectedValueList);


                } catch (NumberFormatException e){

                    Toast.makeText(MainActivity.this, "Number format error", Toast.LENGTH_SHORT).show();

                }



            }
        });


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observedValueList.clear();
                expectedValueList.clear();
                valueListAdapter.clearList();
            }
        });



        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validity
                if(chiSquareValueField.getText().toString().isEmpty()){
                    chiSquareValueField.setError("Required");
                    chiSquareValueField.requestFocus();
                    return;
                }
                if(chiSquareWithConditionValueField.getText().toString().isEmpty()){
                    chiSquareWithConditionValueField.setError("Required");
                    chiSquareWithConditionValueField.requestFocus();
                    return;
                }


                double chiSquare = Double.valueOf(chiSquareValueField.getText().toString());
                double chiSquareWithCondition = Double.valueOf(chiSquareWithConditionValueField.getText().toString());


                if(ChiSquareTest.isAccepted(chiSquare, chiSquareWithCondition)){

                    new AlertDialog.Builder(MainActivity.this)
                            .setView(R.layout.layout_accepted)
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();

                } else {

                    new AlertDialog.Builder(MainActivity.this)
                            .setView(R.layout.layout_rejected)
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .create()
                            .show();
                }


            }
        });

    }

    private void initViews(){
        observedValueField = findViewById(R.id.observed_value_field);
        expectedValueField = findViewById(R.id.expected_value_field);

        calculateButton = findViewById(R.id.calculate_button);
        addButton = findViewById(R.id.add_button);

        valueListView = findViewById(R.id.value_list);
        //listview header
        View headerView = View.inflate(this, R.layout.list_view_header, null);
        valueListView.addHeaderView(headerView);

        valueListAdapter = new ValueListAdapter(this);
        valueListView.setAdapter(valueListAdapter);

        clearButton = findViewById(R.id.clear_button);

        chiSquareValueField = findViewById(R.id.chi_square_field);
        chiSquareWithConditionValueField = findViewById(R.id.sig_chi_square_field);

        checkButton = findViewById(R.id.check_button);
    }

    private void initAdmob(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
//                Toast.makeText(MainActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
//                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
