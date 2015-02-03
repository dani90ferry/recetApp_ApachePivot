package dad.recetapp.ui;

import java.io.IOException;
import java.net.URL;
















import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.ButtonStateListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentDataListener;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.ComponentStateListener;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.ListButtonSelectionListener;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.Spinner;
import org.apache.pivot.wtk.SpinnerSelectionListener;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TextInput;






import org.apache.pivot.wtk.Button.State;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaListFormatItem;
import dad.recetapp.services.items.RecetaListItem;

public class RecetasPane extends TablePane implements Bindable {
	private RecetApp recetApp;
	private List<RecetaListFormatItem> recetas;
	
	@BXML private TableView recetasTable;
	@BXML private TextInput nombreText;
	@BXML private Spinner minutosSpinner;
	@BXML private Spinner segundosSpinner;
	@BXML private ListButton categoriasListButton;
	@BXML private PushButton aniadirButton;
	@BXML private PushButton eliminarButton;
	@BXML private PushButton editarButton;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		recetas = new ArrayList<RecetaListFormatItem>();
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
		
		minutosSpinner.getSpinnerSelectionListeners().add(new SpinnerSelectionListener.Adapter() {
			@Override
			public void selectedItemChanged(Spinner spinner, Object previousSelectedItem) {
				aplicarFiltro();
			}
		});
		
		segundosSpinner.getSpinnerSelectionListeners().add(new SpinnerSelectionListener.Adapter() {
			@Override
			public void selectedItemChanged(Spinner spinner, Object previousSelectedItem) {
				aplicarFiltro();
			}
		});
		
		categoriasListButton.getListButtonSelectionListeners().add(new ListButtonSelectionListener.Adapter() {
			@Override
			public void selectedItemChanged(ListButton listButton, Object previousSelectedItem) {
				aplicarFiltro();
			}
		});
		
		aniadirButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAniadirButtonPressed();
			}
		});
		
		eliminarButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button arg0) {
				onEliminarButtonPressed();
			}
		});
		
		editarButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEditarButtonPressed();
			}
		});
	}
	
	public TableView getRecetasTable() {
		return recetasTable;
	}

	protected void aplicarFiltro() {
		java.util.List<RecetaListItem> aux;
		recetas.clear();
		try {
				Integer tiempoTotal = minutosSpinner.getSelectedIndex() * 60 + segundosSpinner.getSelectedIndex();
				//Para que no desaparezcan recetas de la tabla al no haber indicado ningún tiempo.
				if(tiempoTotal == 0)
					tiempoTotal = null;
				Long idCategoria = ((CategoriaItem)categoriasListButton.getSelectedItem()).getId();
				aux = ServiceLocator.getRecetasService().buscarRecetas(nombreText.getText(), tiempoTotal, idCategoria);
			for (RecetaListItem c : aux) {
				recetas.add(convert(c));
			}
		} catch (ServiceException e) {
			
		}
		//Actualizar el número de recetas
		recetApp.getPrincipalWindow().setNumRecetasText("" + recetas.getLength());
	}
	
	private RecetaListFormatItem convert(RecetaListItem rci) {
		RecetaListFormatItem rlfi = new RecetaListFormatItem();
		rlfi.setId(rci.getId());
		rlfi.setNombre(rci.getNombre());
		rlfi.setFechaCreacion(rci.getFechaCreacion());
		rlfi.setPara(rci.getCantidad(), rci.getPara());
		rlfi.setTiempoTotal(rci.getTiempoTotal());
		rlfi.setCategoria(rci.getCategoria());
		return rlfi;
	}

	private void initRecetasTable() {
		try {
			java.util.List<RecetaListItem> aux = ServiceLocator.getRecetasService().listarRecetas();
			for (RecetaListItem c : aux) {
				recetas.add(convert(c));
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
	
	protected void onEliminarButtonPressed() {
		StringBuffer mensaje = new StringBuffer();
		mensaje.append("¿Desea eliminar las siguientes recetas?\n\n");
		
		java.util.List<RecetaListFormatItem> eliminados = new java.util.ArrayList<RecetaListFormatItem>();
		Sequence<?> seleccionados = recetasTable.getSelectedRows();
		
		if(seleccionados.getLength() != 0) {
			for (int i = 0; i < seleccionados.getLength(); i++) {
				mensaje.append("- " + ((RecetaListFormatItem)seleccionados.get(i)).getNombre() + "\n");
			}
			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("Sí", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
					if (confirmar.getResult() && confirmar.getSelectedOption().equals("Sí")) {
						for (int i = 0; i < seleccionados.getLength(); i++) {
							eliminados.add((RecetaListFormatItem) seleccionados.get(i));
							recetas.remove((RecetaListFormatItem)seleccionados.get(i));
						}
						for (RecetaListFormatItem e : eliminados) {
							try {
								ServiceLocator.getRecetasService().eliminarReceta(e.getId());
							} catch (ServiceException e1) {
							
							}
						}
						recetApp.getPrincipalWindow().setNumRecetasText("" + recetas.getLength());
					}
				}
			});
		}
		//Recargar la tabla
//		No sirve
//		recetas.clear();
//		initRecetasTable();
		//Actualizar el número de recetas
		
	}
	
	protected void onAniadirButtonPressed() {
		recetApp.openNuevaRecetaWindow();
	}
	
	public void setWindowsApp(RecetApp windowsApp) {
		this.recetApp = windowsApp;
	}
}
