package com.example.physicalterms.dao;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {
    @TypeConverter
    public String fromData(List<String> data) {
        return data.stream().collect(Collectors.joining(","));
    }

    @TypeConverter
    public List<String> toData(String data) {
        return Arrays.asList(data.split(","));
    }
}
