package com.rapidticket.venue.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Venue {
    private String id;
    private String code;
    private String name;
    private String location;
    private int capacity;
    private User createdBy;
}
