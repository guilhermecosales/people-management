package com.github.peoplemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.peoplemanagement.entity.Address;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Address}
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AddressDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4079357399019791958L;

    private UUID addressId;
    private String street;
    private String zipCode;
    private String number;
    private String city;
    private boolean isDefault;

}