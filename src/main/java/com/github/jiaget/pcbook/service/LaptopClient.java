package com.github.jiaget.pcbook.service;

import com.github.jiaget.pcbook.pb.CreateLaptopRequest;
import com.github.jiaget.pcbook.pb.CreateLaptopResponse;
import com.github.jiaget.pcbook.pb.Laptop;
import com.github.jiaget.pcbook.pb.LaptopServiceGrpc;
import com.github.jiaget.pcbook.pb.LaptopServiceGrpc.LaptopServiceBlockingStub;
import com.github.jiaget.pcbook.sample.Generator;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LaptopClient {
    private static final Logger logger = Logger.getLogger(LaptopClient.class.getName());

    // a connect to server
    private final ManagedChannel channel;
    private final LaptopServiceBlockingStub blockingStub;

    private LaptopClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = LaptopServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void createLaptop(Laptop laptop) {
        CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();
        CreateLaptopResponse response = CreateLaptopResponse.getDefaultInstance();
        try {
            response = blockingStub.withDeadlineAfter(5, TimeUnit.SECONDS).createLaptop(request);
        } catch (StatusRuntimeException e){
            if (e.getStatus().getCode() == Status.Code.ALREADY_EXISTS) {
                // info Log Level
                logger.info("laptop ID already exists");
                return;
            }
            logger.log(Level.SEVERE, "request failed" + e.getMessage());
            return;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "request failed:" + e.getMessage());
            return;
        }
        logger.info("laptop created with ID:" + response.getId());

    }

    public static void main(String[] args) throws InterruptedException {
        LaptopClient client = new LaptopClient("0.0.0.0", 8080);

        Generator generator = new Generator();
        Laptop laptop = generator.NewLaptop().toBuilder().setId("").build();
        try {
            client.createLaptop(laptop);
        } finally {
            client.shutdown();
        }
    }
}
