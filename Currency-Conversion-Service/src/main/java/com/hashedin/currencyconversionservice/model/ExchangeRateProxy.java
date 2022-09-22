package com.hashedin.currencyconversionservice.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="currency-exchange-service", url="localhost:8000") 
public interface ExchangeRateProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeRate getExchangeValues(@PathVariable String from,
				@PathVariable String to
			);

}
