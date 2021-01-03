package com.akshay.rest.webservices.currency.calculation;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.akshay.rest.webservices.currency.calculation.entity.CurrencyCalculationBean;

//Feign makes it easy to talk to other restful microservices
@FeignClient(name="netflix-zuul-api-gateway-server")
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

	@GetMapping("currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	CurrencyCalculationBean retrieveCurrencyExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
