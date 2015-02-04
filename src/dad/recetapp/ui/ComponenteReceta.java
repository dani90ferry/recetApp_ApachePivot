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
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.DialogCloseListener;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.content.ButtonData;

import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;

public class ComponenteReceta extends TablePane implements Bindable {
	private RecetApp recetApp;
	private NuevoIngredienteDialog nuevoIngredienteDialog;
	private EditarIngredienteDialog editarIngredienteDialog;
	private NuevaInstruccionDialog nuevaInstruccionDialog;
	private EditarInstruccionDialog editarInstruccionDialog;
	private List<IngredienteItem> ingredientes;
	private List<InstruccionItem> instrucciones;
	
	@BXML private TableView ingredientesTable;
	@BXML private TableView instruccionesTable;
	@BXML private TextInput seccionText;
	@BXML private PushButton eliminarTab;
	@BXML private PushButton aniadirIngredienteButton;
	@BXML private PushButton editarIngredienteButton;
	@BXML private PushButton eliminarIngredienteButton;
	@BXML private PushButton aniadirInstruccionButton;
	@BXML private PushButton editarInstruccionButton;
	@BXML private PushButton eliminarInstruccionButton;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		ingredientes = new ArrayList<IngredienteItem>();
		ingredientesTable.setTableData(ingredientes);
		instrucciones = new ArrayList<InstruccionItem>();
		instruccionesTable.setTableData(instrucciones);

		
		seccionText.getComponentKeyListeners().add(new ComponentKeyListener.Adapter() {
			@Override
			public boolean keyTyped(Component component, char character) {
				onSeccionTextKeyTyped();
				return false;
			}
		});
		
