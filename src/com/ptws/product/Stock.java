package com.ptws.product;
import com.ptws.ProductFeed;

public class Stock extends ProductFeed {
	public Stock(String symbol, String exchange, String currency) {
		this.m_symbol = symbol;
		this.m_secType = "STK";
		this.m_exchange = exchange;
		this.m_currency = currency;
	}
	
	public Stock(String symbol) {
		this.m_symbol = symbol;
		this.m_secType = "STK";
		this.m_exchange = "SMART";
		this.m_currency = "USD";
	}
}
