package com.narve.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.narve.model.Department;
import com.narve.model.Employee;
import com.narve.repository.AddressRepository;
import com.narve.repository.DepartmentRepository;
import com.narve.repository.EmployeeRepository;
import com.narve.requestDTO.EmployeeRequestDTO;
import com.narve.responseDTO.AddressResponseDTO;
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
	            	            
	            employeeRepository.save(employee);
	            return ResponseEntity.ok("Record saved successfully");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error saving record: " + e.getMessage());
	        }
	    }
	    
	   

		 public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
		        try {
		            List<Employee> employees = employeeRepository.findAll();

		            List<EmployeeResponseDTO> dtoList = employees.stream().map(employee -> {
		                EmployeeResponseDTO dto = modelMapper.map(employee, EmployeeResponseDTO.class);
		                dto.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);

		                List<AddressResponseDTO> addressDTOs = employee.getAddresses().stream()
		                        .map(address -> modelMapper.map(address, AddressResponseDTO.class))
		                        .collect(Collectors.toList());

		                dto.setAddresses(addressDTOs);
		                return dto;
		            }).collect(Collectors.toList());

		            return ResponseEntity.ok(dtoList);
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

	              Department  department=departmentRepository.findById(employeeRequestDTO.getDepartmentId()).orElseThrow(() ->new RuntimeException("department not found"));
                  employee.setDepartment(department);

	              Employee  employeesaved=employeeRepository.save(employee); 

	                return new ResponseEntity<>("employee updated",HttpStatus.OK);
	            } else {
	                return ResponseEntity.status(404).body("Employee not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().body("Error updating employee: " + e.getMessage());
	        }
	    }
	 }
