package serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import java.io.IOException;

public class JsonKafkaDeserializer<T> implements DeserializationSchema<T> {
    private final Class<T> typeClass;
    private final  ObjectMapper objectMapper = new ObjectMapper();

    public JsonKafkaDeserializer(Class<T> typeClass) {
        this.typeClass = typeClass;
    }
    @Override
    public T deserialize(byte[] bytes) throws IOException {
            return objectMapper.readValue(bytes, typeClass);
    }

    @Override
    public boolean isEndOfStream(T t) {
        return false;
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return TypeInformation.of(typeClass);
    }
}
