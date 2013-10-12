package com.ptws.product;
import com.ptws.ProductFeed;

public class Cash extends ProductFeed {
	//Is the does the contract represent exactly what the user was look for, or is it just showing inverse price data?
	public boolean swapped = false;
	
	public Cash(String symbol, String exchange, String currency) {
		this.m_symbol = symbol;
		this.m_secType = "CASH";
		this.m_exchange = exchange;
		this.m_currency = currency;
	}
	
	public Cash(String cur1, String cur2) {
		this.m_symbol = cur1;
		this.m_secType = "CASH";
		this.m_exchange = "IDEALPRO";
		this.m_currency = cur2;
	}
	
	public void swap() {
		if (!this.swapped) {
			//Swap the symbol and currency 
			String s = this.m_symbol;
			this.m_symbol = this.m_currency;
			this.m_currency = s;
			//Change the status of swapped
			this.swapped = true;
		}
	}
}
