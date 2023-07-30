package com.landroute.component;

import com.landroute.constant.Region;
import com.landroute.dto.Country;
import com.landroute.exception.NoRouteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
public class TestRoutePointsValidator {

    private static RoutePointsValidator routePointsValidator;
    private static Map<String, Country> countryMap;
    private static Country origin;
    private static Country destination;
    private static Country isolatedCountry;
    private static Country countryNotConnectedByLand;

    @BeforeAll
    public static void setUp() {
        routePointsValidator = new RoutePointsValidator();

        origin = new Country("CZE", Region.EUROPE, List.of("AUT"));
        destination = new Country("ITA", Region.EUROPE, List.of("AUT"));
        isolatedCountry = new Country("AFG", Region.ASIA, new ArrayList<>());
        countryNotConnectedByLand = new Country("BLZ", Region.AMERICAS, List.of("GTM", "MEX"));

        countryMap = new HashMap<>();
        countryMap.put(origin.getCca3(), origin);
        countryMap.put("AUT", new Country("AUT", Region.EUROPE, List.of("CZE", "ITA")));
        countryMap.put(destination.getCca3(), destination);
        countryMap.put(isolatedCountry.getCca3(), isolatedCountry);
        countryMap.put(countryNotConnectedByLand.getCca3(), countryNotConnectedByLand);
    }

    @Test
    public void validate_positiveResult() {
        routePointsValidator.validate(countryMap, origin.getCca3(), destination.getCca3());
    }

    @Test
    public void buildRoute_originCountryNotFound() {
        Throwable exception = assertThrows(NoRouteException.class, () ->
                routePointsValidator.validate(countryMap, "OOO", destination.getCca3()));
        assertEquals("Origin country OOO not found", exception.getMessage());
    }

    @Test
    public void buildRoute_destinationCountryNotFound() {
        Throwable exception = assertThrows(NoRouteException.class, () ->
                routePointsValidator.validate(countryMap, origin.getCca3(), "DDD"));
        assertEquals("Destination country DDD not found", exception.getMessage());
    }

    @Test
    public void buildRoute_countriesNotConnectedByLand() {
        Throwable exception = assertThrows(NoRouteException.class, () ->
                routePointsValidator.validate(countryMap, origin.getCca3(), countryNotConnectedByLand.getCca3()));
        assertEquals("EUROPE (CZE) is not connected with AMERICAS (BLZ) by land", exception.getMessage());
    }

    @Test
    public void buildRoute_originIsIsolated() {
        Throwable exception = assertThrows(NoRouteException.class, () ->
                routePointsValidator.validate(countryMap, isolatedCountry.getCca3(), destination.getCca3()));
        assertEquals("Origin AFG is isolated", exception.getMessage());
    }

    @Test
    public void buildRoute_destinationIsIsolated() {
        Throwable exception = assertThrows(NoRouteException.class, () ->
                routePointsValidator.validate(countryMap, origin.getCca3(), isolatedCountry.getCca3()));
        assertEquals("Destination AFG is isolated", exception.getMessage());
    }
}
