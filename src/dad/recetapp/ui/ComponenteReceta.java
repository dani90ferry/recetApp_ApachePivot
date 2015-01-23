package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.PushButton;

public class ComponenteReceta extends FillPane implements Bindable {
	private RecetApp recetApp;
	
	@BXML private PushButton editarButtonTable2;
	@BXML private PushButton anadirButtonTable2;
	
	@Override
	public void initialize(Map<String, Object> arg0, URL arg1, Resources arg2) {
		anadirButtonTable2.getButtonPressListeners().add(new ButtonPressListener() {	
			public void buttonPressed(Button button) {
				 onAnadirTable2ButtonPressed();		
			}
		});
		
	
		editarButtonTable2.getButtonPressListeners().add(new ButtonPressListener() {	
			public void buttonPressed(Button button) {
				 onEditarTable2ButtonPressed();		
			}
		});

	}

	protected void onAnadirTable2ButtonPressed() {
		recetApp.openNuevaIntruccionWindow();
	}

	protected void onEditarTable2ButtonPressed() {
		recetApp.openEditarIntruccionWindow();
	}
	
	public void setWindowsApp(RecetApp windowsApp) {
		this.recetApp = windowsApp;
	}
}
