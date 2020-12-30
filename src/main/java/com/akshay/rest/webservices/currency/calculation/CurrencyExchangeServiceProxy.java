package com.akshay.rest.webservices.currency.calculation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.akshay.rest.webservices.currency.calculation.entity.CurrencyCalculationBean;

//Feign makes it easy to talk to other restful microservices
@FeignClient(name="currency-exchange-service", url="localhost:8000")
public interface CurrencyExchangeServiceProxy {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	CurrencyCalculationBean retrieveCurrencyExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
