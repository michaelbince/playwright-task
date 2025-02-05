package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.DataProviderException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestDataLoader {

   private TestDataLoader() {}

    public static Map<String, Object> loadTestData(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<>(){});
        } catch (IOException e) {
            throw new DataProviderException("Error loading test data from file: " + filePath, e);
        }
    }

}
