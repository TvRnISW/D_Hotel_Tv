package com.walton.hoteltv;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.walton.hoteltv.model.HotelBillModel;

import java.util.List;

public class BillRecyclerAdapter extends RecyclerView.Adapter<BillRecyclerAdapter.ViewHolder> {
    private static final String TAG = "BillRecyclerAdapter";
    Context context;
    List<HotelBillModel> hotelBillModelList;
    BillRecyclerAdapter(Context context, List<HotelBillModel> hotelBillModelList){
        this.context=context;
        this.hotelBillModelList=hotelBillModelList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position!=0){
            holder.date.setText(hotelBillModelList.get(position-1).getDate().getDate().substring(0, hotelBillModelList.get(position-1).getDate().getDate().indexOf(" ")));
            holder.room.setText(hotelBillModelList.get(position-1).getRoomno());
            holder.item.setText(hotelBillModelList.get(position-1).getSname());

            holder.tax.setText(hotelBillModelList.get(position-1).getTax());
            holder.service.setText(hotelBillModelList.get(position-1).getService());
            holder.balance.setText(hotelBillModelList.get(position-1).getBalance());
            holder.credit.setText(hotelBillModelList.get(position-1).getCredit());

            double addable=Double.parseDouble(hotelBillModelList.get(position-1).getAmmount());;
            Log.d("bill data", String.valueOf(Double.parseDouble(hotelBillModelList.get(position-1).getAmmount())));
            if(addable==0.0){
                addable= Double.parseDouble(hotelBillModelList.get(position-1).getCredit());
            }
            holder.charge.setText(String.valueOf(addable));
            double gross= addable
                    +Double.parseDouble(hotelBillModelList.get(position-1).getTax())
                    +Double.parseDouble(hotelBillModelList.get(position-1).getService());

            holder.gross.setText(String.valueOf(gross));
        }

    }

    @Override
    public int getItemCount() {
        return hotelBillModelList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,room,item,charge,service,tax,balance,credit,gross;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            room = itemView.findViewById(R.id.room);
            item = itemView.findViewById(R.id.item);
            charge = itemView.findViewById(R.id.charge);
            service = itemView.findViewById(R.id.service);
            tax = itemView.findViewById(R.id.tax);
            balance = itemView.findViewById(R.id.balance);
            credit = itemView.findViewById(R.id.credit);
            gross = itemView.findViewById(R.id.gross);
        }
    }
}
