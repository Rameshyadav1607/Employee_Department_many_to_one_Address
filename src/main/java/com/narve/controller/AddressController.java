package com.narve.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.narve.requestDTO.AddressRequestDTO;
import com.narve.requestDTO.AddressUpdateRequestDTO;
import com.narve.responseDTO.AddressResponseDTO;
import com.narve.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	private AddressService addressService;
	@PostMapping("/save")
	public ResponseEntity<String>  createAddress(@RequestBody AddressRequestDTO addressRequestDTO ){
		return addressService.createAddress(addressRequestDTO);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteAddressById(@PathVariable Long id){
		return addressService.deleteAddressById(id);
		
	}
	
	@DeleteMapping("/employeeI/{id}")
	public ResponseEntity<String> deleteAddressByEmpId(@PathVariable Long id){
		return addressService.deleteAddressByEmpId(id);
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateAddess(@PathVariable Long id,@RequestBody AddressUpdateRequestDTO addressRequestDTO){
		return addressService.updateAddess(id,addressRequestDTO);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<AddressResponseDTO>> getallAddress(){
		return addressService.getAllAddress();
		
	}

}
