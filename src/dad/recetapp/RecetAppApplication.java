package dad.recetapp;

import java.io.IOException;
import java.net.URL;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;

import dad.recetapp.ui.VentanaPrincipalWindow;

public class RecetAppApplication implements Application{
	private VentanaPrincipalWindow ventanaPrincipalWindow = null;
	
	@Override
	public void suspend() throws Exception { }

	@Override
	public void resume() throws Exception {	}

	@Override
	public boolean shutdown(boolean optional) throws Exception {
		return false;
	}

	@Override
	public void startup(Display display, Map<String, String> properties)
			throws Exception {
		ventanaPrincipalWindow = (VentanaPrincipalWindow) loadWindow("dad/recetapp/ui/VentanaPrincipalWindow.bxml");
		ventanaPrincipalWindow.open(display);
	}
	
	public static Window loadWindow(String bxmlFile) throws IOException, SerializationException {
		URL bxmlUrl = RecetAppApplication.class.getClassLoader().getResource(bxmlFile);
		BXMLSerializer serializer = new BXMLSerializer();
		return (Window) serializer.readObject(bxmlUrl);
	}
}
