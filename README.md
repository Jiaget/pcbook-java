
# 0. 环境&依赖 配置
- 下载安装Java环境： 1.jdk 2.IDEA
- 新建项目选择 gradle框架。
- 在gradle文件里的`plugins`添加`id "com.google.protobuf" version "0.8.15"` 让`gradle`处理`protobuf`的版本下载
- 在`dependencies` 中添加`proto` 的依赖：`protobuf-java`和`grpc-all`这些依赖都可以在maven库中找到。
- 在gradle 底部添加`protoc`的依赖。 这些关于`proto`的依赖的命令可以 google 关键字 `gradle protoc` 获取。
```
protobuf {
    // Configure the protoc executable
    protoc {
        // Download from repositories
        artifact = 'com.google.protobuf:protoc:3.15.8'
    }
}
```
- 同理，获取`grpc`相关命令
```
plugins{
  grpc {
    artifact = 'io.grpc:protoc-gen-grpc-java:1.0.0-pre2'
    // or
    // path = 'tools/protoc-gen-grpc-java'
  }
}
```
- 在 `protobuf` 下再添加 以下指令。这指定所有的protobuf任务都通过plugins下指定的grpc运行，可以提高protobuf的工作效率
```
    generateProtoTasks{
        all()*.plugins {
            grpc{}
        }
    }
```

- 指定grpc生成代码路径以及Java代码路径
```
sourceSets {
    main {
        java {
            srcDirs 'build/generated/source/proto/main/grpc'
            srcDirs 'build/generated/source/proto/main/java'
        }
    }
}
```
- 给java添加package: `java_multiple_files`将java 代码按类、接口拆分多个文件
```
option java_package = "com.github.jiaget.pcbook.pb";
option java_multiple_files = true;

```

最后，将两部分的proto代码统一。

# 1. 使用java生成数据 （binary， json）
- 在 `sample/Generator.java` 编写生成随机数据代码
- 在 `serializer/Serializer.java` 编写写入二进制文件，读取二进制文件，写入json文件，三个函数。

# 2. 客户端、服务端代码
- 基本逻辑和go端一致。
- 启动 java 服务端， go 客户端连接成功。
- 启动 go 服务端， java 客户端连接成功。

这就是 gRPC 第一类，简单 rpc。



