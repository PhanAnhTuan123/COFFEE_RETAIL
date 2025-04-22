package socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ProtocolUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    // Convert object to JSON string
    public static String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    // Generic method to convert JSON string to object
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    // Generic method to handle List<T>
    public static <T> List<T> fromJsonList(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(
                json,
                mapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
    }


}