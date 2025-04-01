package com.narve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.narve.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

	List<Address> findByEmployeeId(Long id);

}
