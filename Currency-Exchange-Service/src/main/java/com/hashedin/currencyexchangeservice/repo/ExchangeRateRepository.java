package com.hashedin.currencyexchangeservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hashedin.currencyexchangeservice.model.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
	
	ExchangeRate findByFromAndTo(String from, String to);

}
