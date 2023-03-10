package models.responses;

import lombok.Data;

@Data
public class UpdateUserResponseDto {
    String name, job, id, updatedAt;
}
