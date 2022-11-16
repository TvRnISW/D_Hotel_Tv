package com.walton.hoteltv;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

public class LocalAttractionActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {

    private Button btnFirst, btnSecond, btnThird, btnFourth, btnFifth;
    private TextView txtLocalAttractionPlaceTitle, txtDetails;
    private List<ContentData> dataList;

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == TestConstant.ONE_FOCUS){
                oneLaout(true);
            }else if (msg.what == TestConstant.ONE_HIDE){
                oneLaout(false);
            }else if (msg.what == TestConstant.TWO_FOCUS){
                twoLaout(true);
            }else if (msg.what == TestConstant.TWO_HIDE){
                twoLaout(false);
            }else if (msg.what == TestConstant.THREE_FOCUS){
                threeLaout(true);
            }else if (msg.what == TestConstant.THREE_HIDE){
                threeLaout(false);
            }else if (msg.what == TestConstant.FOUR_FOCUS){
                fourLaout(true);
            }else if (msg.what == TestConstant.FOUR_HIDE){
                fourLaout(false);
            }else if (msg.what == TestConstant.FIVE_FOCUS){
                fiveLaout(true);
            }else if (msg.what == TestConstant.FIVE_HIDE){
                fiveLaout(false);
            }
        }
    };

    private void fiveLaout(boolean b) {
        if (b){
            txtLocalAttractionPlaceTitle.setText(dataList.get(4).getTitle());
            txtDetails.setText(dataList.get(4).getDetails());
        }
    }

    private void fourLaout(boolean b) {
        if (b){
            txtLocalAttractionPlaceTitle.setText(dataList.get(3).getTitle());
            txtDetails.setText(dataList.get(3).getDetails());
        }
    }

    private void threeLaout(boolean b) {
        if (b){
            txtLocalAttractionPlaceTitle.setText(dataList.get(2).getTitle());
            txtDetails.setText(dataList.get(2).getDetails());
        }
    }

    private void twoLaout(boolean b) {
        if (b){
            txtLocalAttractionPlaceTitle.setText(dataList.get(1).getTitle());
            txtDetails.setText(dataList.get(1).getDetails());
        }
    }

    private void oneLaout(boolean b) {
        if (b){
            txtLocalAttractionPlaceTitle.setText(dataList.get(0).getTitle());
            txtDetails.setText(dataList.get(0).getDetails());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_attraction);

        dataList = new ArrayList<>();

        dataList.add(new ContentData("Sreemongol", "Sreemongol in Sylhet has long been credited as a top tourist attraction having earned its recognition as a town as early as the beginning of the 19th century. Even though the first ever tea garden in Sylhet – Malnichara – was established near Sylhet city in 1854. Anyone travelling in and around Sreemongol is sure to be greeted by lush green tea gardens that stretch over miles and miles of hilly areas, not to speak of the chirping birds and shading trees besides the pineapple and lemon gardens."));
        dataList.add(new ContentData("Sunderbans", "Sunderbans, is the largest mangrove forest on earth and a UNESCO World Heritage Site. It spans from the Hooghly River in India’s state of West Bengal to the Baleswar River in Bangladesh. 60% of this mist-shrouded forest is located in Bangladesh. Intersected by a complex network of rivers and creeks, mudflats and tiny islands, this amazing forest is comprised of two elementary eco-regions: mangrove forest and freshwater swamp Forest. It has the rich biodiversity and unique eco-system which boasts of the Royal Bengal tigers, spotted deer, crocodiles, riverine dolphins, wild boars and many more."));
        dataList.add(new ContentData("Cox’s Bazaar", "Cox’s Bazaar is a major tourist attraction in Bangladesh. Sloping down to the blue water of the Bay of Bengal against the picturesque backdrop of a series of hills covered with dense forest, Cox’s Bazaar sea beach is the longest sea beach in the world. Its total length is 120 km. It is the place of miles of golden sands, lofty cliffs, surfing waves, amazing conch shells, beautiful pagodas, Buddhist temples and tribes, and mouthwatering seafood. The shark-free beach has its own reputation for bathing, sunbathing, swimming, surfing and many beach rides. The breathtaking beauty of the setting sun behind the vast sea is fascinating."));
        dataList.add(new ContentData("Saint Martin Island","The Saint Martin’s Island is one of the most visited tourist’s spots in Bangladesh. The only coral island in Bangladesh is about 8 km in length and rarely more than 1 km wide. It is about 10 km (6 mi) south-west of the southern tip of the Cox’s Bazaar-Teknaf peninsula. Beaches fringed with coconut palms, panoramic beauty of the island and pristine marine life attract the tourists. Magnificent landscapes, crystal clear sea water, coral colony, and the roar of the Bay of Bengal are the main attraction of the visitors."));
        dataList.add(new ContentData("Khagrachari","Khagrachari, locally known as Chengmi is one of the most beautiful districts in Bangladesh. This beautiful district competes shoulder to shoulder with Bandarban and Rangamati in terms of mesmerizing landscapes and vibrant culture and lifestyles. Richhang Waterfall, Alutilla cave, Dighinala are some of the attractions located in Khagrachari."));

        initUI();
    }

    private void initUI() {

        txtDetails = findViewById(R.id.txtDetails);
        txtLocalAttractionPlaceTitle = findViewById(R.id.txtLocalAttractionPlaceTitle);

        btnFirst = findViewById(R.id.btnFirst);
        btnSecond = findViewById(R.id.btnSecond);
        btnThird = findViewById(R.id.btnThird);
        btnFourth = findViewById(R.id.btnFourth);
        btnFifth = findViewById(R.id.btnFifth);

        btnFirst.setOnClickListener(this);
        btnSecond.setOnClickListener(this);
        btnThird.setOnClickListener(this);
        btnFourth.setOnClickListener(this);
        btnFifth.setOnClickListener(this);

        btnFirst.setOnFocusChangeListener(this);
        btnSecond.setOnFocusChangeListener(this);
        btnThird.setOnFocusChangeListener(this);
        btnFourth.setOnFocusChangeListener(this);
        btnFifth.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.btnFirst:
                if (b) {
                    handler.sendEmptyMessage(TestConstant.ONE_FOCUS);
                    btnFirst.setTextColor(Color.BLACK);
                } else {
                    handler.sendEmptyMessage(TestConstant.ONE_HIDE);
                    btnFirst.setTextColor(Color.WHITE);
                }
                break;
            case R.id.btnSecond:
                if (b) {
                    handler.sendEmptyMessage(TestConstant.TWO_FOCUS);
                    btnSecond.setTextColor(Color.BLACK);
                } else {
                    handler.sendEmptyMessage(TestConstant.TWO_HIDE);
                    btnSecond.setTextColor(Color.WHITE);
                }
                break;
            case R.id.btnThird:
                if (b) {
                    handler.sendEmptyMessage(TestConstant.THREE_FOCUS);
                    btnThird.setTextColor(Color.BLACK);
                } else {
                    handler.sendEmptyMessage(TestConstant.THREE_HIDE);
                    btnThird.setTextColor(Color.WHITE);
                }
                break;
            case R.id.btnFourth:
                if (b) {
                    handler.sendEmptyMessage(TestConstant.FOUR_FOCUS);
                    btnFourth.setTextColor(Color.BLACK);
                } else {
                    handler.sendEmptyMessage(TestConstant.FOUR_HIDE);
                    btnFourth.setTextColor(Color.WHITE);
                }
                break;
            case R.id.btnFifth:
                if (b) {
                    handler.sendEmptyMessage(TestConstant.FIVE_FOCUS);
                    btnFifth.setTextColor(Color.BLACK);
                } else {
                    handler.sendEmptyMessage(TestConstant.FIVE_HIDE);
                    btnFifth.setTextColor(Color.WHITE);
                }
                break;
        }
    }
}