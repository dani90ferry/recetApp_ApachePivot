package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Window;

public class NuevaRecetaWindow extends Window implements Bindable{
	
	private RecetApp recetApp;

	@BXML private PushButton cancelarButton;
	@BXML private ComponenteReceta componenteReceta;

	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		cancelarButton.getButtonPressListeners().add(new ButtonPressListener() {	
			public void buttonPressed(Button button) {
				 onCancelarButtonPressed();		
			}
		});

	}

	protected void onCancelarButtonPressed() {
		close();
	}
	
	public void setRecetApp(RecetApp recetApp) {
		this.recetApp = recetApp;
		componenteReceta.setWindowsApp(recetApp);
	}
}
