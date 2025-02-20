package com.rapidticket.show.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Venue implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String code;
    private String name;
    private String location;
    private int capacity;
}
