package beans;

@SuppressWarnings("serial")
public class ExceptionMetier extends Exception {
	
	public ExceptionMetier(String origine) {
		super(origine);
	}
}
