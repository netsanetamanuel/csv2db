package com.devnet.csv2db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devnet.csv2db.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{

}
