
package com.riya.bankapp.banking_app.payloads;
import lombok.*;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String messsage;
    private boolean success;
    
}
