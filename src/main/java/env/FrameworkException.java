package env;

public class FrameworkException extends Exception {

	private static final long serialVersionUID = -4914410867556194122L;

	public FrameworkException() {
		super();
	}

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(String message, Exception e) {
		super(message, e);
	}
}
