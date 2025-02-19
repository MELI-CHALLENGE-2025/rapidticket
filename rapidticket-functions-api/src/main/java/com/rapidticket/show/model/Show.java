package com.rapidticket.show.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Show {
    private String id;
    private String code;
    private String name;
    private String description;
}
