package com.landroute.service;

import com.landroute.component.CountryLoader;
import com.landroute.component.RouteCreator;
import com.landroute.component.RoutePointsValidator;
import com.landroute.dto.Country;
import com.landroute.dto.RouteResult;
import com.landroute.exception.NoRouteException;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandRouteService {

    private final CountryLoader dataLoader;
    private final RouteCreator routeCreator;
    private final RoutePointsValidator routePointsValidator;

    private Map<String, Country> countryMap;

    @PostConstruct
    protected void init() {
        countryMap = dataLoader.loadCountries().stream()
                .collect(Collectors.toMap(Country::getCca3, c -> c));
    }

    public RouteResult findRoute(final String origin, final String destination) {
        routePointsValidator.validate(countryMap, origin, destination);

        Country originCountry = countryMap.get(origin.toUpperCase());
        Country destinationCountry = countryMap.get(destination.toUpperCase());

        return routeCreator.buildRoute(countryMap, originCountry, destinationCountry);
    }
}
