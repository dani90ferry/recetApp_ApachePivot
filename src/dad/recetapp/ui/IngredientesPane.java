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
import dad.recetapp.services.items.TipoIngredienteItem;

public class IngredientesPane extends TablePane implements Bindable {
	private List<TipoIngredienteItem> tipoIngredientes;
	
	
	@BXML private TableView ingredientesTable;
	@BXML private TextInput nombreText;
	@BXML private PushButton aniadirButton;
	@BXML private PushButton eliminarButton;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		tipoIngredientes = new ArrayList<TipoIngredienteItem>();
		ingredientesTable.setTableData(tipoIngredientes);
		
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
			java.util.List<TipoIngredienteItem> aux = ServiceLocator.getTiposIngredienteService().listarTiposIngredientes();
			for (TipoIngredienteItem c : aux) {
				tipoIngredientes.add(c);
			}
		} catch (ServiceException e) {
			
		}
	}
	
	protected void onAniadirButtonPressed() {
		TipoIngredienteItem nueva = new TipoIngredienteItem();
		nueva.setNombre(nombreText.getText());
		try {
			ServiceLocator.getTiposIngredienteService().crearTipoIngrediente(nueva);
		} catch (ServiceException e) {
			
		}
		tipoIngredientes.add(nueva);
		nombreText.setText("");
	}
	
	protected void onEliminarButtonPressed() {
		java.util.List<TipoIngredienteItem> eliminados = new java.util.ArrayList<TipoIngredienteItem>();
		Sequence<?> seleccionados = ingredientesTable.getSelectedRows();
		for (int i = 0; i < seleccionados.getLength(); i++) {
			eliminados.add((TipoIngredienteItem) seleccionados.get(i));
			tipoIngredientes.remove((TipoIngredienteItem)seleccionados.get(i));
		}
		for (TipoIngredienteItem e : eliminados) {
			try {
				TipoIngredienteItem c = ServiceLocator.getTiposIngredienteService().obtenerTipoIngrediente(e.getId());
				ServiceLocator.getTiposIngredienteService().eliminarTipoIngrediente(c.getId());
			} catch (ServiceException e1) {
				
			}
		}
	}
	
}
