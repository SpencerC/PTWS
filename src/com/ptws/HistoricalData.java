package com.ptws;
import java.util.ArrayList;

public class HistoricalData extends ArrayList<Bar> {
	public String endDateTime;
	public String durationStr;
	public String barSizeSetting;
	public String whatToShow;
	public int useRTH;
	public int formatDate;
	
	HistoricalData(String endDateTime, String durationStr, String barSizeSetting, String whatToShow, int useRTH, int formatDate) {
		this.endDateTime = endDateTime;
		this.durationStr = durationStr;
		this.barSizeSetting = barSizeSetting;
		this.whatToShow = whatToShow;
		this.useRTH = useRTH;
		this.formatDate = formatDate;
		
	}
	
	public void addBar(String date, double open, double high, double low, double close, int volume, int count, double wap, boolean hasGaps) {
		Bar b = new Bar(date, open, high, low, close, volume, count, wap, hasGaps);
		this.add(b);
	}
	
	public boolean isSynchronized() {
		//If the latest open price is -1.0, we have all of the data
		try {
			return (this.get(this.size()-1).open == -1.0);
		} catch (Exception e) {
			return false;
		}
	}
}
