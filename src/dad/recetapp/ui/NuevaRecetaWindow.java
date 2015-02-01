package dad.recetapp.ui;

import java.io.IOException;
import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.Vote;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Spinner;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TabPaneSelectionListener;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
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
	@BXML private TabPane recetasTab;

	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		initCategoriaListButton();
		initRecetasTab();
		
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
		
		recetasTab.getTabPaneSelectionListeners().add(new TabPaneSelectionListener.Adapter() {
			@Override
			public Vote previewSelectedIndexChange(TabPane tabPane, int selectedIndex) {
				if(selectedIndex == recetasTab.getLength() - 2) {
					ComponenteReceta c = null;
					try {
						c = (ComponenteReceta) loadComponent("/dad/recetapp/ui/ComponenteReceta.bxml");
					} catch (IOException | SerializationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					recetasTab.getTabs().insert(c, recetasTab.getLength() - 2);
					recetasTab.setSelectedIndex(recetasTab.getLength() - 3);
				}
				return super.previewSelectedIndexChange(tabPane, selectedIndex);
			}
		});
	}
	
	private void initCategoriaListButton() {
		java.util.List<CategoriaItem> aux;
		try {
			aux = ServiceLocator.getCategoriasService().listarCategorias();
			CategoriaItem categoria = new CategoriaItem();
			categoria.setDescripcion("<Seleccione una categoría>");
			aux.add(0, categoria);
			categoriaListButton.setListData(convertirList(aux));
			categoriaListButton.setSelectedIndex(0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initRecetasTab() {
		ComponenteReceta c = null;
		try {
			c = (ComponenteReceta) loadComponent("/dad/recetapp/ui/ComponenteReceta.bxml");
		} catch (IOException | SerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		recetasTab.getTabs().insert(c, 0);
		recetasTab.setSelectedIndex(0);
	}
	
	protected void onCrearButtonPressed() {
		RecetaItem receta = new RecetaItem();
		receta.setNombre(nombreText.getText());
		receta.setCantidad(Integer.valueOf(cantidadText.getText()));
		//TODO Arreglar
		receta.setPara((String) paraListButton.getSelectedItem());
		receta.setTiempoTotal(tTotalMSpinner.getSelectedIndex() * 60 + tTotalSSpinner.getSelectedIndex());
		receta.setTiempoThermomix(tThermoMSpinner.getSelectedIndex() * 60 + tThermoSSpinner.getSelectedIndex());
		receta.setCategoria((CategoriaItem)categoriaListButton.getSelectedItem());
		
		try {
			ServiceLocator.getRecetasService().crearReceta(receta);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		close();
		//TODO cuando añades una receta no se carga en la tabla recetas
	}

	protected void onCancelarButtonPressed() {
		close();
	}
	
	private List<CategoriaItem> convertirList(java.util.List<CategoriaItem> listUtil) {
		List<CategoriaItem> aux = new ArrayList<CategoriaItem>();
		for(CategoriaItem c : listUtil) {
			aux.add(c);
		}
		return aux;
	}
	
	private Component loadComponent(String bxmlFile) throws IOException, SerializationException {
		URL bxmlUrl = RecetApp.class.getResource(bxmlFile);
		BXMLSerializer serializer = new BXMLSerializer();
		return (Component) serializer.readObject(bxmlUrl);
	}
	
	public void removeSelectedTab() {
		//El segundo argumento de remove indica cuantas tabs se eliminar a partir del índice
//		recetasTab.getTabs().remove(recetasTab.getSelectedIndex(), 1);
//		recetasTab.remove(recetasTab.getSelectedIndex(), 1);
		System.out.println("Hola");
	}
	
	public void setRecetApp(RecetApp recetApp) {
		this.recetApp = recetApp;
//		componenteReceta.setWindowsApp(recetApp);
	}
}
