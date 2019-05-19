package moriakoff.kafka.pension.listener;

import moriakoff.kafka.pension.model.dto.PaymentDto;
import moriakoff.kafka.pension.service.PensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentKafkaListener {

    @Autowired
    public PensionService pensionService;

    @KafkaListener(topics = "paymentTest",containerFactory = "paymentDtoKafkaContainerFactory")
    public void paymentListener(PaymentDto paymentDto){
        pensionService.paymentOperation(paymentDto);
    }


}
