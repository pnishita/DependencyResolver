package com.java.dependencyresolver.model;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
@Getter
@Setter
public class NotificationDTO {
    private String message;
    private String feedName;
    private LocalDate date;
}
