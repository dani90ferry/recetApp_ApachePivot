package dad.recetapp.ui;

import java.io.IOException;
import java.net.URL;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.DialogCloseListener;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TablePane;
import org.apache.pivot.wtk.TableView;

import dad.recetapp.services.items.InstruccionItem;

public class ComponenteReceta extends TablePane implements Bindable {
	private RecetApp recetApp;
	private NuevaInstruccionWindow nuevaInstruccionWindow;
	private NuevoIngredienteWindow nuevoIngedienteWindow;
	private List<InstruccionItem> instrucciones;
	
	@BXML private TableView instruccionesTable;
	@BXML private PushButton aniadirInstruccion;
	@BXML private PushButton aniadirIngrediente;
	
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		instrucciones = new ArrayList<InstruccionItem>();
		instruccionesTable.setTableData(instrucciones);
		
		aniadirInstruccion.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAniadirInstruccionButtonPressed();
			}
		});
		
		aniadirIngrediente.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAniadirIngredienteButtonPressed();
			}
		});
		
	}
	

	protected void onAniadirIngredienteButtonPressed() {
		try {
			nuevoIngedienteWindow = (NuevoIngredienteWindow) RecetApp.loadWindow("/dad/recetapp/ui/NuevoIngredienteWindow.bxml");
			nuevoIngedienteWindow.open(getWindow(), new DialogCloseListener() {
				public void dialogClosed(Dialog dialog, boolean modal) {
					onNuevoIngredienteDialogClosed(dialog);
				}
			});
		} catch (IOException | SerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	protected void onNuevoIngredienteDialogClosed(Dialog dialog) {
		// TODO Auto-generated method stub
		
	}


	protected void onAniadirInstruccionButtonPressed() {
		//recetApp.openNuevaIntruccionWindow();
		try {
			nuevaInstruccionWindow = (NuevaInstruccionWindow) RecetApp.loadWindow("/dad/recetapp/ui/NuevaInstruccionWindow.bxml");
			nuevaInstruccionWindow.open(getWindow(), new DialogCloseListener() {
				public void dialogClosed(Dialog dialog, boolean modal) {
					onNuevaInstruccionDialogClosed(dialog);
				}
			});
		} catch (IOException | SerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void onNuevaInstruccionDialogClosed(Dialog dialog) {
		NuevaInstruccionWindow niw = (NuevaInstruccionWindow) dialog;
		if (!niw.getCancelado()) {
			InstruccionItem instruccion = new InstruccionItem();
			instruccion.setDescripcion(niw.getDescripcion());
			instruccion.setOrden(niw.getOrden());
			instrucciones.add(instruccion);
		}		
	}


	public void setWindowsApp(RecetApp windowsApp) {
		this.recetApp = windowsApp;
	}
	
	public ComponenteReceta getComponenteReceta(){
		return this;
	}
	
}
