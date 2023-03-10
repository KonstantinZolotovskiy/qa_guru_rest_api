package models.responses;

import lombok.Data;

@Data
public class CreateUserResponseDto {
    String name, job, id, createdAt;
}
