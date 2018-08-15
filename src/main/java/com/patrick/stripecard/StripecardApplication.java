package com.patrick.stripecard;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class StripecardApplication {

    public static void main(String[] args) {

        SpringApplication.run(StripecardApplication.class, args);
    }
}
