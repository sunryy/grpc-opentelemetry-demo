package com.luo.demo.grpc.sb.server.hello;

import io.grpc.stub.StreamObserver;
import io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceRequest;
import io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceResponse;
import io.opentelemetry.proto.collector.metrics.v1.MetricsServiceGrpc.MetricsServiceImplBase;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j

public class HelloPromithusImpl extends MetricsServiceImplBase{
// public class HelloPromithusImpl {
        // public class HelloPromithusImpl extends MetricsServiceImplBase{
// /*
        @Override
    public void export(ExportMetricsServiceRequest request, StreamObserver<ExportMetricsServiceResponse> responseObserver) {
        // ExportMetricsServiceRequest req =  ExportMetricsServiceRequest.newBuilder().setResourceMetrics(index, builderForValue);
        int mtrCnt = request.getResourceMetricsCount();

    }
    // */
}
