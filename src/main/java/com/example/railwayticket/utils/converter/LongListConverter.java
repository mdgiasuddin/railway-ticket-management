package com.example.railwayticket.utils.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class LongListConverter implements AttributeConverter<List<Long>, String> {

    @Override
    public String convertToDatabaseColumn(List<Long> list) {
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<Long> convertToEntityAttribute(String str) {
        return (str == null || str.isEmpty()) ? new ArrayList<>()
                : Arrays.stream(str.split(","))
                .map(Long::parseLong)
                .toList();
    }
}
