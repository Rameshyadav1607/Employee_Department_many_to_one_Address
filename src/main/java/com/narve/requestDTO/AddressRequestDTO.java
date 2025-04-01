package com.narve.requestDTO;

import java.util.List;

public class AddressRequestDTO {
	
	private Long empId;
	private List<AddressDTO> addressDTO;
	
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public List<AddressDTO> getAddressDTO() {
		return addressDTO;
	}
	public void setAddressDTO(List<AddressDTO> addressDTO) {
		this.addressDTO = addressDTO;
	}
	

}
