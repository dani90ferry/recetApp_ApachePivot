package dad.recetapp.services.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.DataBase;
import dad.recetapp.services.ITipoIngredientesService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.TipoIngredienteItem;

public class TipoIngredientesService implements ITipoIngredientesService {

	@Override
	public void crearTipoIngrediente(TipoIngredienteItem tipoIngrediente)
			throws ServiceException {
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("insert into tipos_ingredientes (nombre) values (?)");
			statement.setString(1, tipoIngrediente.getNombre());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear el ingrediente '" + 
					tipoIngrediente.getNombre(), e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al crear el ingrediente: El ingrediente no puede ser nulo", e);
		}

	}

	@Override
	public void modificarTipoIngrediente(TipoIngredienteItem tipoIngrediente)
			throws ServiceException {
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("update tipos_ingredientes set nombre = ? where id = ?");
			statement.setString(1, tipoIngrediente.getNombre());
			statement.setLong(2, tipoIngrediente.getId());
			statement.executeUpdate();
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al crear el ingrediente '" + 
					tipoIngrediente.getNombre(), e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al modificar el ingrediente: El ingrediente no puede ser nulo", e);
		}

	}

	@Override
	public void eliminarTipoIngrediente(Long id) throws ServiceException {
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("delete from tipos_ingredientes where id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al eliminar el ingrediente '" + 
					id, e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al eliminar el ingrediente: El ingrediente no puede ser nulo", e);
		}

	}

	@Override
	public List<TipoIngredienteItem> listarTiposIngredientes()
			throws ServiceException {
		List<TipoIngredienteItem> aux = new ArrayList<TipoIngredienteItem>();
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select * from tipos_ingredientes");
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				TipoIngredienteItem tipoIngrediente = new TipoIngredienteItem();
				tipoIngrediente.setId(rs.getLong(1));
				tipoIngrediente.setNombre(rs.getString(2));
				aux.add(tipoIngrediente);
			}
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al listar los ingredientes", e);
		}
		return aux;
	}

	@Override
	public TipoIngredienteItem obtenerTipoIngrediente(Long id)
			throws ServiceException {
		TipoIngredienteItem tipoIngrediente = null;
		try {
			if(id == null) {
				throw new ServiceException("Error al recuperar el ingrediente: Debe especificar el identificador");
			}
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select * from tipos_ingredientes where id = ?");
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				tipoIngrediente = new TipoIngredienteItem();
				tipoIngrediente.setId(rs.getLong("id"));
				tipoIngrediente.setNombre(rs.getString("nombre"));
			}
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar el ingrediente con ID '" + id + "'");
		}
		return tipoIngrediente;
	}
}
