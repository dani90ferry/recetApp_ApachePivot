package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dad.recetapp.db.DataBase;
import dad.recetapp.services.IRecetasService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.TipoAnotacionItem;

public class RecetasService implements IRecetasService {

	@Override
	public void crearReceta(RecetaItem receta) throws ServiceException {
		Connection conn = null;
		try {
			conn = DataBase.getConnection();
			conn.setAutoCommit(false);

			// le ponemos fecha de creación a la receta 
			receta.setFechaCreacion(new Date());
			
			// insertar la receta
			PreparedStatement statement = conn.prepareStatement(
				"insert into recetas " +
				"(nombre, fecha_creacion, cantidad, para, tiempo_total, tiempo_thermomix, id_categoria) " + 
				"values (?, ?, ?, ?, ?, ?, ?)",			
				Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, receta.getNombre());
			statement.setDate(2, new java.sql.Date(receta.getFechaCreacion().getTime()));
			statement.setInt(3, receta.getCantidad());
			statement.setString(4, receta.getPara());
			statement.setInt(5, receta.getTiempoTotal());
			statement.setInt(6, receta.getTiempoThermomix());
			statement.setLong(7, receta.getCategoria().getId());
			statement.executeUpdate();
			
			// recuperar el ID de la receta recién creada
			ResultSet rs = statement.getGeneratedKeys();
			Long id = null;
			if (rs.next()) {
				id = rs.getLong(1);
			}
			
			rs.close();
			statement.close();
			
			// crear anotaciones
			// TODO
			
			// crear secciones 
			// TODO
			
			conn.commit();
			conn.setAutoCommit(true);
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new ServiceException("Error al crear la receta '" + receta.getNombre() + "'", e);
		}
	}

	@Override
	public void modificarReceta(RecetaItem receta) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarReceta(Long id) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RecetaListItem> buscarRecetas(String nombre,
			Integer tiempoTotal, Long idCategoria) throws ServiceException {
		List<RecetaListItem> aux = new ArrayList<RecetaListItem>();
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select * from recetas where nombre = ?");
			statement.setString(1, nombre);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				RecetaListItem receta = new RecetaListItem();
				receta.setId(rs.getLong("id"));
				receta.setNombre(rs.getString("nombre"));
				receta.setFechaCreacion(rs.getDate("fecha_creacion"));
				receta.setCantidad(rs.getInt("cantidad"));
				receta.setPara(rs.getString("para"));
				receta.setTiempoTotal(rs.getInt("tiempo_total"));
				receta.setTiempoTotal(rs.getInt("tiempo_total"));
				receta.setTiempoThermomix(rs.getInt("tiempo_thermomix"));
				receta.setCategoria((ServiceLocator.getCategoriasService().obtenerCategoria(rs.getLong("id_categoria")).getDescripcion()));
				aux.add(receta);
			}
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al listar las recetas", e);
		}
		return aux;
	}

	@Override
	public List<RecetaListItem> listarRecetas() throws ServiceException {
		List<RecetaListItem> aux = new ArrayList<RecetaListItem>();
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select * from recetas");
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				RecetaListItem receta = new RecetaListItem();
				receta.setId(rs.getLong("id"));
				receta.setNombre(rs.getString("nombre"));
				receta.setFechaCreacion(rs.getDate("fecha_creacion"));
				receta.setCantidad(rs.getInt("cantidad"));
				receta.setPara(rs.getString("para"));
				receta.setTiempoTotal(rs.getInt("tiempo_total"));
				receta.setTiempoTotal(rs.getInt("tiempo_total"));
				receta.setTiempoThermomix(rs.getInt("tiempo_thermomix"));
				receta.setCategoria((ServiceLocator.getCategoriasService().obtenerCategoria(rs.getLong("id_categoria")).getDescripcion()));
				aux.add(receta);
			}
			statement.close();
		} catch(SQLException e) {
			throw new ServiceException("Error al listar las recetas", e);
		}
		return aux;
	}

	@Override
	public RecetaItem obtenerReceta(Long id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
