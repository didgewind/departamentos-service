package profe.ms.empleadosweb.exceptions;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		switch(response.getStatusCode()) {
		
		case CONFLICT:
			System.out.println("Error. ¿Empleado duplicado?");
			throw new EmpleadoDuplicadoException();
			
		case NOT_FOUND:
			System.out.println("Error. ¿Empleado no existe?");
			throw new EmpleadoNotFoundException();
			
		default:
			super.handleError(response);
			
		}
		
	}

}
