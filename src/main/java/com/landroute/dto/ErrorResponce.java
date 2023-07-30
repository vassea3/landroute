package com.landroute.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

import static java.time.LocalDateTime.now;

@Data
@Builder
public class ErrorResponce {

    private String error;

    private int status;

    @Builder.Default
    private LocalDateTime timestamp = now();
}
