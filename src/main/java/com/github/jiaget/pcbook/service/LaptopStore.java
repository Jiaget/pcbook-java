package com.github.jiaget.pcbook.service;

import com.github.jiaget.pcbook.pb.Laptop;

public interface LaptopStore {
    void Save(Laptop laptop) throws Exception;
    Laptop Find(String id);
}
