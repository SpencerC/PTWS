package com.ptws;

public class Bar {
	public String date;
	public double open; 
	public double high; 
	public double low;
	public double close; 
	public int volume;
	public int count;
	public double wap;
	public boolean hasGaps;
	
	Bar(String date, double open, double high, double low, double close, int volume, int count, double wap, boolean hasGaps) {
		this.date = date;
		this.open = open; 
		this.high = high; 
		this.low = low;
		this.close = close; 
		this.volume = volume;
		this.count = count;
		this.wap = wap;
		this.hasGaps = hasGaps;
	}
}
