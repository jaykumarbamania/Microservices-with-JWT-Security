package com.microservice.app.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.app.model.ExchangeRate;
import com.microservice.app.model.ExchangeRateProxy;

@RestController
public class ExchangeRateController {
	
	@Autowired
	private ExchangeRateProxy exchangeRateProxy;
	
//	//With Feign CLient
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public ExchangeRate calculateCurrencyConversion(
			@PathVariable String from,@PathVariable String to,
			@PathVariable BigDecimal quantity){
		
		ExchangeRate exchangeRate = exchangeRateProxy.getExchangeValues(from, to);
		
		return new ExchangeRate(exchangeRate.getId(), from, to, quantity,
				"Running at port : "+exchangeRate.getEnvironment(), 
				quantity.multiply(exchangeRate.getConversionMultiple()),
				exchangeRate.getConversionMultiple());
	}
}
