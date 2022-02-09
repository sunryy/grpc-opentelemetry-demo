package com.luo.demo.grpc.sb.client.hello;

import com.luo.demo.grpc.hello.HelloGrpc;
import com.luo.demo.grpc.hello.HelloReply;
import com.luo.demo.grpc.hello.HelloRequest;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * gRPC客户端实现
 *
 * @author luohq
 * @date 2022-02-0t
 */
@Service
@Slf4j
public class HelloClientService {

    @GrpcClient("grpc-sb-server")
    private HelloGrpc.HelloBlockingStub helloBlockingStub;

    @GrpcClient("grpc-sb-server")
    private HelloGrpc.HelloStub helloStub;

    /**
     * 调用sayHello
     */
    public void sayHello() {
        //构建请求参数
        HelloRequest helloRequest = HelloRequest.newBuilder()
                .setName("luo")
                .build();

        //阻塞API
        HelloReply helloReply = this.helloBlockingStub.sayHello(helloRequest);
        log.info("[callSayHello]blocking resp: {}", helloReply);

        //非阻塞API
        this.helloStub.sayHello(helloRequest, new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {
                log.info("[callSayHello]resp: {}", value);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("[callSayHello]error");
            }

            @Override
            public void onCompleted() {
                log.info("[callSayHello]complete");
            }
        });
    }

    /**
     * 调用sayHelloServerStream
     */
    public void sayHelloServerStream() {
        //构建请求参数
        HelloRequest helloRequest = HelloRequest.newBuilder()
                .setName("luo")
                .build();

        //阻塞API
        Iterator<HelloReply> helloReplyIterator = this.helloBlockingStub.sayHelloServerStream(helloRequest);
        while (helloReplyIterator.hasNext()) {
            HelloReply helloReply = helloReplyIterator.next();
            log.info("[callSayHelloServerStream]blocking resp: {}", helloReply);
        }


        //非阻塞API
        this.helloStub.sayHelloServerStream(helloRequest, new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {
                log.info("[callSayHelloServerStream]resp: {}", value);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("[callSayHelloServerStream]error");
            }

            @Override
            public void onCompleted() {
                log.info("[callSayHelloServerStream]complete");
            }
        });
    }

    /**
     * 调用sayHelloClientStream
     */
    public void sayHelloClientStream() {
        //仅支持非阻塞API
        StreamObserver<HelloRequest> requestObserver = this.helloStub.sayHelloClientStream(new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {
                log.info("[callSayHelloClientStream]resp: {}", value);
            }

            @Override
            public void onError(Throwable t) {
                log.error("[callSayHelloClientStream]error", t);
            }

            @Override
            public void onCompleted() {
                log.info("[callSayHelloClientStream]complete");
            }
        });

        //发送请求
        requestObserver.onNext(HelloRequest.newBuilder().setName("luo1-c").build());
        //连续发送请求
        requestObserver.onNext(HelloRequest.newBuilder().setName("luo2-c").build());
        //连续发送请求
        requestObserver.onNext(HelloRequest.newBuilder().setName("luo3-c").build());

        //结束发送请求
        requestObserver.onCompleted();

    }

    /**
     * 调用sayHelloBiStream
     */
    public void sayHelloBiStream() {
        //仅支持非阻塞API
        StreamObserver<HelloRequest> requestObserver = this.helloStub.sayHelloBiStream(new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {
                log.info("[callSayHelloBiStream]resp: {}", value);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("[callSayHelloBiStream]error");
            }

            @Override
            public void onCompleted() {
                log.info("[callSayHelloBiStream]complete");
            }
        });

        //发送请求
        requestObserver.onNext(HelloRequest.newBuilder().setName("luo1-b").build());
        //连续发送请求
        requestObserver.onNext(HelloRequest.newBuilder().setName("luo2-b").build());
        //连续发送请求
        requestObserver.onNext(HelloRequest.newBuilder().setName("luo3-b").build());

        //结束发送请求
        requestObserver.onCompleted();
    }

}