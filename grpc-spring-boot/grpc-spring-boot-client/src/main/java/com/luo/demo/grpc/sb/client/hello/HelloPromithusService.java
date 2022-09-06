package com.luo.demo.grpc.sb.client.hello;

import org.springframework.stereotype.Service;

import io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceRequest;
import io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceResponse;
import io.opentelemetry.proto.collector.metrics.v1.MetricsServiceGrpc;
import io.opentelemetry.proto.common.v1.AnyValue;
import io.opentelemetry.proto.common.v1.KeyValue;
import io.opentelemetry.proto.metrics.v1.Gauge;
import io.opentelemetry.proto.metrics.v1.InstrumentationLibraryMetrics;
import io.opentelemetry.proto.metrics.v1.Metric;
import io.opentelemetry.proto.metrics.v1.NumberDataPoint;
import io.opentelemetry.proto.metrics.v1.ResourceMetrics;
import io.opentelemetry.proto.resource.v1.Resource;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@Slf4j
 
public class HelloPromithusService {
    @GrpcClient("MetricsService")
    private MetricsServiceGrpc.MetricsServiceBlockingStub helloBlockingStub;

    @GrpcClient("MetricsService")
    private MetricsServiceGrpc.MetricsServiceStub helloStub;

    public void sendMetrics(){
      AnyValue av1 = AnyValue.newBuilder().setIntValue(1).build();
      KeyValue kv1 = KeyValue.newBuilder()
                    .setKey("spu_inst_id")
                    .setValue(av1)
                    .build();
      AnyValue av2 = AnyValue.newBuilder().setStringValue("172.16.200.125:12000").build();
      KeyValue kv2 = KeyValue.newBuilder()
                    .setKey("eureka_node")
                    .setValue(av2)
                    .build();
      NumberDataPoint numdp = NumberDataPoint.newBuilder()
                    .setStartTimeUnixNano(1661932734)
                    .addAttributes(kv1)
                    .addAttributes(kv2)
                    .setAsDouble(20.1)
                    .build();

        Gauge gauge = Gauge.newBuilder()
                        .addDataPoints(numdp)
                        .build();
        Metric metric = Metric.newBuilder()
                        // .setName("eureka_heartbeat_tps")
                        .setName("eureka_register_tps")
                        .setDescription("eureka注册tps")
                        .setUnit("条每秒")
                        .setGauge(gauge)
                        .build();
        InstrumentationLibraryMetrics instrMetrics = InstrumentationLibraryMetrics.newBuilder()
                                                    .addMetrics(metric)
                                                     .build();
        ResourceMetrics resourceMetrics = ResourceMetrics.newBuilder().
                                          setResource(Resource.newBuilder().build())
                                          .addInstrumentationLibraryMetrics(instrMetrics)
                                        //   .setInstrumentationLibraryMetrics(0, instrMetrics)
                                          .build();
        ExportMetricsServiceRequest req = ExportMetricsServiceRequest.newBuilder()
                                            .addResourceMetrics(resourceMetrics)
                                            // .setResourceMetrics(0, resourceMetrics)
                                            .build();

        ExportMetricsServiceResponse res = this.helloBlockingStub.export(req);          
        log.info("[callRpcService]blocking resp: ");
    
    }

}
