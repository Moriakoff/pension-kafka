package moriakoff.kafka.pension.repository;

import moriakoff.kafka.pension.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository <Payment, Integer> {

}
