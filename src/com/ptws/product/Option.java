package com.ptws.product;
import com.ptws.ProductFeed;

public class Option extends ProductFeed {
	public double delta;
	public double optPrice;
	public double pvDividend;
	public double gamma;
	public double vega;
	public double theta;
	public double undPrice;
	
	public Option(String underlyingSymbol, String type, double strike, String expiry) {
		this.m_symbol = underlyingSymbol;
		this.m_secType = "OPT";
		this.m_right = type;
		this.m_strike = strike;
		this.m_expiry = expiry;
	}
}
