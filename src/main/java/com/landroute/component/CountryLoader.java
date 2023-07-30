package com.landroute.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.landroute.dto.Country;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CountryLoader {

    private static final TypeReference<List<Country>> TYPE_REF = new TypeReference<>() {
    };
    public static final String RESOURCE_URL = "/countries.json";

    private final ObjectMapper objectMapper;

    public List<Country> loadCountries() {
        try {
            return objectMapper.readValue(CountryLoader.class.getResourceAsStream(RESOURCE_URL), TYPE_REF);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to load list of countries");
        }
    }


}
