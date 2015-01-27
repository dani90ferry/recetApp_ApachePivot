package dad.recetapp.services;


import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.impl.MedidasService;
import dad.recetapp.services.impl.TipoIngredientesService;

public class ServiceLocator {
	private static final ICategoriasService categoriasService = new CategoriasService();
	private static final IMedidasService medidasService = new MedidasService();
	private static final ITipoIngredientesService tipoIngredientesService = new TipoIngredientesService(); 

	private ServiceLocator() {}
	
	public static ICategoriasService getCategoriasService() {
		return categoriasService;
	}
	
	public static IMedidasService getMedidasService() {
		return medidasService;
	}
	
	public static ITipoIngredientesService getTiposIngredienteService() {
		return tipoIngredientesService;
	}
}
