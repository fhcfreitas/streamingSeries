package fhcfreitas.java.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fhcfreitas.java.demo.model.SeriesData;

public class ConvertData implements IConvertData{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obtainData(String json, Class<T> classes) {
        try {
            return mapper.readValue(json, classes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
