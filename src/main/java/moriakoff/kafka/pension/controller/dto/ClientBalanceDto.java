package moriakoff.kafka.pension.controller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientBalanceDto {

    private Integer id;

    private String name;

    private Double balance;

    private Boolean isActive;

}
