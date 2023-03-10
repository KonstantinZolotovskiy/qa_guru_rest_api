package models.requests;

import lombok.Data;

@Data
public class LoginUserRequestDto {
    String email, password;
}
