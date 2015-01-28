package dad.recetapp.services;

import java.util.List;

import dad.recetapp.services.items.MedidaItem;

public interface IMedidasService {
	
	public void crearMedida(MedidaItem medida) throws ServiceException;
	public void modificarMedida(MedidaItem medida) throws ServiceException;
	public void eliminarMedida(Long id) throws ServiceException;
	public List<MedidaItem> listarMedidas() throws ServiceException;
	public MedidaItem obtenerMedida(Long id) throws ServiceException;

}
