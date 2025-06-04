package de.gothaer.model;

public class Euro2DollarRechnerImpl implements Euro2DollarRechner {
	
	private final ExchanceService exchangeservice;

	public Euro2DollarRechnerImpl(final ExchanceService exchangeservice) {
		this.exchangeservice = exchangeservice;
	}

	/* (non-Javadoc)
	 * @see de.model.IEuro2DollarRechner#calculateEuro2Dollar(de.model.Euro2DollarForm)
	 */
	@Override
	public double calculateEuro2Dollar(double euro) {

        try {
            double wechselkurs = exchangeservice.getRate("USD");
            return euro * wechselkurs;
        } catch (RuntimeException e) {
            throw new ExchangeServiceException("Wechselkurs konnte nicht ermittelt werden.", e);
        }
    }

}
