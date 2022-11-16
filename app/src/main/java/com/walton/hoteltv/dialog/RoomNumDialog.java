package com.walton.hoteltv.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.walton.hoteltv.R;

public class RoomNumDialog extends Dialog implements View.OnFocusChangeListener {

    private EditText etRoomNumber;
    private LinearLayout btnOk, btnCancel;
    private Context mContext;
    private RoomData iRoomData;


    public RoomNumDialog(@NonNull Context context, RoomData roomData) {
        super(context);
        this.mContext = context;
        this.iRoomData = roomData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        setContentView(R.layout.dialog_set_date);

        initUI();
    }

    private void initUI() {
        etRoomNumber = findViewById(R.id.etRoomNumber);
        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnOk);

        btnCancel.setOnFocusChangeListener(this);
        btnOk.setOnFocusChangeListener(this);
        etRoomNumber.setShowSoftInputOnFocus(false);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iRoomData != null) {
                    if (!etRoomNumber.getText().toString().isEmpty()) {
                        iRoomData.getRoomData(etRoomNumber.getText().toString());
                    }else {
                        etRoomNumber.setError("Empty!");
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iRoomData != null) {
                    iRoomData.getRoomData("cancel");
                }
            }
        });
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.btnCancel:
                if (b) {
                    btnCancel.setBackground(mContext.getResources().getDrawable(R.drawable.item_selected));
                } else {
                    btnCancel.setBackground(mContext.getResources().getDrawable(R.drawable.item_normal));
                }
                break;
            case R.id.btnOk:
                if (b) {
                    btnOk.setBackground(mContext.getResources().getDrawable(R.drawable.item_selected));
                } else {
                    btnOk.setBackground(mContext.getResources().getDrawable(R.drawable.item_normal));
                }
                break;

        }
    }

    public interface RoomData {
        public void getRoomData(String data);
    }
}
