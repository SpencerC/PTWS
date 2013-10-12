package com.ptws;

import java.util.Vector;
import java.util.HashMap;;
import java.math.*;
import java.util.Random;

import com.ib.client.*;
import com.ptws.product.*;

public class DataSource implements EWrapper {
	//TWS Connection Socket
	EClientSocket e;
	//TWS Client ID
	int clientId;
	//TWS Ticker ID
	int tickerId;
	
	HashMap<Integer,ProductFeed> feeds;
	HashMap<Integer,HistoricalData> historicalReqs;

	public DataSource() {
		//Initiate client socket
		e = new EClientSocket(this);
		//Generate a random number for ClientID
		Random r = new Random();
		clientId = r.nextInt(10000);
		//Initialize the tickerId
		tickerId = 0;
		
		//Create maps for contract types
		feeds = new HashMap<Integer,ProductFeed>();
		historicalReqs = new HashMap<Integer,HistoricalData>();
		
		//Open the connection
		e.eConnect("",7496, clientId);
	}
	
	//Feed subscriptions
	//VERY IMPORTANT: remember to increment tickerId for each contract!!!
	//Returns a contract which will be updated with stock information
	public ProductFeed getStockFeed(String symbol) {
		Stock s = new Stock(symbol);
		subscribeFeed(s, "100,101,104,106,165");
		feeds.put(tickerId, s);
		return s;
	}
	//Returns a Forex Contract
	public ProductFeed getForexFeed(String cur1, String cur2) {
		Cash c = new Cash(cur1,cur2);
		subscribeFeed(c, "165");
		return c;
	}
	//Returns a Forex Contract
	public ProductFeed getOptionFeed(String underlyingSymbol, String type, double strike, String expiry) {
		Option o = new Option(underlyingSymbol, type, strike, expiry);
		subscribeFeed(o, "165");
		return o;
	}
	//Returns a Forex Contract
	public ProductFeed getFutureFeed(String cur1, String cur2) {
		Cash cc = new Cash(cur1,cur2);
		subscribeFeed(cc, "165");
		return cc;
	}
	//Returns a Forex Contract
	public ProductFeed getIndexFeed(String symbol) {
		Index i = new Index(symbol);
		subscribeFeed(i, "165");
		return i;
	}
	
	//Historical data loaders
	public HistoricalData loadHistory(ProductFeed feed, String name, String endDateTime, String durationStr, String barSizeSetting, String whatToShow, int useRTH, int formatDate, boolean async) {
		//Create a new historical data request
		e.reqHistoricalData(++tickerId, feed, endDateTime, durationStr, barSizeSetting, whatToShow, useRTH, formatDate);
		//Add the historical data to the product feed under the correct name
		HistoricalData h = feed.newHistoricalData(name, endDateTime, durationStr, barSizeSetting, whatToShow, useRTH, formatDate);
		//Make the historical data request accessable to the interface methods by ticker ID
		historicalReqs.put(tickerId, h);
		//Wait for synchronization
		if (!async) { while(!h.isSynchronized()) {} }
		return h;
	}

	public HistoricalData loadTradeHistory(ProductFeed feed, String endDateTime, String durationStr, String barSizeSetting, int useRTH, int formatDate) {
		return loadHistory(feed, "trades", endDateTime, durationStr, barSizeSetting, "TRADES", useRTH, formatDate, false);
	}
	
	//Creates new data subscriptions for contracts 
	private void subscribeFeed(ProductFeed c, String ticks, boolean async) {
		e.reqMktData(++tickerId, c, ticks, false);
		e.reqContractDetails(tickerId, c);
		feeds.put(tickerId, c);
		//Wait for sync
		if (!async) { while(!c.isSynchronized()) {} }
	}
	private void subscribeFeed(ProductFeed c, String ticks) {
		subscribeFeed(c, ticks, false);
	}
	
