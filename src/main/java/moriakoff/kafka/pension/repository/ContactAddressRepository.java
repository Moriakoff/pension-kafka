package moriakoff.kafka.pension.repository;

import moriakoff.kafka.pension.model.entity.ContactAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactAddressRepository extends JpaRepository<ContactAddress,Integer> {

    Optional<ContactAddress> findByCityAndStreetAndHouseNumberAndApartmentNumber(String city,String street,
                                                                                 String houseNumber,
                                                                                 String apartmentNumber);

}
