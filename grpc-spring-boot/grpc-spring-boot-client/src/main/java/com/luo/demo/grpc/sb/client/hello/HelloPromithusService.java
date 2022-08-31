package com.luo.demo.grpc.sb.client.hello;

import org.springframework.stereotype.Service;

import io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceRequest;
import io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceResponse;
import io.opentelemetry.proto.collector.metrics.v1.MetricsServiceGrpc;
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
    @GrpcClient("grpc-sb-server")
    private MetricsServiceGrpc.MetricsServiceBlockingStub helloBlockingStub;

    @GrpcClient("grpc-sb-server")
    private MetricsServiceGrpc.MetricsServiceStub helloStub;

    public void sendMetrics(){
        NumberDataPoint numdp = NumberDataPoint.newBuilder()
                                .setStartTimeUnixNano(1661932734)
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
