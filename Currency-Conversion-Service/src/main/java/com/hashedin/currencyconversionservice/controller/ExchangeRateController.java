package com.hashedin.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hashedin.currencyconversionservice.model.ExchangeRate;
import com.hashedin.currencyconversionservice.model.ExchangeRateProxy;

import jdk.internal.org.jline.utils.Log;

@RestController
public class ExchangeRateController {
	
	@Autowired
	private ExchangeRateProxy exchangeRateProxy;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	//With Feign CLient
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public ExchangeRate calculateCurrencyConversion(
			@PathVariable String from,@PathVariable String to,
			@PathVariable BigDecimal quantity){
		
		ExchangeRate exchangeRate = exchangeRateProxy.getExchangeValues(from, to);
		
		Log.warn("Exchange Rate Coversion is done and {}",exchangeRate);
		
		return new ExchangeRate(exchangeRate.getId(), from, to, quantity,
				"Running at port : "+exchangeRate.getEnvironment(), 
				quantity.multiply(exchangeRate.getConversionMultiple()),
				exchangeRate.getConversionMultiple());
	}
}
