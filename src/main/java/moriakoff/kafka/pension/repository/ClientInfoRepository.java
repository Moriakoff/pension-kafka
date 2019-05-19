package moriakoff.kafka.pension.repository;

import moriakoff.kafka.pension.model.entity.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientInfoRepository extends JpaRepository <ClientInfo,Integer> {

    @Query("select count(all id) from ClientInfo")
    Integer allClients();

    @Query("select sum (balance) from ClientInfo ci where ci.active=true")
    Double countAllByBalanceAndActive();



}
