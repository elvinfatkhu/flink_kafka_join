package schema;

import java.sql.Timestamp;

public class TransactionCustomer {
    private String customerId;
    private String customerName;
    private String customerCity;
    private String transactionId;
    private String productId;
    private double productPrice;
    private int productQuantity;
    private String currency;
    private Timestamp transactionDate;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionDate() {
        return transactionDate.toString();
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionCustomer(String customerId, String customerName, String customerCity, String transactionId, String productId, double productPrice, int productQuantity, String currency, Timestamp transactionDate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerCity = customerCity;
        this.transactionId = transactionId;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.currency = currency;
        this.transactionDate = transactionDate;
    }
}
