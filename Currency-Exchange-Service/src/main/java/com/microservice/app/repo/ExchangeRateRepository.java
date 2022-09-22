package com.microservice.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.app.model.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
	
	ExchangeRate findByFromAndTo(String from, String to);

}
