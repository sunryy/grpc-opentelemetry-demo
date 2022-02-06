# grpc-base模块

公共grpc模块，用于存储proto定义及其生成的代码，
主要功能：
- 存储公共proto3文件，位置：[src/main/proto](src/main/proto)
- 存储根据proto3定义生成的公共gRPC代码
- 集成grpc公共maven依赖
- 集成grpc生成代码maven插件

目前包括示例：
- [src/main/proto/hello.proto](src/main/proto/hello.proto)
  - rpc sayHello (HelloRequest) returns (HelloReply) {}
  - rpc sayHelloServerStream (HelloRequest) returns (stream HelloReply) {}
  - rpc sayHelloClientStream (stream HelloRequest) returns (HelloReply) {}
  - rpc sayHelloBiStream (stream HelloRequest) returns (stream HelloReply) {}