		eliminarTab.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEliminarTabButtonPressed();
			}});
		
		aniadirIngredienteButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAniadirIngredienteButtonPressed();
			}
		});
		
		editarIngredienteButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEditarIngredienteButtonPressed();
			}
		});
		
		eliminarIngredienteButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEliminarIngredienteButtonPressed();
			}
		});
		
		aniadirInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAniadirInstruccionButtonPressed();
			}
		});
		
		editarInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEditarInstruccionButtonPressed();
			}
		});
		
		eliminarInstruccionButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onEliminarInstruccionButtonPressed();
			}
		});
	}

	protected void onSeccionTextKeyTyped() {
		ButtonData buttonData = new ButtonData();
		buttonData.setText(seccionText.getText());
		TabPane.setTabData(ComponenteReceta.this, buttonData);
	}

	protected void onEliminarTabButtonPressed() {
		NuevaRecetaWindow nuevaRecetaWindow = recetApp.getNuevaRecetaWindow();
		EditarRecetaWindow editarRecetaWindow = recetApp.getEditarRecetaWindow();
		
		if(nuevaRecetaWindow != null)
			recetApp.getNuevaRecetaWindow().removeSelectedTab();
		if(editarRecetaWindow != null)
			recetApp.getEditarRecetaWindow().removeSelectedTab();
	}

	protected void onAniadirIngredienteButtonPressed() {
		try {
			nuevoIngredienteDialog = (NuevoIngredienteDialog) recetApp.loadWindow("/dad/recetapp/ui/bxml/NuevoIngredienteDialog.bxml");
			nuevoIngredienteDialog.setTitle("Nueva ingrediente para '" + seccionText.getText() + "'");
			nuevoIngredienteDialog.open(getWindow(), new DialogCloseListener() {
				public void dialogClosed(Dialog dialog, boolean modal) {
					onNuevoIngredienteDialogClosed(dialog);
				}
			});
		} catch (IOException | SerializationException e) {
			Prompt mensaje = new Prompt(e.getMessage());
			mensaje.open(this.getWindow());
		}
	}
	
	protected void onEditarIngredienteButtonPressed() {
		try {
			Sequence<?> seleccionados = ingredientesTable.getSelectedRows();
			if(seleccionados.getLength() == 1) {	
				IngredienteItem ingrediente = (IngredienteItem) seleccionados.get(0);
			
				editarIngredienteDialog = (EditarIngredienteDialog) recetApp.loadWindow("/dad/recetapp/ui/bxml/EditarIngredienteDialog.bxml");
				editarIngredienteDialog.setTitle("Editar ingrediente para '" + seccionText.getText() + "'");
				editarIngredienteDialog.setCantidad(ingrediente.getCantidad());
				editarIngredienteDialog.setMedidaSelectedItem(ingrediente.getMedida());
				editarIngredienteDialog.setTipoSelectedItem(ingrediente.getTipo());
				editarIngredienteDialog.open(getWindow(), new DialogCloseListener() {
					public void dialogClosed(Dialog dialog, boolean modal) {
						onEditarIngredienteDialogClosed(dialog);
					}
				});
			} else {
				Prompt mensaje = new Prompt("Debe seleccionar un ingrediente");
				mensaje.open(this.getWindow());
			}
		} catch (IOException | SerializationException e) {
			Prompt mensaje = new Prompt(e.getMessage());
			mensaje.open(this.getWindow());
		}
	}
	
	protected void onEliminarIngredienteButtonPressed() {
		StringBuffer mensaje = new StringBuffer();
		mensaje.append("¿Desea eliminar los siguientes ingredientes para " + seccionText.getText() + "?\n\n");
		
		java.util.List<IngredienteItem> eliminados = new java.util.ArrayList<IngredienteItem>();
		Sequence<?> seleccionados = ingredientesTable.getSelectedRows();
		
		if(seleccionados.getLength() != 0) {
			for (int i = 0; i < seleccionados.getLength(); i++) {
				mensaje.append("- " + ((IngredienteItem)seleccionados.get(i)).getTipo().getNombre() + "\n");
			}
			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("Sí", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
					if (confirmar.getResult() && confirmar.getSelectedOption().equals("Sí")) {
						for (int i = 0; i < seleccionados.getLength(); i++) {
							eliminados.add((IngredienteItem) seleccionados.get(i));
							ingredientes.remove((IngredienteItem)seleccionados.get(i));
						}
					}
				}
			});
		} else {
			Prompt m = new Prompt("Debe de seleccionar un ingrediente");
			m.open(this.getWindow());
		}
	}

	protected void onNuevoIngredienteDialogClosed(Dialog dialog) {
		NuevoIngredienteDialog niw = (NuevoIngredienteDialog) dialog;
		if (!niw.getCancelado()) {
			IngredienteItem ingrediente = new IngredienteItem();
			ingrediente.setCantidad(niw.getCantidad());
			ingrediente.setMedida(niw.getMedidaSelectedItem());
			ingrediente.setTipo(niw.getTipoSelectedItem());
			ingredientes.add(ingrediente);
		}		
	}
	
	protected void onEditarIngredienteDialogClosed(Dialog dialog) {
		EditarIngredienteDialog eiw = (EditarIngredienteDialog) dialog;
		if (!eiw.getCancelado()) {
			IngredienteItem ingrediente = (IngredienteItem)ingredientesTable.getSelectedRow();
			ingrediente.setCantidad(eiw.getCantidad());
			ingrediente.setMedida(eiw.getMedidaSelectedItem());
			ingrediente.setTipo(eiw.getTipoSelectedItem());
		}
	}

	protected void onAniadirInstruccionButtonPressed() {
		try {
			nuevaInstruccionDialog = (NuevaInstruccionDialog) recetApp.loadWindow("/dad/recetapp/ui/bxml/NuevaInstruccionDialog.bxml");
			nuevaInstruccionDialog.setTitle("Nueva instrucción para '" + seccionText.getText() + "'");
			nuevaInstruccionDialog.open(getWindow(), new DialogCloseListener() {
				public void dialogClosed(Dialog dialog, boolean modal) {
					onNuevaInstruccionDialogClosed(dialog);
				}
			});
		} catch (IOException | SerializationException e) {
			Prompt mensaje = new Prompt(e.getMessage());
			mensaje.open(this.getWindow());
		}
	}
	
	protected void onEditarInstruccionButtonPressed() {
		try {
			Sequence<?> seleccionados = instruccionesTable.getSelectedRows();
			if(seleccionados.getLength() == 1) {
				InstruccionItem instruccion = (InstruccionItem) seleccionados.get(0);
			
				editarInstruccionDialog = (EditarInstruccionDialog) recetApp.loadWindow("/dad/recetapp/ui/bxml/EditarInstruccionDialog.bxml");
				editarInstruccionDialog.setTitle("Editar instrucción para '" + seccionText.getText() + "'");
				editarInstruccionDialog.setOrden(instruccion.getOrden());
				editarInstruccionDialog.setDescripcion(instruccion.getDescripcion());
				editarInstruccionDialog.open(getWindow(), new DialogCloseListener() {
					public void dialogClosed(Dialog dialog, boolean modal) {
						onEditarInstruccionDialogClosed(dialog);
					}
				}); 
			} else {
				Prompt mensaje = new Prompt("Debe seleccionar una instrucción");
				mensaje.open(this.getWindow());
			}
		} catch (IOException | SerializationException e) {
			Prompt mensaje = new Prompt(e.getMessage());
			mensaje.open(this.getWindow());
		}
	}
	
	protected void onEliminarInstruccionButtonPressed() {
		StringBuffer mensaje = new StringBuffer();
		mensaje.append("¿Desea eliminar las siguientes instrucciones para " + seccionText.getText() + "?\n\n");
		
		java.util.List<InstruccionItem> eliminados = new java.util.ArrayList<InstruccionItem>();
		Sequence<?> seleccionados = instruccionesTable.getSelectedRows();
		
		if(seleccionados.getLength() != 0) {
			for (int i = 0; i < seleccionados.getLength(); i++) {
				mensaje.append("- " + ((InstruccionItem)seleccionados.get(i)).getDescripcion() + "\n");
			}
			Prompt confirmar = new Prompt(MessageType.WARNING, mensaje.toString(), new ArrayList<String>("Sí", "No"));
			confirmar.open(this.getWindow(), new SheetCloseListener() {
				public void sheetClosed(Sheet sheet) {
					if (confirmar.getResult() && confirmar.getSelectedOption().equals("Sí")) {
						for (int i = 0; i < seleccionados.getLength(); i++) {
							eliminados.add((InstruccionItem) seleccionados.get(i));
							instrucciones.remove((InstruccionItem)seleccionados.get(i));
						}
					}
				}
			});
		} else {
			Prompt mensaje2 = new Prompt("Debes de seleccionar un ingrediente");
			mensaje2.open(this.getWindow());
		}
	}

	protected void onNuevaInstruccionDialogClosed(Dialog dialog) {
		NuevaInstruccionDialog niw = (NuevaInstruccionDialog) dialog;
		if (!niw.getCancelado()) {
			InstruccionItem instruccion = new InstruccionItem();
			instruccion.setDescripcion(niw.getDescripcion());
			instruccion.setOrden(niw.getOrden());
			instrucciones.add(instruccion);
		}		
	}
	
	protected void onEditarInstruccionDialogClosed(Dialog dialog) {
		EditarInstruccionDialog niw = (EditarInstruccionDialog) dialog;
		if (!niw.getCancelado()) {
			InstruccionItem instruccion = (InstruccionItem)instruccionesTable.getSelectedRow();
			instruccion.setDescripcion(niw.getDescripcion());
			instruccion.setOrden(niw.getOrden());
		}
	}

	public void setRecetApp(RecetApp recetApp) {
		this.recetApp = recetApp;
	}
	
	public String getSeccion() {
		return seccionText.getText();
	}
	
	public void setSeccion(String seccion) {
		seccionText.setText(seccion);
	}
	
	public List<IngredienteItem> getIngredientes() {
		return ingredientes;
	}
	
	public List<InstruccionItem> getInstrucciones() {
		return instrucciones;
	}
}
