package models.requests;

import lombok.Data;

@Data
public class CreateUserRequestDto {
    String name, job;
}
