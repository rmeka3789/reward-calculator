package com.retailer.calc.repository;

import org.springframework.data.repository.CrudRepository;

import com.retailer.calc.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	public Customer findByCustomerId(Long customerId);
}
