package com.github.peoplemanagement.dto.request;

import com.github.peoplemanagement.entity.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Person}
 */

@Data
public class PersonRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1816609187652418321L;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Past
    @NotNull
    private LocalDate birthDate;

}