package moriakoff.kafka.pension.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContactAddressDto {

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String houseNumber;

    @NotBlank
    private String apartmentNumber;
}
