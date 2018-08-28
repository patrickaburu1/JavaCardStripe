package com.patrick.stripecard.controller;

import com.patrick.stripecard.ResourceNotFoundException;
import com.patrick.stripecard.model.Loan;
import com.patrick.stripecard.repository.LoanRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.patrick.stripecard.controller.ApiThread.getObject;

@RestController
public class Api {
    @Autowired
    LoanRepository loanRepository;

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public Charge Pay() throws StripeException {
        Stripe.apiKey = "sk_test_BQokikJOvBiI2HlWgH4olfQ2";

        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "usd");
        chargeMap.put("source", "tok_1CyywV2eZvKYlo2Clv6VKg78"); // obtained via Stripe.js

        try {
            Charge charge = Charge.create(chargeMap);
            System.out.println(charge);
            System.out.println(chargeMap);

            return charge;

          /*  RequestOptions requestOptions = new RequestOptions.RequestOptionsBuilder()
                    .setApiKey("sk_test_...")
                    .setStripeAccount("acct_...")
                    .build();

            Charge.list(null, requestOptions);

            Charge.retrieve("ch_18atAXCdGbJFKhCuBAa4532Z", requestOptions);*/
        } catch (StripeException e) {
            e.printStackTrace();
            return Charge.retrieve(e.getMessage());
        }

    }

    @RequestMapping(value = "getpay", method = RequestMethod.GET)
    public Iterable<Loan> getPay() {

        return loanRepository.findAll();
    }

    @PostMapping(value = "/setpay") // Map ONLY GET Requests
    public @ResponseBody Object setPay(@RequestBody Map<String, String> data) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if (data.isEmpty()) {
            data.clear();
            data.put("status", "error");
            data.put("message", "trying to pass and empty ");
        }

        System.out.println(data.get("amount"));

        Loan n = new Loan();
        n.setAmount(data.get("amount"));
        n.setFname(data.get("fname"));
        n.setInterest(data.get("interest"));
        n.setUser_id(data.get("user_id"));


        try {

            return getObject(data, n, loanRepository);

        } catch (Exception e) {
            e.printStackTrace();

            data.clear();
            data.put("status", "error");
            data.put("message", "parameter missing or invalid");

            return data;
        }

    }

    /*update record*/
    @PutMapping("/update/{id}")
    public Object  update(@PathVariable(value = "id") Integer id, @Valid @RequestBody  Map<String, String> data) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "id", id));

       /* if(noteDetails.getFirstname()!=null)
            note.setFirstname(noteDetails.getFirstname());

        if(noteDetails.getUsername()!=null)
            note.setUsername(noteDetails.getUsername());*/

        try {

            Loan updateLoan = loanRepository.save(loan);
            return updateLoan;

        } catch (Exception e) {

            return data;
        }

    }


}
