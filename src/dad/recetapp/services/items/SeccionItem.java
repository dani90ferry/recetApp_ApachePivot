package dad.recetapp.services.items;

import java.util.ArrayList;
import java.util.List;

public class SeccionItem {
	private Long id;
	private String nombre;
	private List<IngredienteItem> ingredientes = new ArrayList<IngredienteItem>();
	private List<InstruccionItem> instrucciones = new ArrayList<InstruccionItem>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<IngredienteItem> getIngredientes() {
		return ingredientes;
	}

	public List<InstruccionItem> getInstrucciones() {
		return instrucciones;
	}

}
