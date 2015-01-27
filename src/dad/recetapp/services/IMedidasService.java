package dad.recetapp.services;

import java.util.List;

import dad.recetapp.services.items.MedidaItem;

public interface IMedidasService {
	
	public void crearMedida(MedidaItem categoria) throws ServiceException;
	public void modificarMedida(MedidaItem categoria) throws ServiceException;
	public void eliminarMedida(Long id) throws ServiceException;
	public List<MedidaItem> listarMedidas() throws ServiceException;
	public MedidaItem obtenerMedida(Long id) throws ServiceException;

}
