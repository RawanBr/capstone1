package com.example.capston1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.websocket.OnOpen;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "ID cannot be empty")
    private String id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4, message = "Name length must be more then 3")
    private String name;
}
