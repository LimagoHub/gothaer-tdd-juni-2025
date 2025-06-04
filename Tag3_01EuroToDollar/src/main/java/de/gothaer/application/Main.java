package de.gothaer.application;


import de.gothaer.gui.Euro2DollarRechnerViewImpl;
import de.gothaer.gui.Euro2DollarRechnerView;
import de.gothaer.gui.presenter.Euro2DollarPresenterImpl;
import de.gothaer.gui.presenter.Euro2DollarPresenter;
import de.gothaer.model.Euro2DollarRechnerImpl;
import de.gothaer.model.Euro2DollarRechner;
import de.gothaer.model.ExchanceService;
import de.gothaer.model.ExchanceServiceImpl;

public class Main {


	public static void main(String[] args) {
		ExchanceService exchangeservice = new ExchanceServiceImpl();
		Euro2DollarRechner model = new Euro2DollarRechnerImpl(exchangeservice);
		
		Euro2DollarPresenter presenter = new Euro2DollarPresenterImpl();
		
		Euro2DollarRechnerView view = new Euro2DollarRechnerViewImpl();
		
		
		presenter.setView(view);
		presenter.setModel(model);
		
		view.setPresenter(presenter);
		
		presenter.onPopulateItems();
		
		view.show();

	}

}
