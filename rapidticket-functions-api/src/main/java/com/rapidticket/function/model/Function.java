package com.rapidticket.function.model;

import com.rapidticket.function.utils.enums.EnumCurrency;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.ToString;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Function implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String code;
    private Show show;
    private Venue venue;
    private Timestamp date;
    private double basePrice;
    private EnumCurrency currency;
    private User createdBy;
}
