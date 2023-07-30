package com.landroute.controller;

import com.landroute.dto.RouteResult;
import com.landroute.service.LandRouteService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routing")
@RequiredArgsConstructor
@Validated
public class LandRouteController {

    private static final String COUNTRY_CODE_PATTERN = "[a-zA-Z]{3}";

    private final LandRouteService landRouteService;

    @GetMapping("/{origin}/{destination}")
    public RouteResult findRoute(@PathVariable @Pattern(regexp = COUNTRY_CODE_PATTERN) final String origin,
                                 @PathVariable @Pattern(regexp = COUNTRY_CODE_PATTERN) final String destination) {

        return landRouteService.findRoute(origin, destination);
    }
}
