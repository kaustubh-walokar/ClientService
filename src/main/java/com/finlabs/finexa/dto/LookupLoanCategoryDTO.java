package com.finlabs.finexa.dto;


public class LookupLoanCategoryDTO {
	
	private byte id;
    private String description;
    private byte displayOrder;
	
    public LookupLoanCategoryDTO() {
		super();
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(byte displayOrder) {
		this.displayOrder = displayOrder;
	}
    
    
    
    

}
