package com.microservice.app.batch;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.microservice.app.model.ExchangeRate;

public class ExchangeRateProcessor implements ItemProcessor<ExchangeRate, ExchangeRate> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateProcessor.class);

    @Override
    public ExchangeRate process(final ExchangeRate exchangeRate) throws Exception {
    	Long id = exchangeRate.getId();
        String from = exchangeRate.getFrom().toUpperCase();
        String to = exchangeRate.getTo().toUpperCase();
        String environment = exchangeRate.getEnvironment().toUpperCase();
        BigDecimal conversionMultiple = exchangeRate.getConversionMultiple();

        ExchangeRate transformedExchangeRatee = new ExchangeRate(id, from, to, conversionMultiple, environment);
        LOGGER.info("Converting ( {} ) into ( {} )", exchangeRate, transformedExchangeRatee);

        return transformedExchangeRatee;
    }
}