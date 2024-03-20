package processor;

import org.apache.flink.util.OutputTag;
import schema.Customer;
import schema.Transaction;
import schema.TransactionCustomer;

public class TransactionCustomerJoinWithSideOutput extends FactDimJoinWithSideOutput<String, Transaction, Customer, TransactionCustomer>{
    public TransactionCustomerJoinWithSideOutput(Class<Customer> dimTypeClass, OutputTag<Transaction> sideOtputTag) {
        super(dimTypeClass, sideOtputTag);
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
