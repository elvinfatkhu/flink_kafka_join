package app;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import processor.FactDimJoin;
import processor.TransactionCustomerJoin;
import schema.Customer;
import schema.Transaction;
import schema.TransactionCustomer;
import serde.JsonKafkaDeserializer;

public class DataStreamJob {
    public static void main(String[] args) throws Exception {

        //initialize the environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        //Add KafkaSource for Transaction
        KafkaSource<Transaction> trxSource = KafkaSource.<Transaction>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("Event.Python.Transaction.Main")
                .setGroupId("Flink_CG")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new JsonKafkaDeserializer<>(Transaction.class))
                .build();

        //Add KafkaSource for Customer Dimension
        KafkaSource<Customer> customerSource = KafkaSource.<Customer>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("Event.Python.Customer")
                .setGroupId("Flink_CG")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new JsonKafkaDeserializer<>(Customer.class))
                .build();
        //Add Transaction datastream
        DataStream<Transaction> trxStream = env.fromSource(trxSource, WatermarkStrategy.forMonotonousTimestamps(), "Transaction");
        //Add Customer datastream
        DataStream<Customer> trxCustomer = env.fromSource(customerSource, WatermarkStrategy.forMonotonousTimestamps(), "Customer");
        trxCustomer.print();

        //Add key to the stream to perform join
        KeyedStream<Transaction, String> keyedtrxStream = trxStream.keyBy(Transaction -> Transaction.getCustomerId());
        //Add key to the customer stream to perform join
        KeyedStream<Customer, String> keyedCustomerStream = trxCustomer.keyBy(Customer -> Customer.getCustomerId());

        //Join both stream
        DataStream<TransactionCustomer> enriched = keyedtrxStream.connect(keyedCustomerStream).process(new TransactionCustomerJoin(Customer.class));
        enriched.print();



        env.execute("Flink_transaction_enrichment");

    }
}