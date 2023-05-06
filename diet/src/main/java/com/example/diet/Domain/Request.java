package com.example.diet.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private Long req_id;
    private String from_usr_id;
    private String to_usr_id;
    private String req_msg;
}
