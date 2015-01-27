package dad.recetapp.services;

@SuppressWarnings("serial")
public class ServiceException extends Exception {

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable th) {
		super(th);
	}

	public ServiceException(String message, Throwable th) {
		super(message, th);
	}

}