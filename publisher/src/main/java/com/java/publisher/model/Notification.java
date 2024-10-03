package com.java.publisher.model;

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
    private LocalDate eventDate;
}
