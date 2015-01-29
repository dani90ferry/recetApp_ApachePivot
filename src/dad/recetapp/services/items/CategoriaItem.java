package dad.recetapp.services.items;

public class CategoriaItem {
	private Long id;
	private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return descripcion;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CategoriaItem) {
			CategoriaItem tipo = (CategoriaItem) obj;
			return tipo.getId() == this.id;
		}
		return false;
	}
}
