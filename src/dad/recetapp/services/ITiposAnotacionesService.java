package dad.recetapp.services;

import java.util.List;

import dad.recetapp.services.items.TipoAnotacionItem;

public interface ITiposAnotacionesService {

	public void crearTipoAnotacion(TipoAnotacionItem TipoAnotacion) throws ServiceException;
	public void modificarTipoAnotacion(TipoAnotacionItem TipoAnotacion) throws ServiceException;
	public void eliminarTipoAnotacion(Long id) throws ServiceException;
	public List<TipoAnotacionItem> listarTipoAnotacion() throws ServiceException;
	public TipoAnotacionItem obtenerTipoAnotacion(Long id) throws ServiceException;

}
