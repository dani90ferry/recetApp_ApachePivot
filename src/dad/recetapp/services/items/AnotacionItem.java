package dad.recetapp.services.items;

public class AnotacionItem {
	private Long id;
	private String anotaciones;
	private TipoAnotacionItem tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public TipoAnotacionItem getTipo() {
		return tipo;
	}

	public void setTipo(TipoAnotacionItem tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AnotacionItem) {
			AnotacionItem tipo = (AnotacionItem) obj;
			return tipo.getId() == this.id;
		}
		return false;
	}

}
