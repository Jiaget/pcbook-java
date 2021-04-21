package com.github.jiaget.pcbook.sample;

import com.github.jiaget.pcbook.pb.*;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class Generator {
    private  Random rand;

    public Generator() {
        rand = new Random();
    }
    public Keyboard NewKeyboard() {
        return Keyboard.newBuilder()
                .setLayout(randomKeyboardLayout())
                .setBacklit(rand.nextBoolean())
                .build();
    }

    public CPU NewCPU() {
        String brand = randomCPUBrand();
        String name = randomCPUName(brand);

        int numberCores = randomInt(2, 8);
        int numberTreads = randomInt(numberCores, 12);

        double minGhz = randomDouble(2.0, 3.5);
        double maxGhz = randomDouble(minGhz, 5.0);

        return CPU.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setNumberCores(numberCores)
                .setNumberThreads(numberTreads)
                .setMinGhz(minGhz)
                .setMaxGhz(maxGhz)
                .build();
    }

    public GPU NewGPU() {
        String brand = randomGPUBrand();
        String name = randomGPUName(brand);

        double minGhz = randomDouble(1.0, 1.5);
        double maxGhz = randomDouble(minGhz, 2.0);
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(2, 6))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();
        return GPU.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setMinGhz(minGhz)
                .setMaxGhz(maxGhz)
                .setMemory(memory)
                .build();
    }

    public Memory NewRAM() {
        return Memory.newBuilder()
                .setValue(randomInt(4, 64))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();
    }

    public Storage NewSSD() {
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(128, 1024))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();

        return Storage.newBuilder()
                .setDriver(Storage.Driver.SSD)
                .setMemory(memory)
                .build();
    }

    public Storage NewHDD() {
        Memory memory = Memory.newBuilder()
                .setValue(randomInt(1, 6))
                .setUnit(Memory.Unit.TERABYTE)
                .build();

        return Storage.newBuilder()
                .setDriver(Storage.Driver.HDD)
                .setMemory(memory)
                .build();
    }

    public Screen NewScreen() {
        int height = randomInt(1080, 4320);
        int width = height * 16 / 9;

        Screen.Resolution resolution = Screen.Resolution.newBuilder()
                .setHeight(height)
                .setWidth(width)
                .build();
        return Screen.newBuilder()
                .setSizeInch(randomFloat(13, 17))
                .setPanel(randomScreenPanel())
                .setMultitouch(rand.nextBoolean())
                .build();
    }
    public Laptop NewLaptop() {
        String brand = randomLaptopBrand();
        String name = randomLaptopName(brand);
        double weightKg = randomDouble(1.0, 3.0);
        double priceRMB = randomDouble(1500, 20000);

        int releaseYear = randomInt(2010, 2021);
        return Laptop.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setBrand(brand)
                .setName(name)
                .setWeightKg(weightKg)
                .setPriceRmb(priceRMB)
                .setCpu(NewCPU())
                .addGpus(NewGPU())
                .addStorage(NewSSD())
                .addStorage(NewHDD())
                .setScreen(NewScreen())
                .setKeyboard(NewKeyboard())
                .setReleaseYear(releaseYear)
                .setUpdatedAt(timestampNow())
                .build();
    }

    private Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }


    private String randomLaptopName(String brand) {
        switch (brand) {
            case "Apple":
                return randomStringFromSet(
                        "MacBook Air",
                        "Macbook Pro"
                );
            case "Dell":
                return randomStringFromSet(
                        "Insprion",
                        "Vostro",
                        "XPS",
                        "Latitude"
                );
            default:
                return randomStringFromSet(
                        "Thinkpad",
                        "IdeaPad"
                );
        }
    }

    private String randomLaptopBrand() {
        return randomStringFromSet("Apple", "Dell", "Lenovo");
    }

    private Screen.Panel randomScreenPanel() {
        if (rand.nextBoolean()) {
            return Screen.Panel.IPS;
        }
        return Screen.Panel.OLED;
    }

    private float randomFloat(float min, float max) {
        return min + rand.nextFloat() * (max- min);
    }

    private String randomGPUName(String brand) {
        if (brand == "NVIDIA") {
            return randomStringFromSet(
                    "GTX 1080",
                    "GTX 1070",
                    "Quadro P5000",
                    "Tesla K40",
                    "Tesla K40"
            );
        }
        return randomStringFromSet(
                "RX 6900 XT",
                "RX 6800",
                "Radeon VII",
                "R9 Fury X",
                "R9 380X"
        );
    }

    private String randomGPUBrand() {
        return randomStringFromSet("NVIDIA", "AMD");
    }

    private double randomDouble(double min, double max) {
        return min + rand.nextDouble() * (max - min);
    }

    private int randomInt(int min, int max) {
        return min + rand.nextInt(max - min + 1);
    }

    private String randomCPUName(String brand) {
        if (brand.equals("Intel")) {
            return randomStringFromSet(
                    "Intel core i7-4790K",
                    "Intel core i5-4590",
                    "Intel core i3-4160",
                    "Intel core i5-4460",
                    "Intel core i7-4790"
            );
        }
        return randomStringFromSet(
                "Ryzen 3 Pro 1200",
                "Ryzen 3 1200",
                "Ryzen 3 Pro 1300",
                "Ryzen 3 1300X"
        );
    }

    private String randomCPUBrand() {
        return randomStringFromSet("Intel", "AMD");
    }

    private  String randomStringFromSet(String... a) {
        int n = a.length;
        if (n == 0) {
            return "";
        }
        return a[rand.nextInt(n)];
    }

    private Keyboard.Layout randomKeyboardLayout() {
        switch (rand.nextInt(3)){
            case 1:
                return Keyboard.Layout.QWERTY;
            case 2:
                return Keyboard.Layout.QWERTZ;
            default:
                return Keyboard.Layout.AZERTY;

        }
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        Laptop laptop = generator.NewLaptop();
        System.out.println(laptop);
    }
}
