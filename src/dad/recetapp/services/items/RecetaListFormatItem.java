package dad.recetapp.services.items;

import java.util.Date;

public class RecetaListFormatItem {
	private Long id;
	private String nombre;
	private Date fechaCreacion;
	private String para;
	private String tiempoTotal;
	private String categoria;

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

	public String getPara() {
		return para;
	}

	public void setPara(Integer cantidad, String para) {
		this.para = cantidad + " " + para;
	}

	public String getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(Integer tiempoTotal) {
		String tt;
		Integer minutos = tiempoTotal / 60;
		Integer segundos = tiempoTotal % 60;
		tt = minutos + "M";
		if(segundos != 0) {
			tt += " " + segundos + "S";
		}
		this.tiempoTotal = tt;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecetaListFormatItem) {
			RecetaListFormatItem tipo = (RecetaListFormatItem) obj;
			return tipo.getId() == this.id;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return nombre;
	}

}
