package models.requests;

import lombok.Data;

@Data
public class RegistrationUserRequestDto {
    String email, password;
}
