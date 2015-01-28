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
import org.apache.pivot.wtk.TableViewRowListener;
import org.apache.pivot.wtk.TextInput;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;

public class CategoriasPane extends TablePane implements Bindable {
	private List<CategoriaItem> categorias;
	
	@BXML private TableView categoriasTable;
	@BXML private TextInput descripcionText;
	@BXML private PushButton aniadirButton;
	@BXML private PushButton eliminarButton;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		categorias = new ArrayList<CategoriaItem>();
		categoriasTable.setTableData(categorias);
		categoriasTable.getTableViewRowListeners().add(new TableViewRowListener.Adapter() {
			@Override
			public void rowUpdated(TableView tableView, int index) {
				onCategoriasTableRowUpdated(tableView, index);
				super.rowUpdated(tableView, index);
			}
		});
		
		initCategoriasTable();
		
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

	protected void onCategoriasTableRowUpdated(TableView tableView, int index) {
		CategoriaItem c = (CategoriaItem)tableView.getSelectedRow();
		try {
			ServiceLocator.getCategoriasService().modificarCategoria(c);
		} catch (ServiceException e) {
			
		}
	}

	private void initCategoriasTable() {
		try {
			java.util.List<CategoriaItem> aux = ServiceLocator.getCategoriasService().listarCategorias();
			for (CategoriaItem c : aux) {
				categorias.add(c);
			}
		} catch (ServiceException e) {
			
		}
	}

	protected void onAniadirButtonPressed() {
		CategoriaItem nueva = new CategoriaItem();
		nueva.setDescripcion(descripcionText.getText());
		try {
			ServiceLocator.getCategoriasService().crearCategoria(nueva);
		} catch (ServiceException e) {
			
		}
		categorias.add(nueva);
		//TODO IMPORTANTE
		categorias.clear();
		initCategoriasTable();
		descripcionText.setText("");
	}

	protected void onEliminarButtonPressed() {
		java.util.List<CategoriaItem> eliminados = new java.util.ArrayList<CategoriaItem>();
		Sequence<?> seleccionados = categoriasTable.getSelectedRows();
		for (int i = 0; i < seleccionados.getLength(); i++) {
			eliminados.add((CategoriaItem) seleccionados.get(i));
			categorias.remove((CategoriaItem)seleccionados.get(i));
		}
		for (CategoriaItem e : eliminados) {
			try {
				CategoriaItem c = ServiceLocator.getCategoriasService().obtenerCategoria(e.getId());
				ServiceLocator.getCategoriasService().eliminarCategoria(c.getId());
			} catch (ServiceException e1) {
				
			}
		}
	}
}
