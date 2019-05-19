package moriakoff.kafka.pension.controller;

import moriakoff.kafka.pension.model.dto.ClientBalanceDto;
import moriakoff.kafka.pension.service.PensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PensionController {

    @Autowired
    private PensionService pensionService;

    @GetMapping("pension/clients")
    public Integer getAllClients(){
        return pensionService.getAllClients();
    }

    @GetMapping("pension/clients/{id}")
    public ClientBalanceDto getClientInfo(@PathVariable(required = true, name = "id") Integer id){
        return pensionService.getClientBalance(id);
    }

    @GetMapping("pension/balance")
    public Double getPensionBalance(){
        return pensionService.getPensionBalance();
    }

}
