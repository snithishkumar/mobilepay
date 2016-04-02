package in.tn.mobilepay.request.model;

public class GetLuggageList extends TokenJson {

	private long startTime;
	private long endTime;

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "GetLuggageList [startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
