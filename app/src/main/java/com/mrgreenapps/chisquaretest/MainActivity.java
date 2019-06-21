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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText observedValueField;
    private EditText expectedValueField;

    private Button calculateButton;
    private Button submitButton;

    private ListView valueListView;
    private ValueListAdapter valueListAdapter;

    private Button clearButton;

    private EditText chiSquareValueField;
    private EditText chiSquareWithConditionValueField;

    private Button checkButton;

    private List<Double> observedValueList = new ArrayList<>();
    private List<Double> expectedValueList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double chiSquare = ChiSquareTest.chiSquare(observedValueList, expectedValueList);

                chiSquareValueField.setText(String.valueOf(chiSquare));
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
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
        observedValueField = findViewById(R.id.observed_value);
        expectedValueField = findViewById(R.id.expected_value);

        calculateButton = findViewById(R.id.calculate);
        submitButton = findViewById(R.id.submit);

        valueListView = findViewById(R.id.value_list);
        valueListAdapter = new ValueListAdapter(this);
        valueListView.setAdapter(valueListAdapter);

        clearButton = findViewById(R.id.clear);

        chiSquareValueField = findViewById(R.id.chi_square);
        chiSquareWithConditionValueField = findViewById(R.id.chi_square_with_condition);

        checkButton = findViewById(R.id.check);
    }
}
