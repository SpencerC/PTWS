package com.ptws.product;
import com.ptws.ProductFeed;

public class Future extends ProductFeed {
	public Future(String symbol, String expiry) {
		this.m_symbol = symbol;
		this.m_secType = "FUT";
		this.m_exchange = "SMART";
		this.m_currency = "USD";
		this.m_expiry = "USD";
	}
}
