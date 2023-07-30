package com.landroute.component;

import com.landroute.dto.Country;
import com.landroute.exception.NoRouteException;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class RoutePointsValidator {

    public void validate(Map<String, Country> countryMap, String origin, String destination) {
        Country originCountry = Optional.ofNullable(countryMap.get(origin.toUpperCase()))
                .orElseThrow(() -> new NoRouteException(String.format("Origin country %s not found", origin)));
        Country destinationCountry = Optional.ofNullable(countryMap.get(destination.toUpperCase()))
                .orElseThrow(() -> new NoRouteException(String.format("Destination country %s not found", destination)));

        if (!originCountry.getRegion().connectedWith(destinationCountry.getRegion())) {
            throw new NoRouteException(String.format(
                    "%s (%s) is not connected with %s (%s) by land",
                    originCountry.getRegion(), origin,
                    destinationCountry.getRegion(), destination));
        }

        if (!origin.equals(destination)) {
            if (originCountry.getBorders().isEmpty()) {
                throw new NoRouteException(String.format("Origin %s is isolated", origin));
            }

            if (destinationCountry.getBorders().isEmpty()) {
                throw new NoRouteException(String.format("Destination %s is isolated", destination));
            }
        }
    }

}
