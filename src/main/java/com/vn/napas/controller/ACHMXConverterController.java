package com.vn.napas.controller;

import com.vn.napas.service.NrtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/mx-converter")
@RequiredArgsConstructor
public class ACHMXConverterController {
    private final NrtService nrtService;

    /*
        ACH Xác nhận đã nhận bản tin với ngân hàng gửi lệnh
     */
    @PutMapping(value = "/confirm", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> confirm(@RequestBody String request) {
        return new ResponseEntity<>(nrtService.achConfrmBankSender(request), HttpStatus.OK);
    }

    /*
       ACH gửi bản tin pacs008 đến MX Converter để gửi sang ngân hàng nhận lệnh
    */
    @PutMapping(value = "/pacs-008", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> achSendPacs008(@RequestBody String request) {
        return new ResponseEntity<>(nrtService.achSendPacs008(request), HttpStatus.OK);
    }

    /*
       Ngân hàng nhận lệnh sử lí bản tin pacs.008 và gửi lại bản tin pacs.002 về ACH
    */
    @PutMapping(value = "/process-pacs-008", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processPacs008(@RequestBody String request) {
        return new ResponseEntity<>(nrtService.processJSONToXML(request), HttpStatus.OK);
    }

    /*
       ACH xác nhận đã nhận bản tin với ngân hàng nhận lệnh
    */
    @PutMapping(value = "/ach-confrm", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> achConfrmBankReceiver(@RequestBody String request) {
        return new ResponseEntity<>(nrtService.achConfirmBankReceiver(request), HttpStatus.OK);
    }

    /*
       ACH gửi bản tin camt.025 sang MX Converter để gửi sang ngân hàng nhận lệnh
    */
    @PutMapping(value = "/camt-025", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> achSendCamt025BankReceiver(@RequestBody String request) {
        return new ResponseEntity<>(nrtService.achSendCamt025BankReceiver(request), HttpStatus.OK);
    }

}
