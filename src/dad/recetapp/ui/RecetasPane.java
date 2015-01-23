package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TablePane;

public class RecetasPane extends TablePane implements Bindable {
	private RecetApp recetApp;
	
	@BXML private PushButton aniadirButton;
	@BXML private PushButton editarButton;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		aniadirButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAniadirButtonPressed();
			}
		});
		
		editarButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEditarButtonPressed();
			}
		});
		
	}
	
	protected void onEditarButtonPressed() {
		recetApp.openEditarRecetaWindow();
	}

	protected void onAniadirButtonPressed() {
		recetApp.openNuevaRecetaWindow();
	}
	
	public void setWindowsApp(RecetApp windowsApp) {
		this.recetApp = windowsApp;
	}
}
