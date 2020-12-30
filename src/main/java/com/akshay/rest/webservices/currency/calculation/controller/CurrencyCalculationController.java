package com.akshay.rest.webservices.currency.calculation.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.akshay.rest.webservices.currency.calculation.CurrencyExchangeServiceProxy;
import com.akshay.rest.webservices.currency.calculation.entity.CurrencyCalculationBean;

@RestController
public class CurrencyCalculationController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

	@GetMapping("currency-calculation/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyCalculationBean calculateCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyCalculationBean> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyCalculationBean.class, uriVariables);
		CurrencyCalculationBean currentCalculationBean = responseEntity.getBody();
		currentCalculationBean.setQuantity(quantity);
		currentCalculationBean.setTotalCalculatedAmount(quantity.multiply(currentCalculationBean.getConversionMultiple()));
		currentCalculationBean.setPort(Integer.parseInt(environment.getProperty("server.port")));
		return currentCalculationBean;
	}
	
	@GetMapping("currency-calculation-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyCalculationBean calculateCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		CurrencyCalculationBean currentCalculationBean = currencyExchangeServiceProxy.retrieveCurrencyExchangeValue(from, to);
		currentCalculationBean.setQuantity(quantity);
		currentCalculationBean.setTotalCalculatedAmount(quantity.multiply(currentCalculationBean.getConversionMultiple()));
		return currentCalculationBean;
	}
}
