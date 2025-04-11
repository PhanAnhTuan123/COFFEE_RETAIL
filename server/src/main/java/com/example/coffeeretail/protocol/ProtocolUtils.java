package com.example.coffeeretail.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.coffeeretail.dto.Request;
import com.example.coffeeretail.dto.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProtocolUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Request readRequest(InputStream in) throws IOException {
        // Đọc độ dài đầu tiên (nếu dùng framing), hoặc đọc đến end-of-stream của một ObjectInputStream
        return mapper.readValue(in, Request.class);
    }

    public static void writeResponse(OutputStream out, Response resp) throws IOException {
        mapper.writeValue(out, resp);
        out.flush();
    }

    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }
}
