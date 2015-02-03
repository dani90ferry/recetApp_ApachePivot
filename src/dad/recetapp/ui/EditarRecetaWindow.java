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
import org.apache.pivot.wtk.content.ButtonData;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListFormatItem;
import dad.recetapp.services.items.SeccionItem;

public class EditarRecetaWindow extends Window implements Bindable {
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
	@BXML private PushButton guardarButton;
	@BXML private TabPane recetasTab;
	
	@BXML private ComponenteReceta componenteReceta;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		initCategoriaListButton();
		
		cancelarButton.getButtonPressListeners().add(new ButtonPressListener() {	
			public void buttonPressed(Button button) {
				 onCancelarButtonPressed();		
			}
		});
		
		guardarButton.getButtonPressListeners().add(new ButtonPressListener() {	
			public void buttonPressed(Button button) {
				 onGuardadButtonPressed();		
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
	
	public void cargarReceta() {
		RecetaListFormatItem aux = (RecetaListFormatItem) recetApp.getPrincipalWindow().getRecetasPane().getRecetasTable().getSelectedRow();
		
		RecetaItem receta;
		try {
			receta = ServiceLocator.getRecetasService().obtenerReceta(aux.getId());
			nombreText.setText(receta.getNombre());
			cantidadText.setText("" + receta.getCantidad());
			paraListButton.setSelectedItem(receta.getPara());
			categoriaListButton.setSelectedItem(receta.getCategoria());
			tTotalMSpinner.setSelectedIndex(receta.getTiempoTotal() / 60);
			tTotalSSpinner.setSelectedIndex(receta.getTiempoTotal() % 60);
			tThermoMSpinner.setSelectedIndex(receta.getTiempoThermomix() / 60);
			tThermoSSpinner.setSelectedIndex(receta.getTiempoThermomix() % 60);
			
			java.util.List<SeccionItem> secciones = receta.getSecciones();
			for (int i = 0; i < secciones.size(); i++) {
				ComponenteReceta c = null;
				try {
					c = (ComponenteReceta) loadComponent("/dad/recetapp/ui/ComponenteReceta.bxml");
				} catch (IOException | SerializationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				recetasTab.getTabs().insert(c, i);
				
				//Poner el título de la sección en las pestañas
				ButtonData buttonData = new ButtonData();
				buttonData.setText(secciones.get(i).getNombre());
				TabPane.setTabData(c, buttonData);
				
				c.setSeccion(secciones.get(i).getNombre());
				
				//Añadimos los ingredientes a la sección
				for (IngredienteItem ingrediente : secciones.get(i).getIngredientes()) {
					c.getIngredientes().add(ingrediente);
				}
				
				//Añadimos las instrucciones a la sección
				for (InstruccionItem instruccion : secciones.get(i).getInstrucciones()) {
					c.getInstrucciones().add(instruccion);
				}
				
				//Seleccionamos la ventana actual para que se muestre seleccionada siempre la última
				recetasTab.setSelectedIndex(i);
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private Component loadComponent(String bxmlFile) throws IOException, SerializationException {
		URL bxmlUrl = RecetApp.class.getResource(bxmlFile);
		BXMLSerializer serializer = new BXMLSerializer();
		return (Component) serializer.readObject(bxmlUrl);
	}

	protected void onGuardadButtonPressed() {
		System.out.println(recetApp);
	}

	protected void onCancelarButtonPressed() {
		close();
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
	
	private List<CategoriaItem> convertirList(java.util.List<CategoriaItem> listUtil) {
		List<CategoriaItem> aux = new ArrayList<CategoriaItem>();
		for(CategoriaItem c : listUtil) {
			aux.add(c);
		}
		return aux;
	}
	
	public void setRecetApp(RecetApp recetApp) {
		this.recetApp = recetApp;
		//Una vez se ha llamado a loadWindow en recetApp, se invoca este método. Ahora recetApp tiene valor.
		cargarReceta();
	}	
}
