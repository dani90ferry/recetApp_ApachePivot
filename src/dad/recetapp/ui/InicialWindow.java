package dad.recetapp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Timer;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentMouseButtonListener;
import org.apache.pivot.wtk.ImageView;
import org.apache.pivot.wtk.Mouse.Button;
import org.apache.pivot.wtk.Window;

import dad.recetapp.ui.RecetApp;;

public class InicialWindow extends Window implements Bindable {
	private RecetApp recetApp;
	private Timer timer;
	
	@BXML private ImageView imagen;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		imagen.getComponentMouseButtonListeners().add(new ComponentMouseButtonListener() {

			@Override
			public boolean mouseClick(Component component, Button button, int x, int y, int count) {
				onImagenMouseClick();
				return false;
			}

			@Override
			public boolean mouseDown(Component component, Button button, int x,	int y) {
				return false;
			}

			@Override
			public boolean mouseUp(Component component, Button button, int x, int y) {
				return false;
			}
			
		});
		
		timer = new Timer(4000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onTimerActionPerformed(e);
			}
		});
		timer.start();
	}

	protected void onImagenMouseClick() {
		timer.stop();
		recetApp.openPrincipalWindow();
	}
	
	protected void onTimerActionPerformed(ActionEvent e) {
		timer.stop();
		recetApp.openPrincipalWindow();
	}

	public void setRecetApp(RecetApp recetApp) {
		this.recetApp = recetApp;
	}
}
