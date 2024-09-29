package com.java.publisher.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;
import java.time.LocalDate;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EntityScan
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private String feedName;
    private LocalDate date;
}