	//Allows interface methods to update stored contracts with the lasted info by tickerId
	private ProductFeed getFeed (int tickerId) {
		return (ProductFeed)feeds.get(tickerId);
	}
	//Allows interface methods to update historical data by the req id
	private HistoricalData getHistoricalReq (int reqId) {
		return (HistoricalData)historicalReqs.get(reqId);
	}
	
	//
	// Interface methods
	//
	//Updates Contract price data
	public void tickPrice(int tickerId, int field, double p, int canAutoExecute) {
		ProductFeed c = getFeed(tickerId);
		
		double price;
		//Special price modification for inverse forex contracts
		if (c instanceof Cash && ((Cash) c).swapped) {
			price = 1/p;
		} else {
			price = p;
		}
		
		switch (field) {
		case 1:
			c.bidPrice = price;
			break;
		case 2:
			c.askPrice = price;
			break;
		case 4:
			c.lastPrice = price;
			break;
		case 6:
			c.highPrice = price;
			break;
		case 7:
			c.lowPrice = price;
			break;
		case 9:
			c.closePrice = price;
			break;
		}
	}
	
	//Updates contract size data
	public void tickSize(int tickerId, int field, int size) {
		ProductFeed c = getFeed(tickerId);
		switch (field) {
		case 0:
			c.askSize = size;
			break;
		case 3:
			c.bidSize = size;
			break;
		case 5:
			c.lastSize = size;
			break;
		case 8:
			c.volume = size;
			break;
		case 21:
			c.avgVolume = size;
			break;
		}
	}
	
	public void error(Exception e) {
		System.out.println(e.getCause()); 
	}
	public void error(String str) {
		System.out.println(str); 
	}
	public void error(int id, int errorCode, String errorMsg) {
		//System.out.println(id);
		//System.out.println(errorCode);
		System.out.println(errorMsg);
		
		//For no security definition found error
		if (errorCode == 200) {
			ProductFeed c = getFeed(id);
			//If the contract in error is an unswapped cash contract, try swapping it
			if (c instanceof Cash) {
				if (!((Cash) c).swapped) {
					System.out.println("Attempting forex swap...");
					((Cash) c).swap();
					//Reset the market data
					e.reqMktData(id, c, "165", false);
				} else {
					System.out.println("Swap failed. Check product listings for valid contract.");
				}
			}
		}
	}
	public void connectionClosed() {}
	public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {}
	public void tickGeneric(int tickerId, int tickType, double value) {}
	public void tickString(int tickerId, int tickType, String value) {}
	public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays,String futureExpiry, double dividendImpact, double dividendsToExpiry) {}
	public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {}
	public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {}
	public void openOrderEnd() {}
	public void updateAccountValue(String key, String value, String currency, String accountName) {}
	public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {}
	public void updateAccountTime(String timeStamp) {}
	public void accountDownloadEnd(String accountName) {}
	public void nextValidId(int orderId) {}
	public void contractDetails(int reqId, ContractDetails contractDetails) {
		getFeed(reqId).details = contractDetails;
	}
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
		getFeed(reqId).details = contractDetails;
	}
	public void contractDetailsEnd(int reqId) {}
	public void execDetails(int reqId, Contract contract, Execution execution) {}
	public void execDetailsEnd(int reqId) {}
	public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {}
	public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {}
	public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {}
	public void managedAccounts(String accountsList) {}
	public void receiveFA(int faDataType, String xml) {}
	public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {
		HistoricalData r = getHistoricalReq(reqId);
		r.addBar(date, open, high, low, close, volume, count, WAP, hasGaps);
	}
	public void scannerParameters(String xml) {}
	public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {}
	public void scannerDataEnd(int reqId) {}
	public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {}
	public void currentTime(long time) {}
	public void fundamentalData(int reqId, String data) {}
	public void deltaNeutralValidation(int reqId, UnderComp underComp) {}	
	public void tickSnapshotEnd(int reqId) {}
	public void marketDataType(int reqId, int marketDataType) {}
	public void commissionReport(CommissionReport commissionReport) {}
	
}
