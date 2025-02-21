package com.rapidticket.show.model;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Show {
    private String id;
    private String code;
    private String name;
    private String description;
    private User createdBy;
}
