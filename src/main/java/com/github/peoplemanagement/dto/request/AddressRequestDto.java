package com.github.peoplemanagement.dto.request;

import com.github.peoplemanagement.dto.response.PersonDto;
import com.github.peoplemanagement.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link Address}
 */

@Data
public class AddressRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6807677583474723367L;

    @NotBlank
    @Size(min = 5, max = 60)
    private String street;

    @NotBlank
    @Size(min = 8, max = 8)
    private String zipCode;

    @NotBlank
    @Size(min = 1, max = 5)
    private String number;

    @NotBlank
    @Size(min = 3, max = 20)
    private String city;

    private boolean isDefault = true;

    @NotNull
    private PersonDto person;

}