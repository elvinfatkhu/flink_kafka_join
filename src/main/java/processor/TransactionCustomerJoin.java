package processor;

import schema.Customer;
import schema.Transaction;
import schema.TransactionCustomer;

public class TransactionCustomerJoin extends FactDimJoin<String, Transaction, Customer, TransactionCustomer> {

    public TransactionCustomerJoin(Class dimTypeClass) {
        super(dimTypeClass);
    }

    @Override
    public TransactionCustomer join(String s, Transaction transaction, Customer customer) {
        TransactionCustomer transactionCustomer = new TransactionCustomer(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerCity(),
                transaction.getTransactionId(),
                transaction.getProductId(),
                transaction.getProductPrice(),
                transaction.getProductQuantity(),
                transaction.getCurrency(),
                transaction.getTransactionDate());
        return transactionCustomer;
    }
}
