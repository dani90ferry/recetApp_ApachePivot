package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtk.TextInput;

public class NuevaInstruccionDialog extends Dialog implements Bindable {
	
	private Boolean cancelado = true;

	@BXML private PushButton cancelarButton;
	@BXML private PushButton anadirButton;
	@BXML private TextInput ordenText;
	@BXML private TextArea descripcionText;

	private Integer orden;
	
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
		try {
		orden = Integer.parseInt(ordenText.getText());
		cancelado = false;
		close();
		}catch (NumberFormatException e){
			Prompt mensaje = new Prompt("Error en el campo orden.");
			mensaje.open(this.getWindow());
		}
	}

	protected void onCancelarButtonButtonPressed() {
		close();
	}

	public Boolean getCancelado() {
		return cancelado;
	}
	
	public Integer getOrden() {
		return orden;
	}

	public String getDescripcion() {
		return descripcionText.getText();
	}
	

}
