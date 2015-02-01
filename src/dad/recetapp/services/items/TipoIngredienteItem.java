package dad.recetapp.services.items;

public class TipoIngredienteItem {
	private Long id;
	private String nombre;

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TipoIngredienteItem) {
			TipoIngredienteItem tipo = (TipoIngredienteItem) obj;
			return tipo.getId() == this.id;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
