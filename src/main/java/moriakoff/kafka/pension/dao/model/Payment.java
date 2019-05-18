package moriakoff.kafka.pension.dao.model;

import lombok.*;
import moriakoff.kafka.pension.dao.model.type.TransactionType;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Positive
    private Double transactionAmount;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private ClientInfo clientInfo;

}
