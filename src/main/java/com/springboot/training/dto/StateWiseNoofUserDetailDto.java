package com.springboot.training.dto;

public class StateWiseNoofUserDetailDto {
	
	private Integer st_code;

    private String st_name;

    private Integer stuser;
    
    private Integer stparticipant;

    private Integer stpassparticipant;
    
    private Integer stfailparticipant;
    
    
    
    public StateWiseNoofUserDetailDto(Integer st_code, String st_name, Integer stuser, Integer stparticipant, 
    		Integer stpassparticipant, Integer stfailparticipant)  {
    	
    	super();
    	this.st_code=st_code;
    	this.st_name=st_name;
    	this.stuser=stuser;
    	this.stparticipant=stparticipant;
    	this.stpassparticipant=stpassparticipant;
    	this.stfailparticipant=stfailparticipant;
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

	public Integer getStuser() {
		return stuser;
	}

	public void setStuser(Integer stuser) {
		this.stuser = stuser;
	}

	public Integer getStparticipant() {
		return stparticipant;
	}

	public void setStparticipant(Integer stparticipant) {
		this.stparticipant = stparticipant;
	}

	public Integer getStpassparticipant() {
		return stpassparticipant;
	}

	public void setStpassparticipant(Integer stpassparticipant) {
		this.stpassparticipant = stpassparticipant;
	}

	public Integer getStfailparticipant() {
		return stfailparticipant;
	}

	public void setStfailparticipant(Integer stfailparticipant) {
		this.stfailparticipant = stfailparticipant;
	}
    
    
	
	
	
	
	

}
