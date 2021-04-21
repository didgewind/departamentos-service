package profe.ms.departamentosRest.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import profe.empleados.model.Departamento;
import profe.ms.departamentosRest.negocio.DptosNegocio;

@RestController
@RequestMapping("/departamentos")
public class DepartamentosRestController {

	private Logger logger = Logger.getLogger(DepartamentosRestController.class.getName());

	@Autowired
	private DptosNegocio negocio;
	
	@GetMapping
	public List<Departamento> getAllDepartamentos() {
		return negocio.getAllDepartamentos();
	}
	
	@GetMapping("/hystrix")
	public List<Departamento> getAllDepartamentosConHystrix() {
		return negocio.getAllDepartamentosConHystrix();
	}

}
