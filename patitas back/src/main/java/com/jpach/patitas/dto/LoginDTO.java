package com.jpach.patitas.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String username;
    private String password;

}
