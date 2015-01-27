package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.DataBase;
import dad.recetapp.services.ICategoriasService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.CategoriaItem;

public class CategoriasService implements ICategoriasService {
	
	public CategoriasService() {
		
	}

	@Override
	public void crearCategoria(CategoriaItem categoria) throws ServiceException {
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("insert into categorias (descripcion) values (?)");
			statement.setString(1, categoria.getDescripcion());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear la categoría '" + 
					categoria.getDescripcion(), e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al crear la categoría: La categoría no puede ser nula", e);
		}
	}

	@Override
	public void modificarCategoria(CategoriaItem categoria)
			throws ServiceException {
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("update categorias set descripcion = ? where id = ?");
			statement.setString(1, categoria.getDescripcion());
			statement.setLong(2, categoria.getId());
			statement.executeUpdate();
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al modificar la categoría '" + 
					categoria.getDescripcion(), e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al modificar la categoría: La categoría no puede ser nula", e);
		}
	}

	@Override
	public void eliminarCategoria(Long id) throws ServiceException {
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("delete from categorias where id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al eliminar la categoría '" + 
					id, e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al eliminar la categoría: La categoría no puede ser nula", e);
		}
	}

	@Override
	public List<CategoriaItem> listarCategorias() throws ServiceException {
		List<CategoriaItem> aux = new ArrayList<CategoriaItem>();
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select * from categorias");
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				CategoriaItem categoria = new CategoriaItem();
				categoria.setId(rs.getLong(1));
				categoria.setDescripcion(rs.getString(2));
				aux.add(categoria);
			}
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al listar las categorías", e);
		}
		return aux;
	}

	@Override
	public CategoriaItem obtenerCategoria(Long id) throws ServiceException {
		CategoriaItem categoria = null;
		try {
			if(id == null) {
				throw new ServiceException("Error al recuperar la categoría: Debe especificar el identificador");
			}
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select * from categorias where id = ?");
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				categoria = new CategoriaItem();
				categoria.setId(rs.getLong("id"));
				categoria.setDescripcion(rs.getString("descripcion"));
			}
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar la categoría con ID '" + id + "'");
		}
		return categoria;
	}

}
