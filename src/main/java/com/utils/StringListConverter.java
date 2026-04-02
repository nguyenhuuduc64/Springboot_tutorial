package com.utils; // Thay đổi theo package của ông chủ thưa ông chủ

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";"; // Dùng dấu chấm phẩy để ngăn cách thưa ông chủ

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        // Biến List thành String để lưu vào DB: ["Hà Nội", "HCM"] -> "Hà Nội;HCM"
        return (attribute == null || attribute.isEmpty())
                ? ""
                : String.join(SPLIT_CHAR, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        // Biến String từ DB thành List để Java dùng: "Hà Nội;HCM" -> ["Hà Nội", "HCM"]
        if (dbData == null || dbData.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(dbData.split(SPLIT_CHAR)));
    }
}