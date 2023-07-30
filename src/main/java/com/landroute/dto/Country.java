package com.landroute.dto;

import com.landroute.constant.Region;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Country {

    private final String cca3;

    private final Region region;

    @EqualsAndHashCode.Exclude
    private List<String> borders = new ArrayList<>();

}
