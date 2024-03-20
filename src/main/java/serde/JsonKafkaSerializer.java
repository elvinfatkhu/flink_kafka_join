package serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;

public class JsonKafkaSerializer<T> implements KafkaRecordSerializationSchema<T> {
    private final Class<T> typeClass;
    private final String topic;

    public JsonKafkaSerializer(Class<T> typeClass, String topic) {
        this.typeClass = typeClass;
        this.topic = topic;
    }

    @Override
    public void open(SerializationSchema.InitializationContext context, KafkaSinkContext sinkContext) throws Exception {
        KafkaRecordSerializationSchema.super.open(context, sinkContext);
    }

    @Nullable
    @Override
    public ProducerRecord<byte[], byte[]> serialize(T t, KafkaSinkContext kafkaSinkContext, Long aLong) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] serializedPojo = objectMapper.writeValueAsBytes(t);
            ProducerRecord producerRecord = new ProducerRecord<>(topic, null, serializedPojo);
            return producerRecord;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
