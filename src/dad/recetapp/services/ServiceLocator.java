package dad.recetapp.services;

import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.impl.MedidasService;

public class ServiceLocator {
	private static final ICategoriasService categoriasService = new CategoriasService();
	private static final IMedidasService medidasService = new MedidasService();
	
	private ServiceLocator() {}
	
	public static ICategoriasService getCategoriasService() {
		return categoriasService;
	}
	
	public static IMedidasService getMedidasService() {
		return medidasService;
	}
}
