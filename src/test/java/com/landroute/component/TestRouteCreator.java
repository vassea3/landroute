package com.landroute.component;

import com.landroute.constant.Region;
import com.landroute.dto.Country;
import com.landroute.dto.RouteResult;
import com.landroute.exception.NoRouteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRouteCreator {

    private static RouteCreator routeCreator;
    private static Map<String, Country> countryMap;
    private static Country origin;
    private static Country destination;
    private static RouteResult expectedResult;

    @BeforeAll
    public static void setUp() {
        routeCreator = new RouteCreator();

        origin = new Country("CZE", Region.EUROPE, List.of("AUT"));
        destination = new Country("ITA", Region.EUROPE, List.of("AUT"));

        countryMap = new HashMap<>();
        countryMap.put(origin.getCca3(), origin);
        countryMap.put("AUT", new Country("AUT", Region.EUROPE, List.of("CZE", "ITA")));
        countryMap.put(destination.getCca3(), destination);

        expectedResult = new RouteResult(List.of("CZE", "AUT", "ITA"));
    }

    @Test
    void buildRoute_positiveResult() {
        RouteResult actualResult = routeCreator.buildRoute(countryMap, origin, destination);
        assertEquals(actualResult.getRoute().toString(), expectedResult.getRoute().toString());
    }

    @Test
    void buildRoute_noRouteFound() {
        assertThrows(NoRouteException.class, () ->
                routeCreator.buildRoute(countryMap, origin, new Country("AFG", Region.ASIA, new ArrayList<>())));
    }
}
