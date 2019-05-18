package moriakoff.kafka.pension.dao.repository;

import moriakoff.kafka.pension.dao.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository <Payment, Integer> {

}
