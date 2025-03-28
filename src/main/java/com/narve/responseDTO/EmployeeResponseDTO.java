package com.narve.responseDTO;


import java.util.List;

public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private Long departmentId;
    private List<AddressResponseDTO> addresses;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public List<AddressResponseDTO> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressResponseDTO> addresses) {
		this.addresses = addresses;
	}

}
