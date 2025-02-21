package com.rapidticket.function.model;

import com.rapidticket.function.utils.enums.EnumRoleUser;
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
