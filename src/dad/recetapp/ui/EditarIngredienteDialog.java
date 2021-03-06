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
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.TextInput;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoIngredienteItem;

public class EditarIngredienteDialog extends Dialog implements Bindable {
	private Boolean cancelado = true;

	@BXML private PushButton cancelarButton;
	@BXML private PushButton guardarButton;
	@BXML private TextInput cantidadText;
	@BXML private ListButton medidaListButton;
	@BXML private ListButton tipoListButton;

	private Integer cantidad;
	
	@Override
	public void initialize(Map<String, Object> namespace, URL location, Resources resources) {
		initMedidaListButton();
		initTipoListButton();
		
		cancelarButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onCancelarButtonButtonPressed();
			}
		});
		
		guardarButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				onAnadirButtonButtonPressed();
			}
		});
	}

	private void initMedidaListButton() {
		java.util.List<MedidaItem> aux;
		try {
			aux = ServiceLocator.getMedidasService().listarMedidas();
			MedidaItem medida = new MedidaItem();
			medida.setNombre("<Seleccione la medida>");
			aux.add(0, medida);
			medidaListButton.setListData(convertirListMedida(aux));
			medidaListButton.setSelectedIndex(0);
		} catch (ServiceException e) {
			Prompt mensaje = new Prompt(e.getMessage());
			mensaje.open(this.getWindow());
		}
	}
	
	private List<MedidaItem> convertirListMedida(java.util.List<MedidaItem> listUtil) {
		List<MedidaItem> aux = new ArrayList<MedidaItem>();
		for(MedidaItem c : listUtil) {
			aux.add(c);
		}
		return aux;
	}
	
	private void initTipoListButton() {
		java.util.List<TipoIngredienteItem> aux;
		try {
			aux = ServiceLocator.getTiposIngredienteService().listarTiposIngredientes();
			TipoIngredienteItem tipo = new TipoIngredienteItem();
			tipo.setNombre("<Seleccione el tipo de ingrediente>");
			aux.add(0, tipo);
			tipoListButton.setListData(convertirListTipo(aux));
			tipoListButton.setSelectedIndex(0);
		} catch (ServiceException e) {
			Prompt mensaje = new Prompt(e.getMessage());
			mensaje.open(this.getWindow());
		}
	}
	
	private List<TipoIngredienteItem> convertirListTipo(java.util.List<TipoIngredienteItem> listUtil) {
		List<TipoIngredienteItem> aux = new ArrayList<TipoIngredienteItem>();
		for(TipoIngredienteItem c : listUtil) {
			aux.add(c);
		}
		return aux;
	}
	
	protected void onAnadirButtonButtonPressed() {
		Boolean error = false;
		try {
			StringBuffer mensajeError = new StringBuffer();
			
			cantidad = Integer.parseInt(cantidadText.getText());
			
			if(medidaListButton.getSelectedIndex() == 0) {
				mensajeError.append("Debes seleccionar una medida\n");
				error = true;
			}
			
			if(tipoListButton.getSelectedIndex() == 0) {
				mensajeError.append("Debes seleccionar un tipo ingrediente");
				error = true;
			}
			if(mensajeError.length() != 0) {
				Prompt mensaje = new Prompt(mensajeError.toString());
				mensaje.open(this.getWindow());
			}
		
			if(!error){
				cancelado = false;
				close();
			}
		}catch (NumberFormatException e){
			Prompt mensaje = new Prompt("Error en el campo cantidad");
			mensaje.open(this.getWindow());
		}
	}

	protected void onCancelarButtonButtonPressed() {
		close();
	}

	public Boolean getCancelado() {
		return cancelado;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(Integer cantidad) {
		cantidadText.setText("" + cantidad);
	}

	public MedidaItem getMedidaSelectedItem() {
		return (MedidaItem) medidaListButton.getSelectedItem();
	}
	
	public void setMedidaSelectedItem(MedidaItem medida) {
		medidaListButton.setSelectedItem(medida);
	}
	
	public TipoIngredienteItem getTipoSelectedItem() {
		return (TipoIngredienteItem) tipoListButton.getSelectedItem();
	}
	
	public void setTipoSelectedItem(TipoIngredienteItem medida) {
		tipoListButton.setSelectedItem(medida);
	}
}
