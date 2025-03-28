package com.narve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.narve.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
