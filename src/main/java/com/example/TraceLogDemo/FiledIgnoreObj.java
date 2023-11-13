package com.example.TraceLogDemo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
public class FiledIgnoreObj {

    private String username;

    private String password;

}
