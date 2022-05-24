package io.brewagebear.asyncserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMenuRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public UserMenuRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
