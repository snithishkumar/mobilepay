package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

public class DiscardJsonList extends TokenJson{
	private List<DiscardJson> discardJsons = new ArrayList<>();

	public List<DiscardJson> getDiscardJsons() {
		return discardJsons;
	}

	public void setDiscardJsons(List<DiscardJson> discardJsons) {
		this.discardJsons = discardJsons;
	}

	@Override
	public String toString() {
		return "DiscardJsonList [discardJsons=" + discardJsons + "]";
	}
	
	

}
