package dad.recetapp.services.items;

public class IngredienteItem {
	private Long id;
	private Integer cantidad;
	private MedidaItem medida;
	private TipoIngredienteItem tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public MedidaItem getMedida() {
		return medida;
	}

	public void setMedida(MedidaItem medida) {
		this.medida = medida;
	}

	public TipoIngredienteItem getTipo() {
		return tipo;
	}

	public void setTipo(TipoIngredienteItem tipo) {
		this.tipo = tipo;
	}

}
