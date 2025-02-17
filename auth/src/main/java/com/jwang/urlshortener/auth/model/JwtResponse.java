/* (C)2024 */
package com.jwang.urlshortener.auth.model;

import java.io.Serial;
import java.io.Serializable;

public class JwtResponse implements Serializable {

    @Serial private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
