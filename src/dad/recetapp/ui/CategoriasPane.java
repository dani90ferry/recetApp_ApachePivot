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
import org.apache.pivot.wtk.TextInput;

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

	protected void onAniadirButtonPressed() {
		CategoriaItem nueva = new CategoriaItem();
		nueva.setDescripcion(descripcionText.getText());
		categorias.add(nueva);
		descripcionText.setText("");
	}

	protected void onEliminarButtonPressed() {
		Sequence<?> seleccionados = categoriasTable.getSelectedRows();
		for (int i = 0; i < seleccionados.getLength(); i++) {
			categorias.remove((CategoriaItem)seleccionados.get(i));
		}
	}
}
