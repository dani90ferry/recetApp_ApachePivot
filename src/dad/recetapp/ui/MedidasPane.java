package dad.recetapp.ui;

import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TextInput;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.MedidaItem;

public class MedidasPane extends TablePane implements Bindable {
	private List<MedidaItem> medidas;
	
	@BXML private TableView medidasTable;
	@BXML private TextInput nombreText;
	@BXML private TextInput abreviaturaText;
	@BXML private PushButton aniadirButton;
	@BXML private PushButton eliminarButton;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		medidas = new ArrayList<MedidaItem>();
		medidasTable.setTableData(medidas);
		
		initMedidasTable();
		
		aniadirButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAniadirButtonPressed();
			}
		});
		
		eliminarButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEliminarButtonPressed();
			}
		});
	}
	
	private void initMedidasTable() {
		try {
			java.util.List<MedidaItem> aux = ServiceLocator.getMedidasService().listarMedidas();
			for (MedidaItem c : aux) {
				medidas.add(c);
			}
		} catch (ServiceException e) {
			
		}
	}
	
	protected void onAniadirButtonPressed() {
		MedidaItem nueva = new MedidaItem();
		nueva.setNombre(nombreText.getText());
		nueva.setAbreviatura(abreviaturaText.getText());
		try {
			ServiceLocator.getMedidasService().crearMedida(nueva);
		} catch (ServiceException e) {
			
		}
		medidas.add(nueva);
		nombreText.setText("");
		abreviaturaText.setText("");
	}
	
	protected void onEliminarButtonPressed() {
		java.util.List<MedidaItem> eliminados = new java.util.ArrayList<MedidaItem>();
		Sequence<?> seleccionados = medidasTable.getSelectedRows();
		for (int i = 0; i < seleccionados.getLength(); i++) {
			eliminados.add((MedidaItem) seleccionados.get(i));
			medidas.remove((MedidaItem)seleccionados.get(i));
		}
		for (MedidaItem e : eliminados) {
			try {
				MedidaItem c = ServiceLocator.getMedidasService().obtenerMedida(e.getId());
				ServiceLocator.getMedidasService().eliminarMedida(c.getId());
			} catch (ServiceException e1) {
				
			}
		}
	}
}
