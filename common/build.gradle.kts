plugins {
    id("java-library")
    id("com.google.protobuf")
}

dependencies {
    api("io.grpc:grpc-netty-shaded:1.60.0")
    api("io.grpc:grpc-protobuf:1.60.0")
    api("io.grpc:grpc-stub:1.60.0")
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.60.0"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}
