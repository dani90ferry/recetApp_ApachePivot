package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;

public class NuevaInstruccionWindow extends Dialog implements Bindable {
	
	private Boolean cancelado = true;

	@BXML private PushButton cancelarButton;
	@BXML private PushButton anadirButton;
	@BXML private TextInput ordenText;
	@BXML private TextArea descripcionText;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		cancelarButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onCancelarButtonButtonPressed();
			}
		});
		
		anadirButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAnadirButtonButtonPressed();
			}
		});
	}

	
	protected void onAnadirButtonButtonPressed() {
		cancelado = false;
		close();
	}

	protected void onCancelarButtonButtonPressed() {
		close();
	}

	public Boolean getCancelado() {
		return cancelado;
	}
	
	public Integer getOrden() {
		return Integer.valueOf(ordenText.getText());
	}

	public String getDescripcion() {
		return descripcionText.getText();
	}
	

}
