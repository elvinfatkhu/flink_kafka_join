package processor;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction;
import org.apache.flink.util.Collector;
import schema.Customer;
import schema.Transaction;
import schema.TransactionCustomer;

public abstract  class FactDimJoin<K, F, D, O> extends KeyedCoProcessFunction<K, F, D, O> {
    private transient ValueState<D> dimState;
    private final Class<D> dimTypeClass;

    public FactDimJoin(Class<D> dimTypeClass) {
        this.dimTypeClass = dimTypeClass;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ValueStateDescriptor<D> dimStateDescriptor = new ValueStateDescriptor<>("dimState", dimTypeClass );
        dimState = getRuntimeContext().getState(dimStateDescriptor);
    }

    @Override
    public void processElement1(F f, KeyedCoProcessFunction<K, F, D, O>.Context context, Collector<O> collector) throws Exception {
        D dim = dimState.value();
        K key = context.getCurrentKey();
        if (dim != null){
            collector.collect(join(key, f, dim));
        }

    }

    @Override
    public void processElement2(D d, KeyedCoProcessFunction<K, F, D, O>.Context context, Collector<O> collector) throws Exception {
        dimState.update(d);
    }
    public abstract O join(K k, F f, D d);
}
