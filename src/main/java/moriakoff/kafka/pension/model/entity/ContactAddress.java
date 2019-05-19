package moriakoff.kafka.pension.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
public class ContactAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String country;

    @NotBlank
    private String houseNumber;

    @NotBlank
    private String apartmentNumber;
}
