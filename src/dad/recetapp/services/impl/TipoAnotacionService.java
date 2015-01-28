package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.DataBase;
import dad.recetapp.services.ITiposAnotacionesService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.TipoAnotacionItem;

public class TipoAnotacionService implements ITiposAnotacionesService{

	@Override
	public void crearTipoAnotacion(TipoAnotacionItem TipoAnotacion)
			throws ServiceException {
		try {
			if (TipoAnotacion == null || TipoAnotacion.getDescripcion() == null) {
				throw new ServiceException("Error al crear el tipo de anotacion: debe especificar el tipo de anotacion");
			}			
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("insert into tipos_anotaciones (descripcion) values (?)");
			statement.setString(1, TipoAnotacion.getDescripcion());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear el tipo de anotacion '" + TipoAnotacion.getDescripcion() + "'", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al crear el tipo de anotacion: el tipo de anotacion no puede ser nula", e);
		}
	}

	@Override
	public void modificarTipoAnotacion(TipoAnotacionItem TipoAnotacion)
			throws ServiceException {
		try{
			if (TipoAnotacion.getId() == null) {
				throw new ServiceException("Error al recuperar el tipo de anotacion: Debe especificar el identificador");
			}
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("update tipos_anotaciones set descripcion = ? where id = ?");
			statement.setString(1, TipoAnotacion.getDescripcion());
			statement.setLong(2, TipoAnotacion.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al modificar el tipo de anotacion con ID '" + TipoAnotacion.getId()+ "'", e);
		}
	}

	@Override
	public void eliminarTipoAnotacion(Long id) throws ServiceException {
		try{
			if (id == null) {
				throw new ServiceException("Error al recuperar el tipo de anotacion: Debe especificar el identificador");
			}
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("delete from tipos_anotaciones where id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al eliminar el tipo de anotacion con ID '" + id + "'", e);
		}
	}

	@Override
	public List<TipoAnotacionItem> listarTipoAnotacion() throws ServiceException {
		List<TipoAnotacionItem> listTipoAnotacion = new ArrayList<TipoAnotacionItem>();
		TipoAnotacionItem tipoAnotacion = null;
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select id,descripcion from tipos_anotaciones");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				tipoAnotacion = new TipoAnotacionItem();
				tipoAnotacion.setId(rs.getLong("id")); // categoria.setId(id);
				tipoAnotacion.setDescripcion(rs.getString("descripcion"));
				listTipoAnotacion.add(tipoAnotacion);
			}
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar el tipo de anotacion", e);
		}
		return listTipoAnotacion;
	}

	@Override
	public TipoAnotacionItem obtenerTipoAnotacion(Long id)
			throws ServiceException {
		TipoAnotacionItem tipoAnotacion = null;
		try {
			if (id == null) {
				throw new ServiceException("Error al recuperar el tipo de anotacion: Debe especificar el identificador");
			}
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select id,descripcion from tipos_anotaciones where id = ?"); 
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				tipoAnotacion = new TipoAnotacionItem();
				tipoAnotacion.setId(rs.getLong("id")); // categoria.setId(id);
				tipoAnotacion.setDescripcion(rs.getString("descripcion"));
			}
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar el tipo de anotacion con ID '" + id + "'", e);
		}
		return tipoAnotacion;
	}

}
