package dad.recetapp.services.items;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecetaItem {
	private Long id;
	private String nombre;
	private Date fechaCreacion;
	private Integer cantidad;
	private String para;
	private Integer tiempoTotal;
	private Integer tiempoThermomix;
	private CategoriaItem categoria;
	private List<AnotacionItem> anotaciones;
	private List<SeccionItem> secciones;

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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public Integer getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(Integer tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	public Integer getTiempoThermomix() {
		return tiempoThermomix;
	}

	public void setTiempoThermomix(Integer tiempoThermomix) {
		this.tiempoThermomix = tiempoThermomix;
	}

	public CategoriaItem getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaItem categoria) {
		this.categoria = categoria;
	}

	public List<AnotacionItem> getAnotaciones() {
		if (anotaciones == null) { 
			anotaciones = new ArrayList<AnotacionItem>();
		}
		return anotaciones;
	}

	public List<SeccionItem> getSecciones() {
		if (secciones == null) {
			secciones = new ArrayList<SeccionItem>();
		}
		return secciones;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecetaItem) {
			RecetaItem tipo = (RecetaItem) obj;
			return tipo.getId() == this.id;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return nombre;
	}

}
