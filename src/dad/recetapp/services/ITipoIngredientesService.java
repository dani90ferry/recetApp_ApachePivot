package dad.recetapp.services;

import java.util.List;

import dad.recetapp.services.items.TipoIngredienteItem;

public interface ITipoIngredientesService {

	public void crearTipoIngrediente(TipoIngredienteItem tipoIngrediente) throws ServiceException;
	public void modificarTipoIngrediente(TipoIngredienteItem tipoIngrediente) throws ServiceException;
	public void eliminarTipoIngrediente(Long id) throws ServiceException;
	public List<TipoIngredienteItem> listarTiposIngredientes() throws ServiceException;
	public TipoIngredienteItem obtenerTipoIngrediente(Long id) throws ServiceException;
}
