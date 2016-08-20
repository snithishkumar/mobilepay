package in.tn.mobilepay.rest.json;

import java.util.ArrayList;
import java.util.List;

public class PurchaseStatus {
	
	private List<CommonPurchaseData> unPaidData = new ArrayList<>();
	private List<PaiedPurchaseDetails> paidData = new ArrayList<>();
	private List<HistoryPurchaseData> historyData = new ArrayList<>();
	public List<CommonPurchaseData> getUnPaidData() {
		return unPaidData;
	}
	public void setUnPaidData(List<CommonPurchaseData> unPaidData) {
		this.unPaidData = unPaidData;
	}
	public List<PaiedPurchaseDetails> getPaidData() {
		return paidData;
	}
	public void setPaidData(List<PaiedPurchaseDetails> paidData) {
		this.paidData = paidData;
	}
	public List<HistoryPurchaseData> getHistoryData() {
		return historyData;
	}
	public void setHistoryData(List<HistoryPurchaseData> historyData) {
		this.historyData = historyData;
	}
	@Override
	public String toString() {
		return "PurchaseStatus [unPaidData=" + unPaidData + ", paidData=" + paidData + ", historyData=" + historyData
				+ "]";
	}
	
	

}
