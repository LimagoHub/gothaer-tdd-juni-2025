package de.gothaer.gui.presenter;


import de.gothaer.gui.Euro2DollarRechnerView;
import de.gothaer.model.Euro2DollarRechner;

public class Euro2DollarPresenterImpl implements Euro2DollarPresenter {

	private System.Logger logger = System.getLogger(Euro2DollarPresenterImpl.class.getName());

	private Euro2DollarRechnerView view;
	private Euro2DollarRechner model;


	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#getView()
	 */
	@Override
	public Euro2DollarRechnerView getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#setView(de.gui.IEuro2DollarRechnerView)
	 */
	@Override
	public void setView(Euro2DollarRechnerView view) {
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#getModel()
	 */
	@Override
	public Euro2DollarRechner getModel() {
		return model;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#setModel(de.model.IEuro2DollarRechner)
	 */
	@Override
	public void setModel(Euro2DollarRechner model) {
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#rechnen()
	 *
	 * Eurowert als String aus der Maske holen
	 * Eurowert in double konvertieren (bei Problemen jeder Art -> Fehlermeldung in Dollarfeld)
	 * Dollarwert berechnen -> in wir das Model aufrufen (Fehlerbehandlung wie oben)
	 * Dollarwert als String in das Dollarfeld schreiben
	 *
	 *
	 */
	@Override
	public void onRechnen() {
        try {
			double dollar = model.calculateEuro2Dollar(Double.parseDouble(view.getEuro()));
			view.setDollar(String.format("%.2f", dollar));
        } catch (NumberFormatException | NullPointerException e) {
			view.setDollar("Kann nicht konvertiert werden.");
        } catch (RuntimeException e) {
			view.setDollar("Wechselkurs nicht verfuegbar.");
		}
    }

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#beenden()
	 */
	@Override
	public void onBeenden() {
		view.close();
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#populateItems()
	 */
	@Override
	public void onPopulateItems() {
		view.setEuro("0");
		view.setDollar("0");
		view.setRechnenEnabled(true);

	}

	@Override
	public void updateRechnenActionState() {
		try {
			Double.parseDouble(view.getEuro());
			view.setRechnenEnabled(true);
		} catch (NumberFormatException | NullPointerException e) {
			view.setRechnenEnabled(false);
		}

    }
}