package com.retailter.calc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.calc.entity.Customer;
import com.retailer.calc.model.Rewards;
import com.retailer.calc.repository.CustomerRepository;
import com.retailer.calc.service.RewardsService;

@RestController
@RequestMapping("/customers")
public class CustomerRewardsController {

	@Autowired
	RewardsService rewardsService;

	@Autowired
	CustomerRepository customerRepository;

	@GetMapping(value = "/{customerId}/rewards", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId) {
		Customer customer = customerRepository.findByCustomerId(customerId);
		if (customer == null) {
			throw new RuntimeException(String.format("Customer with Id %s missing", customerId));
		}
		Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
		return new ResponseEntity<>(customerRewards, HttpStatus.OK);
	}

}
