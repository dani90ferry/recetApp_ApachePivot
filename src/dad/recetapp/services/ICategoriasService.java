package dad.recetapp.services;

import java.util.List;

import dad.recetapp.services.items.CategoriaItem;

public interface ICategoriasService {
	
	public void crearCategoria(CategoriaItem categoria) throws ServiceException;
	public void modificarCategoria(CategoriaItem categoria) throws ServiceException;
	public void eliminarCategoria(Long id) throws ServiceException;
	public List<CategoriaItem> listarCategorias() throws ServiceException;
	public CategoriaItem obtenerCategoria(Long id) throws ServiceException;

}
