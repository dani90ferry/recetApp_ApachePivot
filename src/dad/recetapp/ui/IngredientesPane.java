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
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableViewRowListener;
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
		ingredientesTable.getTableViewRowListeners().add(new TableViewRowListener.Adapter() {
			@Override
			public void rowUpdated(TableView tableView, int index) {
				onIngredientesTableRowUpdated(tableView, index);
				super.rowUpdated(tableView, index);
			}
		});
		
		initIngredientesTable();
		
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
	
	protected void onIngredientesTableRowUpdated(TableView tableView, int index) {
		TipoIngredienteItem c = (TipoIngredienteItem)tableView.getSelectedRow();
		try {
			ServiceLocator.getTiposIngredienteService().modificarTipoIngrediente(c);
		} catch (ServiceException e) {
			Prompt mensaje = new Prompt(e.getMessage());
			mensaje.open(getWindow());
		}
	}

	private void initIngredientesTable() {
		try {
			java.util.List<TipoIngredienteItem> aux = ServiceLocator.getTiposIngredienteService().listarTiposIngredientes();
			for (TipoIngredienteItem c : aux) {
				tipoIngredientes.add(c);
			}
		} catch (ServiceException e) {
			Prompt mensaje = new Prompt(e.getMessage());
			mensaje.open(getWindow());
		}
	}
	
	protected void onAniadirButtonPressed() {
		if(!nombreText.getText().equals("")) {
			TipoIngredienteItem nueva = new TipoIngredienteItem();
			nueva.setNombre(nombreText.getText());
			try {
				ServiceLocator.getTiposIngredienteService().crearTipoIngrediente(nueva);
				tipoIngredientes.add(nueva);
			} catch (ServiceException e) {
				Prompt mensaje = new Prompt(e.getMessage());
				mensaje.open(getWindow());
			}
			//Recargar la tabla
			tipoIngredientes.clear();
			initIngredientesTable();
			nombreText.setText("");
		}
		else {
			Prompt mensaje = new Prompt("Campo nombre vac�o");
			mensaje.open(this.getWindow());
		}
	}
	
	protected void onEliminarButtonPressed() {
		StringBuffer mensaje = new StringBuffer();
		mensaje.append("�Desea eliminar los siguientes ingredientes?\n\n");
		
		java.util.List<TipoIngredienteItem> eliminados = new java.util.ArrayList<TipoIngredienteItem>();
		Sequence<?> seleccionados = ingredientesTable.getSelectedRows();
		
		if(seleccionados.getLength() != 0) {
			for (int i = 0; i < seleccionados.getLength(); i++) {
				mensaje.append("- " + ((TipoIngredienteItem)seleccionados.get(i)).getNombre() + "\n");
			}
			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("S�", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
					if (confirmar.getResult() && confirmar.getSelectedOption().equals("S�")) {
						for (int i = 0; i < seleccionados.getLength(); i++) {
							eliminados.add((TipoIngredienteItem) seleccionados.get(i));
						}
						for (TipoIngredienteItem e : eliminados) {
							try {
								TipoIngredienteItem c = ServiceLocator.getTiposIngredienteService().obtenerTipoIngrediente(e.getId());
								ServiceLocator.getTiposIngredienteService().eliminarTipoIngrediente(c.getId());
								for (int i = 0; i < seleccionados.getLength(); i++) {
									tipoIngredientes.remove((TipoIngredienteItem)seleccionados.get(i));
								}
							} catch (ServiceException e1) {
								Prompt mensaje = new Prompt(e1.getMessage());
								mensaje.open(getWindow());
							}
						}
					}
				}
			});
		}
	}
}
