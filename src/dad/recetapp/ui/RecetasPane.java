package dad.recetapp.ui;

import java.net.URL;


import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Spinner;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TextInput;


import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaListItem;

public class RecetasPane extends TablePane implements Bindable {
	private RecetApp recetApp;
	private List<RecetaListItem> recetas;
	
	@BXML private TableView recetasTable;
	@BXML private TextInput nombreText;
	@BXML private Spinner minutosSpinner;
	@BXML private Spinner segundosSpinner;
	@BXML private ListButton categoriasListButton;
	@BXML private PushButton aniadirButton;
	@BXML private PushButton editarButton;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		recetas = new ArrayList<RecetaListItem>();
		recetasTable.setTableData(recetas);
		
		initRecetasTable();
		initCategoriaListButton();
		
		nombreText.getComponentKeyListeners().add(new ComponentKeyListener.Adapter() {
		@Override
			public boolean keyTyped(Component component, char character) {
				aplicarFiltro();
				return false;
			}
		});
		
		aniadirButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAniadirButtonPressed();
			}
		});
		
		editarButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEditarButtonPressed();
			}
		});
	}
	
	protected void aplicarFiltro() {
		java.util.List<RecetaListItem> aux;
		recetas.clear();
		try {
			if(!nombreText.getText().isEmpty()) {
				aux = ServiceLocator.getRecetasService().buscarRecetas(nombreText.getText(), null, null);
			}
			else {
				aux = ServiceLocator.getRecetasService().listarRecetas();
			}
			for (RecetaListItem c : aux) {
				recetas.add(c);
			}
		} catch (ServiceException e) {
			
		}
	}

	private void initRecetasTable() {
		try {
			java.util.List<RecetaListItem> aux = ServiceLocator.getRecetasService().listarRecetas();
			for (RecetaListItem c : aux) {
				recetas.add(c);
			}
		} catch (ServiceException e) {
			
		}
	}
	
	private void initCategoriaListButton() {
		java.util.List<CategoriaItem> aux;
		try {
			aux = ServiceLocator.getCategoriasService().listarCategorias();
			CategoriaItem categoria = new CategoriaItem();
			categoria.setDescripcion("<Todas>");
			aux.add(0, categoria);
			categoriasListButton.setListData(convertirList(aux));
			categoriasListButton.setSelectedIndex(0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List<CategoriaItem> convertirList(java.util.List<CategoriaItem> listUtil) {
		List<CategoriaItem> aux = new ArrayList<CategoriaItem>();
		for(CategoriaItem c : listUtil) {
			aux.add(c);
		}
		return aux;
	}

	protected void onEditarButtonPressed() {
		recetApp.openEditarRecetaWindow();
		
	}
	
	protected void onAniadirButtonPressed() {
		recetApp.openNuevaRecetaWindow();
	}
	
	public void setWindowsApp(RecetApp windowsApp) {
		this.recetApp = windowsApp;
	}
}
