package project;

import java.util.Date;

public class Sale1 {
    private int saleId;
    private int employeeId;
    private int customerId;
    private double totalAmount;
    private String paymentType;
    private Date saleDate;

    public Sale1() {}

    public Sale1(int saleId, int employeeId, int customerId, double totalAmount, String paymentType, Date saleDate) {
        this.saleId = saleId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.paymentType = paymentType;
        this.saleDate = saleDate;
    }

    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public Date getSaleDate() { return saleDate; }
    public void setSaleDate(Date saleDate) { this.saleDate = saleDate; }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", employeeId=" + employeeId +
                ", customerId=" + customerId +
                ", totalAmount=" + totalAmount +
                ", paymentType='" + paymentType + '\'' +
                ", saleDate=" + saleDate +
                '}';
    }
}
