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
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Spinner;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TabPane.TabSequence;
import org.apache.pivot.wtk.TabPaneSelectionListener;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.SeccionItem;

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
				onRecetasTabPreviewSelectedIndexChange(tabPane, selectedIndex);
				return super.previewSelectedIndexChange(tabPane, selectedIndex);
			}
		});
	}
	
	protected void onRecetasTabPreviewSelectedIndexChange(TabPane tabPane, int selectedIndex) {
		if(selectedIndex == recetasTab.getLength() - 2) {
			ComponenteReceta c = null;
			try {
				c = (ComponenteReceta) loadComponent("/dad/recetapp/ui/bxml/ComponenteReceta.bxml");
				c.setRecetApp(recetApp);
			} catch (IOException | SerializationException e) {
				Prompt mensaje = new Prompt(e.getMessage());
				mensaje.open(this.getWindow());
			} 
			recetasTab.getTabs().insert(c, recetasTab.getLength() - 2);
			recetasTab.setSelectedIndex(recetasTab.getLength() - 3);
		}
	}

	private void initCategoriaListButton() {
		java.util.List<CategoriaItem> aux;
		try {
			aux = ServiceLocator.getCategoriasService().listarCategorias();
			CategoriaItem categoria = new CategoriaItem();
			categoria.setDescripcion("<Seleccione una categor�a>");
			aux.add(0, categoria);
			categoriaListButton.setListData(convertirList(aux));
			categoriaListButton.setSelectedIndex(0);
		} catch (ServiceException e) {
			Prompt mensaje = new Prompt(e.getMessage());
			mensaje.open(this.getWindow());
		}
	}
	
	private void initRecetasTab() {
		ComponenteReceta c = null;
		try {
			c = (ComponenteReceta) loadComponent("/dad/recetapp/ui/bxml/ComponenteReceta.bxml");
			c.setRecetApp(recetApp);
		} catch (IOException | SerializationException e) {
			Prompt mensaje = new Prompt("Error en el campo orden.");
			mensaje.open(this.getWindow());
		} 
		recetasTab.getTabs().insert(c, 0);
		recetasTab.setSelectedIndex(0);
	}
	
	protected void onCrearButtonPressed() {
		try {
			Integer cantidad = Integer.parseInt(cantidadText.getText());
			
			if(categoriaListButton.getSelectedIndex() == 0) {
				Prompt mensaje = new Prompt("Debe seleccionar una categor�a");
				mensaje.open(this.getWindow());
			}
			else {
				RecetaItem receta = new RecetaItem();
				receta.setNombre(nombreText.getText());
				receta.setCantidad(cantidad);
				receta.setPara((String) paraListButton.getSelectedItem());
				receta.setTiempoTotal(tTotalMSpinner.getSelectedIndex() * 60 + tTotalSSpinner.getSelectedIndex());
				receta.setTiempoThermomix(tThermoMSpinner.getSelectedIndex() * 60 + tThermoSSpinner.getSelectedIndex());
				receta.setCategoria((CategoriaItem)categoriaListButton.getSelectedItem());
			
				TabSequence tabs = recetasTab.getTabs();
				//tabs.getLenght() - 1 para que no tenga en cuenta la pesta�a +
				for(int i = 0; i < tabs.getLength() - 1; i++) {
					ComponenteReceta comp = (ComponenteReceta)tabs.get(i);
					SeccionItem seccion = new SeccionItem();
					seccion.setNombre(comp.getSeccion());
					//A�adimos a la seccion los ingredientes de la tabla del componete
					for (IngredienteItem ingrediente : comp.getIngredientes()) {
						seccion.getIngredientes().add(ingrediente);
					}
					//A�adimos a la seccion las instrucciones de la tabla del componete
					for (InstruccionItem instruccion : comp.getInstrucciones()) {
						seccion.getInstrucciones().add(instruccion);
					}
				receta.getSecciones().add(seccion);
				}
				try {
					ServiceLocator.getRecetasService().crearReceta(receta);
				} catch (ServiceException e) {
					Prompt mensaje = new Prompt("Error al crear la receta.");
					mensaje.open(this.getWindow());
				}
				close();
				recetApp.getPrincipalWindow().getRecetasPane().actualizarTabla();
			}
		} catch(NumberFormatException e) {
			Prompt mensaje = new Prompt("Error en el campo para.");
			mensaje.open(this.getWindow());
		}
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
		int i = recetasTab.getSelectedIndex();
		//El segundo argumento de remove indica cuantas tabs se eliminar a partir del �ndice
		recetasTab.getTabs().remove(i, 1);
		if(i == 0) {
			recetasTab.setSelectedIndex(i);
		}
		else {
			recetasTab.setSelectedIndex(i - 1);
		}
	}
	
	public void setRecetApp(RecetApp recetApp) {
		this.recetApp = recetApp;
		initCategoriaListButton();
		initRecetasTab();
	}
}
