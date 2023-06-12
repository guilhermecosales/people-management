package com.github.peoplemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons")
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 6619573066660338638L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID personId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private Set<Address> address = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personId, person.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId);
    }
}
