package com.narve.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.narve.model.Address;
import com.narve.model.Employee;
import com.narve.repository.AddressRepository;
import com.narve.repository.EmployeeRepository;
import com.narve.requestDTO.AddressRequestDTO;
import com.narve.requestDTO.AddressUpdateRequestDTO;
import com.narve.responseDTO.AddressResponseDTO;

@Service
public class AddressService {
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<String> createAddress(AddressRequestDTO addressRequestDTO) {
	    try {
	       
	        Employee employee = employeeRepository.findById(addressRequestDTO.getEmpId())
	                .orElseThrow(() -> new RuntimeException("Error: Employee with ID " + addressRequestDTO.getEmpId() + " not found"));

	        List<Address> addressList = addressRequestDTO.getAddressDTO().stream()
	                .map(dto -> {
	                    Address address = mapper.map(dto, Address.class);
	                    address.setEmployee(employee);  
	                    return address;
	                }).collect(Collectors.toList());

	        addressRepository.saveAll(addressList);

	        return ResponseEntity.status(HttpStatus.CREATED).body("Address saved successfully");

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred: " + e.getMessage());
	    }
	}

	public ResponseEntity<Void> deleteAddressById(Long id) {
		
		         Optional<Address>    isvailable=addressRepository.findById(id);
		         if(isvailable.isPresent()) {
		        	 addressRepository.deleteById(id);
		        	 return new ResponseEntity<>(HttpStatus.CONFLICT);
		         }
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<String> deleteAddressByEmpId(Long id) {
	    try {
	       
	        Employee employee = employeeRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

	        
	        List<Address> addresses = addressRepository.findByEmployeeId(id);
	        
	        if (addresses.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No addresses found for employee with ID: " + id);
	        }

	       
	        addressRepository.deleteAll(addresses);

	        return ResponseEntity.status(HttpStatus.OK).body("Addresses deleted for employee ID: " + id);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error deleting addresses: " + e.getMessage());
	    }
	}

	public ResponseEntity<String> updateAddess(Long id, AddressUpdateRequestDTO addressUpdateRequestDTO) {
		
		                  Optional<Address>     addressopt=addressRepository.findById(id);
		                    if(addressopt.isPresent()) {
		                    	          Address      address=addressopt.get() ;
		                    	          address.setCity(addressUpdateRequestDTO.getCity());
		                    	          address.setState(addressUpdateRequestDTO.getState());
		                    	          address.setStreet(addressUpdateRequestDTO.getStreet());
		                   Optional<Employee> 	      employeeopt=employeeRepository.findById(addressUpdateRequestDTO.getEmpId());
		                   
		                   if(employeeopt.isPresent()) {
		                	  // address.equals(employeeopt);
		                	   address.setEmployee(employeeopt.get());
		                	  
		                   }
		                   else {
		                	   return new ResponseEntity<>("employee not found",HttpStatus.NOT_FOUND);
		                    }
		                           addressRepository.save(address);
		                           return new ResponseEntity<>("updated address :",HttpStatus.OK);
		                     }
		                     
		            	   return new ResponseEntity<>("department  not found",HttpStatus.NOT_FOUND);
		       
		
	}



	public ResponseEntity<List<AddressResponseDTO>> getAllAddress() {
	    List<Address> addressList = addressRepository.findAll();

	    List<AddressResponseDTO> dtoList = addressList.stream().map(address -> {
	        AddressResponseDTO dto = mapper.map(address, AddressResponseDTO.class);
	        dto.setEmpId(address.getEmployee().getId()); // Manually set empId
	        return dto;
	    }).collect(Collectors.toList());

	    return ResponseEntity.ok(dtoList);
	}




	
		

 

}
