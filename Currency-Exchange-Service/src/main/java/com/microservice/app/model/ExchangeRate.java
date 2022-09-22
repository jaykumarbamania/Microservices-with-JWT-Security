package com.microservice.app.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity(name="exchange_rate")
public class ExchangeRate {
	
	@NonNull
	@Id
	private Long id;
	
	@NonNull
	@Column(name="currency_from")
	private String from;
	
	@NonNull
	@Column(name="currency_to")
	private String to;
	
	@NonNull
	private BigDecimal conversionMultiple;
	
	private String environment;
	


}
