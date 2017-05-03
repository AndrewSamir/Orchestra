package com.samir.andrew.orchestra.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samir.andrew.orchestra.Data.PaymentData;
import com.samir.andrew.orchestra.R;

import java.util.List;

/**
 * Created by andre on 29-Apr-17.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    private List<PaymentData> paymentDataList;

    public PaymentAdapter(List<PaymentData> paymentDataList) {
        this.paymentDataList = paymentDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview_payment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PaymentData paymentData = paymentDataList.get(position);
        holder.paymentTypeName.setText(paymentData.getPaymentTypeName());
        holder.installmentAmount.setText(paymentData.getInstallmentAmount()+"");
        holder.dueDate.setText(paymentData.getDueDate());
        holder.paid.setText("true");
        holder.paidAmount.setText(paymentData.getPaidAmount()+"");
        holder.dateOfPayment.setText(paymentData.getDateOfPayment());

    }

    @Override
    public int getItemCount() {
        return paymentDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView paymentTypeName, installmentAmount, dueDate, paid, paidAmount, dateOfPayment;

        public MyViewHolder(View itemView) {
            super(itemView);
            paymentTypeName = (TextView) itemView.findViewById(R.id.tvPaymentTypeNamePayment);
            installmentAmount = (TextView) itemView.findViewById(R.id.tvInstallmentAmountPayment);
            dueDate = (TextView) itemView.findViewById(R.id.tvDueDatePayment);
            paid = (TextView) itemView.findViewById(R.id.tvPaidPayment);
            paidAmount = (TextView) itemView.findViewById(R.id.tvPaidAmountPayment);
            dateOfPayment = (TextView) itemView.findViewById(R.id.tvDateOfPaymentPayment);
        }
    }
}
