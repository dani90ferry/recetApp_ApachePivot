package dad.recetapp.services;

import dad.recetapp.services.impl.CategoriasService;

public class ServiceLocator {
	private static final ICategoriasService categoriasService = new CategoriasService();
	
	private ServiceLocator() {}
	
	public static ICategoriasService getCategoriasService() {
		return categoriasService;
	}
}
