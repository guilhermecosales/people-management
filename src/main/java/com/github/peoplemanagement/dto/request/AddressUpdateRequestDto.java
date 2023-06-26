package com.github.peoplemanagement.dto.request;

import com.github.peoplemanagement.entity.Address;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link Address}
 */

@Data
public class AddressUpdateRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -430410177981254152L;

    @Size(min = 5, max = 60)
    private String street;

    @Size(min = 8, max = 8)
    private String zipCode;

    @Size(min = 1, max = 5)
    private String number;

    @Size(min = 3, max = 20)
    private String city;

    private boolean isDefault;

}