package in.tn.mobilepay.response.model;

import in.tn.mobilepay.entity.CounterDetailsEntity;

public class CounterDetailsJson {
	private String counterUUID;
	private String counterNumber;
	private String message;
	
	public CounterDetailsJson(){
		
	}
	
	public CounterDetailsJson(CounterDetailsEntity counterDetailsEntity){
		this.counterUUID = counterDetailsEntity.getCounterGuid();
		this.counterNumber = counterDetailsEntity.getCounterNumber();
		this.message = counterDetailsEntity.getMessage();
	}

	public String getCounterUUID() {
		return counterUUID;
	}

	public void setCounterUUID(String counterUUID) {
		this.counterUUID = counterUUID;
	}

	public String getCounterNumber() {
		return counterNumber;
	}

	public void setCounterNumber(String counterNumber) {
		this.counterNumber = counterNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CounterDetailsJson [counterUUID=" + counterUUID + ", counterNumber=" + counterNumber + ", message="
				+ message + "]";
	}
	
	

}
