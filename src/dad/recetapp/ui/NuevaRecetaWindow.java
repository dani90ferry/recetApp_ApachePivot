package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Spinner;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaItem;

public class NuevaRecetaWindow extends Window implements Bindable{
	
	private RecetApp recetApp;

	@BXML private TextInput nombreText;
	@BXML private TextInput cantidadText;
	@BXML private ListButton paraListButton;
	@BXML private ListButton categoriaListButton;
	@BXML private Spinner tTotalMSpinner;
	@BXML private Spinner tTotalSSpinner;
	@BXML private Spinner tThermoMSpinner;
	@BXML private Spinner tThermoSSpinner;
	@BXML private PushButton cancelarButton;
	@BXML private PushButton crearButton;
	@BXML private ComponenteReceta componenteReceta;

	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		cancelarButton.getButtonPressListeners().add(new ButtonPressListener() {	
			public void buttonPressed(Button button) {
				 onCancelarButtonPressed();		
			}
		});
		
		crearButton.getButtonPressListeners().add(new ButtonPressListener() {	
			public void buttonPressed(Button button) {
				 onCrearButtonPressed();		
			}
		});

	}

	protected void onCrearButtonPressed() {
		RecetaItem receta = new RecetaItem();
		CategoriaItem categoria = null;
		try {
			categoria = ServiceLocator.getCategoriasService().obtenerCategoria((long) 1);
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		receta.setNombre(nombreText.getText());
		receta.setCantidad(Integer.valueOf(cantidadText.getText()));
		receta.setPara("persona");
		receta.setTiempoTotal(tTotalMSpinner.getSelectedIndex()*60 + tTotalSSpinner.getSelectedIndex());
		receta.setTiempoThermomix(tThermoMSpinner.getSelectedIndex()*60 + tThermoSSpinner.getSelectedIndex());
		receta.setCategoria(categoria);
		
		try {
			ServiceLocator.getRecetasService().crearReceta(receta);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	

		
	}

	protected void onCancelarButtonPressed() {
		close();
	}
	
	public void setRecetApp(RecetApp recetApp) {
		this.recetApp = recetApp;
		componenteReceta.setWindowsApp(recetApp);
	}
}
