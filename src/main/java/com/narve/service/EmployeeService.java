package com.narve.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.narve.model.Address;
import com.narve.model.Department;
import com.narve.model.Employee;
import com.narve.repository.AddressRepository;
import com.narve.repository.DepartmentRepository;
import com.narve.repository.EmployeeRepository;
import com.narve.requestDTO.EmployeeRequestDTO;
import com.narve.responseDTO.EmployeeResponseDTO;


@Service
public class EmployeeService {
	    private final EmployeeRepository employeeRepository;
	    private final DepartmentRepository departmentRepository;
	    private final ModelMapper modelMapper;
        private final AddressRepository addressRepository;

	    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
				ModelMapper modelMapper, AddressRepository addressRepository) {
			
			this.employeeRepository = employeeRepository;
			this.departmentRepository = departmentRepository;
			this.modelMapper = modelMapper;
			this.addressRepository = addressRepository;
	    }

		public ResponseEntity<String> saveEmployee(EmployeeRequestDTO employeeRequestDTO) {
	        try {
	            Employee employee = new Employee();
	            employee.setName(employeeRequestDTO.getName());
	            
	            Department department = departmentRepository.findById(employeeRequestDTO.getDepartmentId())
	                    .orElseThrow(() -> new RuntimeException("Department not found"));
	            employee.setDepartment(department);
	            
	            List<Address> addresses = employeeRequestDTO.getAddresses().stream().map(addressDTO -> {
	                Address address = modelMapper.map(addressDTO, Address.class);
	                address.setEmployee(employee);
	                return address;
	            }).collect(Collectors.toList());
	            
	            employee.setAddresses(addresses);
	            
	            employeeRepository.save(employee);
	            return ResponseEntity.ok("Record saved successfully");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error saving record: " + e.getMessage());
	        }
	    }
	    
	   

		

		public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
	        try {
	            List<EmployeeResponseDTO> employees = employeeRepository.findAll().stream()
	                    .map(emp -> modelMapper.map(emp, EmployeeResponseDTO.class))
	                    .collect(Collectors.toList());
	            return ResponseEntity.ok(employees);
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().build();
	        }
	    }
	    
	    public ResponseEntity<String> deleteEmployee(Long id) {
	        try {
	            if (employeeRepository.existsById(id)) {
	                employeeRepository.deleteById(id);
	                return ResponseEntity.ok("Employee deleted successfully");
	            } else {
	                return ResponseEntity.status(404).body("Employee not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().body("Error deleting employee: " + e.getMessage());
	        }
	    }
	    
	    public ResponseEntity<?> updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) {
	        try {
	            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
	            
	            if (optionalEmployee.isPresent()) {
	                Employee employee = optionalEmployee.get();
	                
	               
	                employee.setName(employeeRequestDTO.getName());

	                
	                if (!employee.getDepartment().getId().equals(employeeRequestDTO.getDepartmentId())) {
	                    Department department = departmentRepository.findById(employeeRequestDTO.getDepartmentId())
	                            .orElseThrow(() -> new RuntimeException("Department not found"));
	                    employee.setDepartment(department);
	                }

	               
	                addressRepository.deleteAll(employee.getAddresses()); 

	                List<Address> updatedAddresses = employeeRequestDTO.getAddresses().stream().map(addressDTO -> {
	                    Address address = modelMapper.map(addressDTO, Address.class);
	                    address.setEmployee(employee); 
	                    return address;
	                }).collect(Collectors.toList());

	                employee.setAddresses(updatedAddresses);
	                employeeRepository.save(employee); 

	                
	                EmployeeResponseDTO responseDTO = modelMapper.map(employee, EmployeeResponseDTO.class);
	                responseDTO.setDepartmentId(employee.getDepartment().getId());

	                return ResponseEntity.ok(responseDTO);
	            } else {
	                return ResponseEntity.status(404).body("Employee not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().body("Error updating employee: " + e.getMessage());
	        }
	    }
	 }
