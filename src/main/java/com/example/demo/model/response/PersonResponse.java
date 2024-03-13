package com.example.demo.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonResponse {
    @Schema(example = "Vardenis")
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
