package com.microservice.app;



	import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

	import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.extension.ExtendWith;
	import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.test.web.client.TestRestTemplate;
	import org.springframework.boot.test.web.server.LocalServerPort;
	import org.springframework.http.HttpEntity;
	import org.springframework.http.HttpHeaders;
	import org.springframework.http.HttpMethod;
	import org.springframework.http.ResponseEntity;
	import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hashedin.currencyexchangeservice.CurrencyExchangeServiceApplication;
import com.hashedin.currencyexchangeservice.model.ExchangeRate;
import com.hashedin.currencyexchangeservice.repo.ExchangeRateRepository;

	@ExtendWith(SpringExtension.class)
	@SpringBootTest(classes = CurrencyExchangeServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class ExchangeControllerTesting {
	    @LocalServerPort
	    private int port;

	    TestRestTemplate restTemplate;

	    HttpHeaders headers ;
	    
	    @Autowired
	    private ExchangeRateRepository repo;
	    
	    @BeforeEach
	    public void setup() {
	    	restTemplate = new TestRestTemplate();
	    	headers = new HttpHeaders();
	    }

	    @Test
	    public void testRetrieveAllExchangeRates() throws JSONException {

	        HttpEntity<String> entity = new HttpEntity<>(null, headers);

	        ResponseEntity<List> response = restTemplate.exchange(
	        		createURLWithPort("/currency-exchange"),
	               
	                HttpMethod.GET, entity, List.class);

	        List<ExchangeRate> expected = repo.findAll();

	        List<ExchangeRate> actual = response.getBody();

	        assertEquals(expected.size(), actual.size());
	    }
	    
	    @Test
	    public void testRetrieveOneExchangeRates() throws JSONException {

	        HttpEntity<String> entity = new HttpEntity<>(null, headers);

	        ResponseEntity<ExchangeRate> response = restTemplate.exchange(
	        		createURLWithPort("/currency-exchange/from/EUR/to/INR"),
	                HttpMethod.GET, entity, ExchangeRate.class);

	        ExchangeRate expected = new ExchangeRate(11002L,"EUR","INR",BigDecimal.ONE,"8000");

	        assertEquals(expected.getId(), response.getBody().getId());
	    }

	    @Test
	    public void testAddExchangeRate() {

	    	ExchangeRate expected = new ExchangeRate(11012L,"ABC","INR",BigDecimal.ONE,"8004");

	        HttpEntity<ExchangeRate> entity = new HttpEntity<>(expected, headers);

	        ResponseEntity<ExchangeRate> response = restTemplate.exchange(
	                createURLWithPort("/currency-exchange"),
	                HttpMethod.POST, entity, ExchangeRate.class);

	        ExchangeRate actual = response.getBody();

	        assertEquals(expected, actual);

	    }
	    
	    @Test
	    public void testUpdateExchangeRate() {

	    	ExchangeRate expected = new ExchangeRate(11004L,"CDE","INR",BigDecimal.ONE,"8003");

	        HttpEntity<ExchangeRate> entity = new HttpEntity<>(expected, headers);

	        ResponseEntity<ExchangeRate> response = restTemplate.exchange(
	                createURLWithPort("/currency-exchange/"+expected.getId()),
	                HttpMethod.PUT, entity, ExchangeRate.class);

	        ExchangeRate actual = response.getBody();

	        assertEquals(expected, actual);

	    }
	    
	    @Test
	    public void testDeleteExchangeRate() throws JSONException {
	    	Long id = 11006L;
	    	String expectedString = "ExchangeRate with Id "+id+" is Deleted Successfully";

	        HttpEntity<ExchangeRate> entity = new HttpEntity<>(null, headers);

	        ResponseEntity<String> response = restTemplate.exchange(
	                createURLWithPort("/currency-exchange/"+id),
	                HttpMethod.DELETE, entity, String.class);

	

	      assertEquals(expectedString, response.getBody());

	    }

	    private String createURLWithPort(String uri) {
	        return "http://localhost:" + port + uri;
	    }
	}