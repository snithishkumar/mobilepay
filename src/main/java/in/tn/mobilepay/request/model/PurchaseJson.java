package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

public class PurchaseJson extends TokenJson {

	private String billNumber;
	private String purchaseUuid;
	private String dateTime;
	private String userMobile;
	private Boolean isHomeDeliver;
	private List<PurchaseDetailsJson> purchaseDetails = new ArrayList<PurchaseDetailsJson>();
	private List<AmountDetailsJson> amountDetails = new ArrayList<AmountDetailsJson>();
	private String totalAmount;
	private String payableAmount;
	
	
	/*public static void main(String[] args) {
		List<PurchaseDetailsJson> detailsJsons = new ArrayList<PurchaseDetailsJson>();
		PurchaseDetailsJson purchaseDetailsJson = new PurchaseDetailsJson();
		purchaseDetailsJson.setAmount("1000");
		purchaseDetailsJson.setDescription("POLO Shirts");
		purchaseDetailsJson.setItemNo(1);
		purchaseDetailsJson.setQuantity(2);
		detailsJsons.add(purchaseDetailsJson);
		
		purchaseDetailsJson = new PurchaseDetailsJson();
		purchaseDetailsJson.setAmount("2000");
		purchaseDetailsJson.setDescription("Lee Jeans");
		purchaseDetailsJson.setItemNo(2);
		purchaseDetailsJson.setQuantity(1);
		detailsJsons.add(purchaseDetailsJson);
		
		List<AmountDetailsJson> amountDetailsJsons = new ArrayList<AmountDetailsJson>();
		AmountDetailsJson amountDetailsJson = new AmountDetailsJson();
		amountDetailsJson.setKey("Vat");
		amountDetailsJson.setValue("100");
		amountDetailsJsons.add(amountDetailsJson);
		amountDetailsJson = new AmountDetailsJson();
		amountDetailsJson.setKey("Service Tax");
		amountDetailsJson.setValue("300");
		amountDetailsJsons.add(amountDetailsJson);
		
		PurchaseJson purchaseJson = new  PurchaseJson();
		purchaseJson.setBillNumber("001");
		purchaseJson.setClientToken("clientToekn");
		purchaseJson.setDateTime(String.valueOf(System.currentTimeMillis()));
		purchaseJson.setIsHomeDeliver(false);
		purchaseJson.setPayableAmount("3500");
		purchaseJson.setPurchaseUuid(UUID.randomUUID().toString());
		purchaseJson.setServerToken("clientToekn");
		purchaseJson.setTotalAmount("3500");
		purchaseJson.setUserMobile("9942320690");
		purchaseJson.getAmountDetails().addAll(amountDetailsJsons);
		purchaseJson.getPurchaseDetails().addAll(detailsJsons);
		Gson gson = new Gson();
		String ddd = gson.toJson(purchaseJson);
		System.out.println(ddd);
	}*/

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Boolean getIsHomeDeliver() {
		return isHomeDeliver;
	}

	public void setIsHomeDeliver(Boolean isHomeDeliver) {
		this.isHomeDeliver = isHomeDeliver;
	}

	public List<PurchaseDetailsJson> getPurchaseDetails() {
		return purchaseDetails;
	}

	public void setPurchaseDetails(List<PurchaseDetailsJson> purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}

	public List<AmountDetailsJson> getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(List<AmountDetailsJson> amountDetails) {
		this.amountDetails = amountDetails;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(String payableAmount) {
		this.payableAmount = payableAmount;
	}
	
	

	public String getPurchaseUuid() {
		return purchaseUuid;
	}

	public void setPurchaseUuid(String purchaseUuid) {
		this.purchaseUuid = purchaseUuid;
	}

	@Override
	public String toString() {
		return "PurchaseJson [billNumber=" + billNumber + ", purchaseUuid=" + purchaseUuid + ", dateTime=" + dateTime
				+ ", userMobile=" + userMobile + ", isHomeDeliver=" + isHomeDeliver + ", purchaseDetails="
				+ purchaseDetails + ", amountDetails=" + amountDetails + ", totalAmount=" + totalAmount
				+ ", payableAmount=" + payableAmount + "]";
	}

}
