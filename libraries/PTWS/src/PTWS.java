import java.util.Vector;
import com.ib.client.*;
import java.math.*;
import java.util.Random;

public class PTWS implements EWrapper {
	Random r = new Random();
	int rand = r.nextInt(10000);
	//**StkContract**//
	public String m_symbol; // = "EUR";
	public String m_secType; //= "CASH"; 
    public String m_exchange; // = "IDEALPRO";
    public String m_currency; //= "USD";
    //**reqMktData**//
    public static int tickerId; // = 1;
    public String genericTickList = "100,101,104,225"; //34 volume
    Boolean snapshot = false;
    //**tickPrice**//
    float price; //specifies the price for the field in tickPrice
    //**tickSize**//
    int askSize;
    int bidSize;
    int lastSize;
    int volume;
    
//	public static void main(String[] args) {
//		PTWS fx = new PTWS();
//	}

	public PTWS(String m_symbol, String m_secType, String m_exchange, String m_currency) {
		EClientSocket e = new EClientSocket(this);
		e.eConnect("",7496, rand);
		System.out.println(e);
		StkContract sc = new StkContract(m_symbol, m_secType, m_exchange, m_currency);
		e.reqMktData(tickerId, sc, genericTickList, snapshot);
	}
	
	public void tickPrice(int tickerId, int field, double p, int canAutoExecute) {
		this.price = (float)p;
	}
	
	public void tickSize(int tickerId, int field, int size) {
		if(field == 0)
			this.askSize = size;
		else if(field == 3)
			this.bidSize = size;
		else if(field == 5)
			this.lastSize = size;
		else if(field == 8)
			this.volume = size;
	}

	public String getM_symbol() {
		return m_symbol;}
	public void setM_symbol(String m_symbol) {
		this.m_symbol = m_symbol;}
	
	public String getM_secType() {
		return m_secType;}
	public void setM_secType(String m_secType) {
		this.m_secType = m_secType;}
	
	public String getM_exchange() {
		return m_exchange;}
	public void setM_exchange(String m_exchange) {
		this.m_exchange = m_exchange;}
	
	public String getM_currency() {
		return m_currency;}
	public void setM_currency(String m_currency) {
		this.m_currency = m_currency;}
	
	public float getPrice() {
		return price;}
	public void setPrice(float price) {
		this.price = price;}
	
	public int getAskSize() {
		return askSize;}
	public void setAskSize(int askSize) {
		this.askSize = askSize;}
	
	public int getBidSize() {
		return bidSize;}
	public void setBidSize(int bidSize) {
		this.bidSize = bidSize;}
	
	public int getLastSize() {
		return lastSize;}
	public void setLastSize(int lastSize) {
		this.lastSize = lastSize;}
	
	public int getVolume() {
		return volume;}
	public void setVolume(int volume) {
		this.volume = volume;}
	
	public void error(Exception e) {
		System.out.println(e.getCause()); }
	public void error(String str) {
		System.out.println(str); }
	public void error(int id, int errorCode, String errorMsg) {
		System.out.println(errorMsg); }
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
	public void contractDetails(int reqId, ContractDetails contractDetails) {}
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {}
	public void contractDetailsEnd(int reqId) {}
	public void execDetails(int reqId, Contract contract, Execution execution) {}
	public void execDetailsEnd(int reqId) {}
	public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {}
	public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {}
	public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {}
	public void managedAccounts(String accountsList) {}
	public void receiveFA(int faDataType, String xml) {}
	public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {}
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
