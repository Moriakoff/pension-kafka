package moriakoff.kafka.pension.service;

import moriakoff.kafka.pension.controller.dto.ClientBalanceDto;
import moriakoff.kafka.pension.controller.dto.ClientInfoDto;
import moriakoff.kafka.pension.controller.dto.ContactAddressDto;
import moriakoff.kafka.pension.controller.dto.PaymentDto;
import moriakoff.kafka.pension.dao.model.ClientInfo;
import moriakoff.kafka.pension.dao.model.ContactAddress;
import moriakoff.kafka.pension.dao.model.Payment;
import moriakoff.kafka.pension.dao.model.type.TransactionType;
import moriakoff.kafka.pension.dao.repository.ClientInfoRepository;
import moriakoff.kafka.pension.dao.repository.ContactAddressRepository;
import moriakoff.kafka.pension.dao.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PensionService {

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ContactAddressRepository contactAddressRepository;

    @Autowired
    private ModelMapper modelMapper;


    @KafkaListener(topics = "paymentTest", containerFactory = "paymentDtoKafkaContainerFactory")
    public void paymentOperation(PaymentDto paymentDto) {

        Payment payment = savePayment(paymentDto);


        if (paymentDto.getTransactionType().equals(TransactionType.DEPOSIT)) {
            deposit(payment);
        }
        else if (paymentDto.getTransactionType().equals(TransactionType.WITHDRAW)) {
            withdraw(payment);
        }
        else if (paymentDto.getTransactionType().equals(TransactionType.FREEZE)) {
            freeze(payment);
        }
        else if (paymentDto.getTransactionType().equals(TransactionType.DEFREEZE)) {
            defreeze(payment);
        }


        System.out.println(paymentDto.toString());
    }

    @Transactional
    public Payment savePayment(PaymentDto paymentDto) {
        Payment payment = paymentRepository.save(new Payment());
        ClientInfoDto clientInfoDto = paymentDto.getClientInfoDto();
        ContactAddressDto contactAddressDto = clientInfoDto.getContactAddressDto();


        ClientInfo clientInfo =
                clientInfoRepository.findById(paymentDto.getClientInfoDto().getId())
                        .orElse(clientInfoDtoToEntity(paymentDto.getClientInfoDto()));

        ContactAddress contactAddress =
                contactAddressRepository.findByCityAndStreetAndHouseNumberAndApartmentNumber
                        (contactAddressDto.getCity(),
                                contactAddressDto.getStreet(),
                                contactAddressDto.getHouseNumber(),
                                contactAddressDto.getApartmentNumber())
                        .orElse(contactAddressDtoToEntity(paymentDto.getClientInfoDto().getContactAddressDto()));


        contactAddress = contactAddressRepository.save(contactAddress);

        clientInfo.setContactAddress(contactAddress);

        clientInfo = clientInfoRepository.save(clientInfo);


        payment.setTransactionType(paymentDto.getTransactionType());
        payment.setTransactionAmount(paymentDto.getTransactionAmount());
        payment.setClientInfo(clientInfo);

        paymentRepository.save(payment);

        return payment;
    }

    @Transactional
    public void deposit(Payment payment) {
        ClientInfo client = payment.getClientInfo();
        System.out.println(client);

        if (client.isActive()) {
            double balance = client.getBalance() + payment.getTransactionAmount();
            client.setBalance(balance);
            clientInfoRepository.save(client);
        }
    }

    @Transactional
    public void withdraw(Payment payment) {
        ClientInfo client = payment.getClientInfo();

        if (client.isActive()) {
            double balance = client.getBalance() - payment.getTransactionAmount();
            client.setBalance(balance);
            clientInfoRepository.save(client);
        }

    }

    @Transactional
    public void freeze(Payment payment) {
        ClientInfo client = payment.getClientInfo();
        client.setActive(false);
        clientInfoRepository.save(client);
    }

    @Transactional
    public void defreeze(Payment payment) {
        ClientInfo client = payment.getClientInfo();
        client.setActive(true);
        clientInfoRepository.save(client);
    }


    @Transactional(readOnly = true)
    public Integer getAllClients() {
        return clientInfoRepository.allClients();
    }

    @Transactional(readOnly = true)
    public ClientBalanceDto getClientBalance(Integer id) {
        ClientInfo client = clientInfoRepository.getOne(id);

        return ClientBalanceDto.builder()
                .id(id)
                .name(client.getFirstName() + " " + client.getLastName())
                .balance(client.getBalance())
                .isActive(client.isActive())
                .build();
    }

    @Transactional(readOnly = true)
    public Double getPensionBalance() {
        return clientInfoRepository.countAllByBalanceAndActive();
    }

    public Payment paymentDtoToEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .clientInfo(clientInfoDtoToEntity(paymentDto.getClientInfoDto()))
                .transactionType(paymentDto.getTransactionType())
                .transactionAmount(paymentDto.getTransactionAmount())
                .build();
    }

    private ClientInfo clientInfoDtoToEntity(ClientInfoDto clientInfoDto) {
        System.out.println(clientInfoDto.getId());
        return ClientInfo.builder()
                .id(clientInfoDto.getId())
                .firstName(clientInfoDto.getFirstName())
                .lastName(clientInfoDto.getLastName())
                .phoneNumber(clientInfoDto.getPhoneNumber())
                .email(clientInfoDto.getEmail())
                .balance(0.)
                .active(true)
                .dateOfBirth(clientInfoDto.getDateOfBirth())
                .contactAddress(contactAddressDtoToEntity(clientInfoDto.getContactAddressDto()))
                .build();

    }

    private ContactAddress contactAddressDtoToEntity(ContactAddressDto contactAddressDto) {
        return ContactAddress.builder()
                .apartmentNumber(contactAddressDto.getApartmentNumber())
                .city(contactAddressDto.getCity())
                .country(contactAddressDto.getCountry())
                .houseNumber(contactAddressDto.getHouseNumber())
                .street(contactAddressDto.getStreet())
                .build();
    }
}
