package profe.ms.empleadosweb.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import profe.ms.empleados.model.Empleado;
import profe.ms.empleadosweb.exceptions.EmpleadoDuplicadoException;
import profe.ms.empleadosweb.exceptions.EmpleadosException;
import profe.ms.empleadosweb.exceptions.RestTemplateErrorHandler;

@Service
@Primary
@Retryable(exclude = { EmpleadosException.class }, include = { Exception.class } )
public class EmpleadosServiceRibbonProgramado implements EmpleadosService {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	
	private Logger logger = Logger.getLogger(EmpleadosServiceRibbonProgramado.class.getName());
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	@Value("${app.empleadosServiceAlias}")
	private String empleadosServiceAlias;
	
	private String getBaseUrl() {
		ServiceInstance instance = loadBalancerClient.choose(this.empleadosServiceAlias);
		logger.info(String.format("Invocando el microservicio del puerto %s", instance.getPort()));
		return instance.getUri().toString() + "/empleados/";
	}
	
	private RestTemplate getRestTemplate() {
		return restTemplateBuilder
				.errorHandler(new RestTemplateErrorHandler())
				.build();
	}
	
	@Override
	public Empleado[] getAllEmpleados() {
		return getRestTemplate().getForObject(getBaseUrl(), Empleado[].class);
	}

	@Override
	public Empleado getEmpleado(String cif) {
		return getRestTemplate().getForObject(getBaseUrl() + cif, Empleado.class);
	}

	@Override
	public void insertaEmpleado(Empleado emp) {
		getRestTemplate().postForLocation(getBaseUrl(), emp);
	}

	@Override
	public void modificaEmpleado(Empleado emp) {
		getRestTemplate().put(getBaseUrl() + emp.getCif(), emp);
	}

	@Override
	public void eliminaEmpleado(String cif) {
		getRestTemplate().delete(getBaseUrl() + cif);
	}

}
