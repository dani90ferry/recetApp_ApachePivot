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
import dad.recetapp.services.items.TipoAnotacionItem;

public class AnotacionesPane extends TablePane implements Bindable {
	private List<TipoAnotacionItem> anotaciones;

	@BXML private TableView anotacionesTable;
	@BXML private TextInput descripcionText;
	@BXML private PushButton aniadirButton;
	@BXML private PushButton eliminarButton;

	@Override
	public void initialize(Map<String, Object> namespace, URL location,
			Resources resources) {
		anotaciones = new ArrayList<TipoAnotacionItem>();
		anotacionesTable.setTableData(anotaciones);
		anotacionesTable.getTableViewRowListeners().add(new TableViewRowListener.Adapter() {
			@Override
			public void rowUpdated(TableView tableView, int index) {
				onAnotacionesTableRowUpdated(tableView, index);
				super.rowUpdated(tableView, index);
			}
		});
		initAnotacionesTable();

		eliminarButton.getButtonPressListeners().add(new ButtonPressListener() {
			public void buttonPressed(Button button) {
				onEliminarButtonPressed();
			}
		});

		aniadirButton.getButtonPressListeners().add(new ButtonPressListener() {
			public void buttonPressed(Button button) {
				try {
					onAnadirButtonPressed();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	protected void onAnotacionesTableRowUpdated(TableView tableView, int index) {
		TipoAnotacionItem c = (TipoAnotacionItem)tableView.getSelectedRow();
		try {
			ServiceLocator.getTiposAnotacionesService().modificarTipoAnotacion(c);
		} catch (ServiceException e) {
			
		}
	}

	private void initAnotacionesTable() {
		try {
			java.util.List<TipoAnotacionItem> anotacionesutil = ServiceLocator
					.getTiposAnotacionesService().listarTipoAnotacion();
			for (TipoAnotacionItem anot : anotacionesutil) {
				anotaciones.add(anot);
			}
		} catch (ServiceException e) {
			
		}
	}

	protected void onEliminarButtonPressed() {
		StringBuffer mensaje = new StringBuffer();
		mensaje.append("¿Desea eliminar las siguientes anotaciones?\n\n");
		
		java.util.List<TipoAnotacionItem> eliminados = new java.util.ArrayList<TipoAnotacionItem>();
		Sequence<?> seleccionados = anotacionesTable.getSelectedRows();
		
		if(seleccionados.getLength() != 0) {
			for (int i = 0; i < seleccionados.getLength(); i++) {
				mensaje.append("- " + ((TipoAnotacionItem)seleccionados.get(i)).getDescripcion() + "\n");
			}
			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("Sí", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
					if (confirmar.getResult() && confirmar.getSelectedOption().equals("Sí")) {
						for (int i = 0; i < seleccionados.getLength(); i++) {
							eliminados.add((TipoAnotacionItem) seleccionados.get(i));
							anotaciones.remove((TipoAnotacionItem)seleccionados.get(i));
						}
						for (TipoAnotacionItem e : eliminados) {
							try {
								TipoAnotacionItem c = ServiceLocator.getTiposAnotacionesService().obtenerTipoAnotacion(e.getId());
								ServiceLocator.getTiposAnotacionesService().eliminarTipoAnotacion(c.getId());
							} catch (ServiceException e1) {
							
							}
						}
					}
				}
			});
		}
	}

	protected void onAnadirButtonPressed() throws ServiceException {
		if(!descripcionText.getText().equals("")) {
			TipoAnotacionItem nueva = new TipoAnotacionItem();
			nueva.setDescripcion(descripcionText.getText());
			try {
				ServiceLocator.getTiposAnotacionesService().crearTipoAnotacion(nueva);
			} catch (ServiceException e) {
			
			}
			anotaciones.add(nueva);
			//TODO IMPORTANTE
			anotaciones.clear();
			initAnotacionesTable();
			descripcionText.setText("");
		}
		else {
			Prompt mensaje = new Prompt("Descripción no puede ser vacía");
			mensaje.open(this.getWindow());
		}
	}
}
