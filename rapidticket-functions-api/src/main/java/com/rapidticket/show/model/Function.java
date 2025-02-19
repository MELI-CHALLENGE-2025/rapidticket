package com.rapidticket.show.model;

import com.rapidticket.show.utils.enums.EnumCurrency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Function {
    private String id;
    private String code;
    private String showId;
    private String venueId;
    private Date date;
    private double basePrice;
    private EnumCurrency currency;
}
