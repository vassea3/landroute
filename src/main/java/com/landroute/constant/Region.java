package com.landroute.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Region {

    @JsonProperty("Africa")
    AFRICA,
    @JsonProperty("Americas")
    AMERICAS,
    @JsonProperty("Antarctic")
    ANTARCTIC,
    @JsonProperty("Asia")
    ASIA,
    @JsonProperty("Europe")
    EUROPE,
    @JsonProperty("Oceania")
    OCEANIA;
    private static final Set<Region> CONTINENTAL = Set.of(AFRICA, ASIA, EUROPE);

    public boolean connectedWith(Region region) {
        if (this == region) {
            return true;
        } else return (CONTINENTAL.contains(this) && CONTINENTAL.contains(region));
    }
}
