package profe.ms.departamentosRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

import profe.ms.empleadosweb.services.EmpleadosService;
import profe.ms.empleadosweb.services.EmpleadosServiceRibbonProgramado;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
@EnableCircuitBreaker
public class DepartamentosServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "departamentos-service");
		SpringApplication.run(DepartamentosServer.class, args);
	}

	@Bean
	public EmpleadosService empleadosService() {
		return new EmpleadosServiceRibbonProgramado();
	}
	
}
