package com.example.railwayticket.utils.converter;

import jakarta.persistence.AttributeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntegerListConverter implements AttributeConverter<List<Integer>, String> {
    @Override
    public String convertToDatabaseColumn(List<Integer> list) {
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<Integer> convertToEntityAttribute(String str) {
        return str.isEmpty() ? new ArrayList<>()
                : Arrays.stream(str.split(","))
                .map(Integer::parseInt)
                .toList();
    }
}
