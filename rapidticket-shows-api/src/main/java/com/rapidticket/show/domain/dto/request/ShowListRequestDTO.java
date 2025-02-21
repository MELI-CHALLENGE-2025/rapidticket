package com.rapidticket.show.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ShowListRequestDTO implements Serializable {
    @Size(min = 1, max = 50, message = "code must be between 1 and 50 characters")
    private String code;
    @Size(max = 100, message = "name must be up to 100 characters")
    private String name;
    @Min(value = 1, message = "Page must be greater than or equal to 1")
    private int page;
    @Min(value = 1, message = "Size must be greater than or equal to 1")
    private int size;
}
