package de.gothaer.gui;



import de.gothaer.gui.presenter.Euro2DollarPresenter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Euro2DollarRechnerViewImpl extends Frame implements Euro2DollarRechnerView {
	
	
	private static final long serialVersionUID = 8836985830947515511L;
	private Panel centerPanel = null;
	private Panel southPanel = null;
	private TextField txtEuro = null;
	private TextField txtDollar = null;
	private Button btnRechnen = null;
	private Button btnEnde = null;
	
	private transient Euro2DollarPresenter presenter;
	
	public Euro2DollarRechnerViewImpl() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				presenter.onBeenden();
			}
		});
		add(getCenterPanel(),BorderLayout.CENTER);
		add(getSouthPanel(),BorderLayout.SOUTH);
		pack();
	}
	
	
	
	public Euro2DollarPresenter getPresenter() {
		return presenter;
	}



	public void setPresenter(Euro2DollarPresenter presenter) {
		this.presenter = presenter;
	}



	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#close()
	 */
	@Override
	public void close() {
		this.dispose();
	}
	
	@Override
	public boolean isRechnenEnabled() {
		
		return getBtnRechnen().isEnabled();
	}



	@Override
	public void setRechnenEnabled(boolean enabled) {
		getBtnRechnen().setEnabled(enabled);
		
	}
	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#getEuro()
	 */
	@Override
	public String getEuro() {
		return getTxtEuro().getText();
	}




	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#setEuro(java.lang.String)
	 */
	@Override
	public void setEuro(String euro) {
		getTxtEuro().setText(euro);
	}




	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#getDollar()
	 */
	@Override
	public String getDollar() {
		return getTxtDollar().getText();
	}




	/* (non-Javadoc)
	 * @see de.gui.IEuro2DolarRechnerView#setDollar(java.lang.String)
	 */
	@Override
	public void setDollar(String dollar) {
		getTxtDollar().setText(dollar);
	}


	@Override
	public void setEnabled(boolean value){
		getBtnRechnen().setEnabled(value);
	}

	private Panel getCenterPanel() {
		if(centerPanel == null) {
			centerPanel = new Panel(new GridLayout(2,2));
			centerPanel.add(new Label("Euro:"));
			centerPanel.add(getTxtEuro());
			centerPanel.add(new Label("Dollar:"));
			centerPanel.add(getTxtDollar());
		}
		return centerPanel;
	}
	private Panel getSouthPanel() {
		if(southPanel == null) {
			southPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
			southPanel.add(getBtnRechnen());
			southPanel.add(getBtnEnde());
			
		}
		return southPanel;
	}
	private TextField getTxtEuro() {
		if(txtEuro == null) {
			txtEuro = new TextField(20);
			txtEuro.addTextListener(e->presenter.updateRechnenActionState());
		}
		return txtEuro;
	}
	private TextField getTxtDollar() {
		if(txtDollar == null) {
			txtDollar = new TextField(20);
			txtDollar.setEditable(false);
		}
		return txtDollar;
	}
	private Button getBtnRechnen() {
		if(btnRechnen == null) {
			btnRechnen = new Button("Rechnen");
			btnRechnen.setEnabled(false);
			btnRechnen.addActionListener(e->presenter.onRechnen());
		}
		return btnRechnen;
	}
	private Button getBtnEnde() {
		if(btnEnde == null) {
			btnEnde = new Button("Ende");
			btnEnde.addActionListener(e->presenter.onBeenden());
		}
		return btnEnde;
	}




	
	
}
