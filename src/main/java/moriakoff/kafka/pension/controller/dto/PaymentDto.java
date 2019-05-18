package moriakoff.kafka.pension.controller.dto;

import lombok.*;
import moriakoff.kafka.pension.dao.model.type.TransactionType;

import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentDto {

    private TransactionType transactionType;

    @Positive
    private Double transactionAmount;

    private ClientInfoDto clientInfoDto;


}
