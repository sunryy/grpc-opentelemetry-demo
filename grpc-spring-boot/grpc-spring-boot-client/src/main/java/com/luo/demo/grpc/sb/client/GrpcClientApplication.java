package com.luo.demo.grpc.sb.client;

import com.luo.demo.grpc.sb.client.hello.HelloClientService;
import com.luo.demo.grpc.sb.client.hello.HelloPromithusService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * gRPC客户端应用
 *
 * @author luohq
 * @date 2022-02-07
 */
@SpringBootApplication
public class GrpcClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }


    @Resource
    private HelloClientService helloClientService;

    @Resource
    private HelloPromithusService helloPromiService;

    @Override
    public void run(String... args) throws Exception {
        // this.helloClientService.sayHello();
        // this.helloClientService.sayHelloServerStream();
        // this.helloClientService.sayHelloClientStream();
        // this.helloClientService.sayHelloBiStream();

        helloPromiService.sendMetrics();
    }
}