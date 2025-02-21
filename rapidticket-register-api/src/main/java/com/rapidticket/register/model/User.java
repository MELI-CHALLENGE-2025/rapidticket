package com.rapidticket.register.model;

import com.rapidticket.register.utils.enums.EnumRoleUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
