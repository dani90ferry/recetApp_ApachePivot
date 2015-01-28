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
	private RecetApp recetApp;
	
	@BXML private RecetasPane recetasPane;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		
	}

	public void setRecetApp(RecetApp recetApp) {
		this.recetApp = recetApp;
		recetasPane.setWindowsApp(recetApp);
	}
}
