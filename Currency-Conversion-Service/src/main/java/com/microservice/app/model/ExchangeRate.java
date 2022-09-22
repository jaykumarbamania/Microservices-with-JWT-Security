package com.microservice.app.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {
	
	private Long id;
	private String from;
	private String to;
	private BigDecimal quantity;
	private String environment;
	private BigDecimal conversionMultiple;
	private BigDecimal totalCalculatedAmount;
	

}
