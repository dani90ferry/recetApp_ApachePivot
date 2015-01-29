package dad.recetapp.services.items;

public class MedidaItem {
	private Long id;
	private String nombre;
	private String abreviatura;

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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MedidaItem) {
			MedidaItem tipo = (MedidaItem) obj;
			return tipo.getId() == this.id;
		}
		return false;
	}

}
