package com.github.peoplemanagement.controller.utility;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@UtilityClass
public class Location {

    public static URI create(final UUID savedObjectId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedObjectId)
                .toUri();
    }

}
