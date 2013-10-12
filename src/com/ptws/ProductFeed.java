package com.ptws;
import java.util.HashMap;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;

//ProductFeed extends Contract, and adds custom methods and variables
public class ProductFeed extends Contract {
	//Price
	public double bidPrice;
	public double askPrice;
	public double lastPrice;
	public double highPrice;
	public double lowPrice;
	public double closePrice;
	
	//Size
	public int askSize;
	public int bidSize;
	public int lastSize;
	public int volume;
	public int avgVolume;

	//Details
	public ContractDetails details = new ContractDetails();
	
	//Historical Parameters
	public HashMap<String,HistoricalData> historicalData = new HashMap<String,HistoricalData>();
	
	public HistoricalData newHistoricalData(String name, String endDateTime, String durationStr, String barSizeSetting, String whatToShow, int useRTH, int formatDate) {
		HistoricalData h = new HistoricalData(endDateTime, durationStr, barSizeSetting, whatToShow, useRTH, formatDate);
		this.historicalData.put(name,h);
		return h;
	}

	public boolean isSynchronized() {
		//If pricing data and tick data is present, return true
		try {
			return (this.lastPrice > 0 && this.details.m_minTick > 0);
		} catch (Exception e) {
			return false;
		}
	}
}
