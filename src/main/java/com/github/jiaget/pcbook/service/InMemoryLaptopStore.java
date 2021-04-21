package com.github.jiaget.pcbook.service;

import com.github.jiaget.pcbook.pb.Laptop;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryLaptopStore implements LaptopStore {
    private ConcurrentMap<String, Laptop> data;

    public InMemoryLaptopStore(){
        data = new ConcurrentHashMap<>(0);
    }

    @Override
    public void Save(Laptop laptop) throws Exception {
        if (data.containsKey(laptop.getId())) {
            throw new AlreadyExistsException("laptop ID already exists");
        }
        // deep copy
        Laptop copiedLaptop = laptop.toBuilder().build();
        data.put(copiedLaptop.getId(), copiedLaptop);
    }

    @Override
    public Laptop Find(String id) {
        if (!data.containsKey(id)){
            return null;
        }
        Laptop copiedLaptop = data.get(id).toBuilder().build();
        return copiedLaptop;
    }
}
