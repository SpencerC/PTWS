import com.ib.client.Contract;

public class StkContract extends Contract {
	
	public StkContract(String m_symbol, String m_secType, String m_exchange, String m_currency) {
		this.m_symbol = m_symbol;
		this.m_secType = m_secType;
		this.m_exchange = m_exchange;
		this.m_currency = m_currency;
	}
	
	
}
