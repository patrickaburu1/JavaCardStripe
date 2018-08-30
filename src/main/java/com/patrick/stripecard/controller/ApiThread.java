package com.patrick.stripecard.controller;

import com.patrick.stripecard.model.Loan;
import com.patrick.stripecard.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@RestController
public class ApiThread {

    @Autowired
    LoanRepository loanRepository;

    private Logger logger = Logger.getLogger(ApiThread.class.getSimpleName());

    /*method to save data*/
    static Object getObject(@RequestBody Map<String, String> data1, Loan n, LoanRepository loanRepository) {
        loanRepository.save(n);

        data1.clear();
        data1.put("status", "success");
        data1.put("message", "loan applied successfully");

        return data1;
    }

    @Async("stripeExecutor")
    @PostMapping(value = "/test-thread") // Map ONLY GET Requests
    public @ResponseBody
    CompletableFuture<Object> testThread(@RequestBody Map<String, String> data) {

        if (data.isEmpty()) {
            data.clear();
            data.put("status", "error");
            data.put("message", "trying to pass and empty ");

            logger.info("\n" + "\n" + "*********  empty data  ******* " + "\n");

            return CompletableFuture.completedFuture(data);
        }
        logger.info("\n" + "\n" + "*********  data  ******* " + data + "\n");

        Loan n = new Loan();
        n.setAmount(data.get("amount"));
        n.setFname(data.get("fname"));
        n.setInterest(data.get("interest"));
        n.setUser_id(data.get("user_id"));


        try {

            loanRepository.save(n);

            data.clear();
            data.put("status", "success");
            data.put("message", "loan applied successfully");

            return CompletableFuture.completedFuture(data);

        } catch (Exception e) {
            e.printStackTrace();

            data.clear();
            data.put("status", "error");
            data.put("message", "parameter missing or invalid");
            data.put("error", e.getMessage());
            return CompletableFuture.completedFuture(data);

        }

    }

    /*get all records with thread executor */
    @Async("stripeExecutor")
    @RequestMapping(value = "getData", method = RequestMethod.GET)
    public @ResponseBody
    Object getPay(@Param(value = "amount") @Valid String amount) {
        /*has map to prepare an object*/
        Map<String, String> d = new HashMap<>();

        /*check if amount value have been passed */
        if (amount == null || amount.equals("")) {
            d.put("status", "error");
            d.put("message", "amount is required");

            logger.info("\n" + "\n" + "********* static value set  ******* " + "\n");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(d);
        }


        /*return data with response code*/
        try {

            return ResponseEntity.status(HttpStatus.OK).body(loanRepository.test(amount));


        }catch (Exception e){

            d.put("status", "failed");
            d.put("message", "something went wrong");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(d);

        }


    }

    @PostMapping(value = "/test-thread1") // Map ONLY GET Requests
    public @ResponseBody
    Object test(@RequestBody Map<String, String> data1) {

        if (data1.isEmpty()) {
            data1.clear();
            data1.put("status", "error");
            data1.put("message", "trying to pass and empty ");

            logger.info("\n" + "\n" + "*********  empty data  ******* " + "\n");

            return data1;
        }
        logger.info("\n" + "\n" + "*********  data  ******* " + data1 + "\n");

        Loan n = new Loan();
        n.setAmount(data1.get("amount"));
        n.setFname(data1.get("fname"));
        n.setInterest(data1.get("interest"));
        n.setUser_id(data1.get("user_id"));


        try {

            return getObject(data1, n, loanRepository);

        } catch (Exception e) {
            e.printStackTrace();

            data1.clear();
            data1.put("status", "error");
            data1.put("message", "parameter missing or invalid");
            data1.put("error", e.getMessage());
            return data1;

        }
    }
}
