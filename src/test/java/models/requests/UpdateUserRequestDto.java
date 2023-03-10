package models.requests;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    String name, job;
}
