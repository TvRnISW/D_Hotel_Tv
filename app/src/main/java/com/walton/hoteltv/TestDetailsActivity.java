package com.walton.hoteltv;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class TestDetailsActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private TextView txtRestaurantName, txtDetails;
    private Button btnRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_details);


        initUI();

        setData();

    }

    private void setData() {
        txtDetails.setText("We and our partners store or access information on devices, such as cookies and process personal data,  You can change your preferences at any time by returning to this site or visit our privacy policy.\n\n\nOPENING HOURS\nBreakfast 6:30 am - 10:30 am on weekends 6:30 am - 11 am.\nLunch - 12:30 pm - 3:30 pm\nDinner 6:30 pm - 10-30 pm");

    }

    private void initUI() {
        btnRestaurant = findViewById(R.id.btnRestaurant);
        txtDetails = findViewById(R.id.txtDetails);
        txtRestaurantName = findViewById(R.id.txtRestaurantName);


        btnRestaurant.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.btnRestaurant:
                if (b){
                    btnRestaurant.setTextColor(Color.BLACK);
                }
                break;
        }
    }
}