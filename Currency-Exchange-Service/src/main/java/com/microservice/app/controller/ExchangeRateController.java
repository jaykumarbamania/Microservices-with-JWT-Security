package com.microservice.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.app.model.ExchangeRate;
import com.microservice.app.repo.ExchangeRateRepository;

import lombok.extern.slf4j.Slf4j;

//import brave.sampler.Sampler;


@RestController 
@Slf4j
public class ExchangeRateController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeRateRepository repository;
	
	
//	@Bean
//	public Sampler defaultSampler() {
//		return Sampler.ALWAYS_SAMPLE;
//	}
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@Cacheable(value="exRate")  
	public ExchangeRate getExchangeRateValue(
			@PathVariable String from, @PathVariable String to) {
		ExchangeRate object =  repository.findByFromAndTo(from, to);
		if(object==null) {
			throw new RuntimeException("Unable to find data for:"+from+ " to:"+to);
		}
		String port = environment.getProperty("local.server.port");//for getting port no
		object.setEnvironment(port);
		log.warn("getExchangeValue found {} ",object);
		return object;
	}
	
	@GetMapping("/currency-exchange")
	@Cacheable(value="exRates")  
	public List<ExchangeRate> getAllExchangeRates() {
		log.warn("inside getAllExchangeRates");
		return repository.findAll();
	}
	
	
	@PostMapping("/currency-exchange")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ExchangeRate addNewExchangeRate(@RequestBody ExchangeRate exchangeRate) {
		return repository.save(exchangeRate);
	}
	
	@PutMapping("/currency-exchange/{id}")
	public ExchangeRate updateExchangeRate(@RequestBody ExchangeRate exchangeRate,
										@PathVariable long id) {
		Optional<ExchangeRate> currentExchangeRateObj = repository.findById(id);
		if(currentExchangeRateObj.isEmpty()) {
			throw new RuntimeException("Unable to find Exchange Rate with "+id);
		}
		ExchangeRate currentExchangeRate = currentExchangeRateObj.get();
		currentExchangeRate.setFrom(exchangeRate.getFrom());
		currentExchangeRate.setTo(exchangeRate.getTo());
		currentExchangeRate.setConversionMultiple(exchangeRate.getConversionMultiple());
		currentExchangeRate.setEnvironment(exchangeRate.getEnvironment());
		return repository.save(currentExchangeRate);
	}
	
	@DeleteMapping("/currency-exchange/{id}")
	public String addNewExchangeRate(@PathVariable long id) {
		repository.deleteById(id);
		return "ExchangeRate with Id "+id+" is Deleted Successfully";
	}
}
