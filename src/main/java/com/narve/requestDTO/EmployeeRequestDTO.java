package com.narve.requestDTO;

import java.util.List;

public class EmployeeRequestDTO {
	private String name;
    private Long departmentId;
    private List<AddressRequestDTO> addresses;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public List<AddressRequestDTO> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressRequestDTO> addresses) {
		this.addresses = addresses;
	}
    
    

}
