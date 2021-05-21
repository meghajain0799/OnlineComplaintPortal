package com.cg.sprint1.proj.status.advice;


public enum Status {
	OPEN("OPEN"),RESOLVE_VIRTUALLY("RESOLVE ONLINE"),RESOLVE_AT_HOME("RESOLVE AFTER HOME VISIT"),RESOLVED("RESOLVED"),CLOSED("CLOSED");
	
	private final String status;
	
	Status(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
}
