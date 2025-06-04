package de.gothaer.model;

public class ExchanceServiceImpl implements ExchanceService{
    @Override
    public double getRate(final String currency) {
        return 1.1;
    }
}
