package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.Window;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;

public class PrincipalWindow extends Window implements Bindable {
//	private RecetApp recetApp;
	
	@BXML private RecetasPane recetasPane;
	@BXML private Label numRecetas;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		try {
			numRecetas.setText("" + ServiceLocator.getRecetasService().listarRecetas().size());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setRecetApp(RecetApp recetApp) {
//		this.recetApp = recetApp;
		recetasPane.setWindowsApp(recetApp);
	}
	
	public void setNumRecetasText(String text) {
		numRecetas.setText(text);
	}

	public RecetasPane getRecetasPane() {
		return recetasPane;
	}
}
