package com.rapidticket.venue.model;

import com.rapidticket.venue.utils.enums.EnumRoleUser;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class User {
    private String id;
    private String email;
    private String fullName;
    private String password;
    private EnumRoleUser role;
}
