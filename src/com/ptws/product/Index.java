package com.ptws.product;
import com.ptws.ProductFeed;

public class Index extends ProductFeed {
	public Index(String symbol) {
		this.m_symbol = symbol;
		this.m_secType = "IND";
	}
}
