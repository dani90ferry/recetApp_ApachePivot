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
		medidasTable.getTableViewRowListeners().add(new TableViewRowListener.Adapter() {
			@Override
			public void rowUpdated(TableView tableView, int index) {
				onMedidasTableRowUpdated(tableView, index);
				super.rowUpdated(tableView, index);
			}
		});
		
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
	
	protected void onMedidasTableRowUpdated(TableView tableView, int index) {
		MedidaItem c = (MedidaItem)tableView.getSelectedRow();
		try {
			ServiceLocator.getMedidasService().modificarMedida(c);
		} catch (ServiceException e) {
			
		}
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
		if(!nombreText.getText().equals("") && !abreviaturaText.getText().equals("")) {
			MedidaItem nueva = new MedidaItem();
			nueva.setNombre(nombreText.getText());
			nueva.setAbreviatura(abreviaturaText.getText());
			try {
				ServiceLocator.getMedidasService().crearMedida(nueva);
			} catch (ServiceException e) {
			
			}
			medidas.add(nueva);
			//TODO IMPORTANTE
			medidas.clear();
			initMedidasTable();
			nombreText.setText("");
			abreviaturaText.setText("");
		} else {
			Prompt mensaje = new Prompt("Quedan campos vacíos");
			mensaje.open(this.getWindow());
		}
	}
	
	protected void onEliminarButtonPressed() {
		StringBuffer mensaje = new StringBuffer();
		mensaje.append("¿Desea eliminar las siguientes medidas?\n\n");
		
		java.util.List<MedidaItem> eliminados = new java.util.ArrayList<MedidaItem>();
		Sequence<?> seleccionados = medidasTable.getSelectedRows();
		
		if(seleccionados.getLength() != 0) {
			for (int i = 0; i < seleccionados.getLength(); i++) {
				mensaje.append("- " + ((MedidaItem)seleccionados.get(i)).getNombre() + "\n");
			}
			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("Sí", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
					if (confirmar.getResult() && confirmar.getSelectedOption().equals("Sí")) {
						for (int i = 0; i < seleccionados.getLength(); i++) {
							eliminados.add((MedidaItem) seleccionados.get(i));
						}
						for (MedidaItem e : eliminados) {
							try {
								MedidaItem c = ServiceLocator.getMedidasService().obtenerMedida(e.getId());
								ServiceLocator.getMedidasService().eliminarMedida(c.getId());
								for (int i = 0; i < seleccionados.getLength(); i++) {
									medidas.remove((MedidaItem)seleccionados.get(i));
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
