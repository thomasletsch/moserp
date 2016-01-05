package org.moserp.environment.rest;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginData implements Serializable {

    private String username;
    private String password;
}
