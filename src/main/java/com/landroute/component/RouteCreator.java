package com.landroute.component;

import com.landroute.dto.Country;
import com.landroute.dto.RouteResult;
import com.landroute.exception.NoRouteException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.springframework.stereotype.Component;

@Component
public class RouteCreator {

    public RouteResult buildRoute(Map<String, Country> countries, Country origin, Country destination) {
        Map<Country, Boolean> visited = new HashMap<>();
        Map<Country, Country> previous = new HashMap<>();

        Country currentCountry = origin;

        Queue<Country> pivot = new ArrayDeque<>();
        pivot.add(currentCountry);

        visited.put(currentCountry, true);

        while (!pivot.isEmpty()) {
            currentCountry = pivot.remove();
            if (!currentCountry.equals(destination)) {
                for (String neighbour : currentCountry.getBorders()) {
                    Country neighbourCountry = countries.get(neighbour);
                    if (!visited.containsKey(neighbourCountry)) {
                        pivot.add(neighbourCountry);
                        visited.put(neighbourCountry, true);
                        previous.put(neighbourCountry, currentCountry);
                        if (neighbourCountry.equals(destination)) {
                            return buildResult(destination, previous);
                        }
                    }
                }
            }
        }
        throw new NoRouteException("Cannot reach the route");
    }

    private RouteResult buildResult(Country destination, Map<Country, Country> previous) {
        List<Country> routeCountries = new ArrayList<>();
        for (Country node = destination; node != null; node = previous.get(node)) {
            routeCountries.add(node);
        }
        Collections.reverse(routeCountries);

        return new RouteResult(routeCountries.stream()
                .map(Country::getCca3)
                .toList());
    }
}
