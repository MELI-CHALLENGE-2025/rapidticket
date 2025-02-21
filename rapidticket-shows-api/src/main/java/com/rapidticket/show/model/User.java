package com.rapidticket.show.model;

import com.rapidticket.show.utils.enums.EnumRoleUser;
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
