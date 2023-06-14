package com.github.peoplemanagement.dto.response;

import com.github.peoplemanagement.entity.Person;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link Person}
 */

@Data
public class PersonDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4283686762833190013L;

    private UUID personId;

    private String name;

    private LocalDate birthDate;

}