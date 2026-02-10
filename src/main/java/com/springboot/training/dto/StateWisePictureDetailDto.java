package com.springboot.training.dto;

public class StateWisePictureDetailDto {
	
	
	private Integer st_code;

    private String st_name;

    private String photo_path;
    
    
    
    public StateWisePictureDetailDto(Integer st_code, String st_name, String photo_path) {
    	
    	super();
    	this.st_code=st_code;
    	this.st_name=st_name;
    	this.photo_path=photo_path;
    }
    
	public Integer getSt_code() {
		return st_code;
	}

	public void setSt_code(Integer st_code) {
		this.st_code = st_code;
	}

	public String getSt_name() {
		return st_name;
	}

	public void setSt_name(String st_name) {
		this.st_name = st_name;
	}

	public String getPhoto_path() {
		return photo_path;
	}

	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}

   
	
	
	
	

}
