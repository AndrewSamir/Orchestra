
package com.samir.andrew.orchestra.Data;


public class PaymentData {

    int unitclientcode;
    String paymentTypeName;
    int installmentAmount;
    String dueDate;
    boolean paid;
    int paidAmount;
    String dateOfPayment;

    public PaymentData(int unitclientcode, String paymentTypeName,
                       int installmentAmount, String dueDate,
                       boolean paid, int paidAmount,
                       String dateOfPayment) {

        this.unitclientcode = unitclientcode;
        this.paymentTypeName = paymentTypeName;
        this.installmentAmount = installmentAmount;
        this.dueDate = dueDate;
        this.paid = paid;
        this.paidAmount = paidAmount;
        this.dateOfPayment = dateOfPayment;
    }

    public int getUnitclientcode() {
        return unitclientcode;
    }

    public void setUnitclientcode(int unitclientcode) {
        this.unitclientcode = unitclientcode;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public int getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(int installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

}
