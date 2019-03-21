package profe.ms.departamentosRest.controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import profe.ms.empleados.model.Departamento;
import profe.ms.empleados.model.Empleado;
import profe.ms.empleadosweb.services.EmpleadosService;

@RestController
@RequestMapping("/departamentos")
public class DepartamentosRestController {

	private Logger logger = Logger.getLogger(DepartamentosRestController.class.getName());

	private Map<String, Departamento> departamentos;
	
	@Autowired
	private EmpleadosService empleadosService;
	
	@PostConstruct
	public void init() {
		// Crear departamentos y mapa
		departamentos = new HashMap<>();
		Departamento dptRRHH = new Departamento("RRHH", "Recursos Humanos");
		Departamento dptID = new Departamento("I+D", "Informática");
		departamentos.put(dptRRHH.getId(), dptRRHH);
		departamentos.put(dptID.getId(), dptID);
		// Recuperar empleados de ms
		Empleado[] empleados = empleadosService.getAllEmpleados();
		// asignar empleados a dpts
		boolean bRrhh = true;
		for (Empleado emp: empleados) {
			if (bRrhh) {
				dptRRHH.getEmpleados().add(emp);
			} else {
				dptID.getEmpleados().add(emp);
			}
			bRrhh = !bRrhh;
		}
	}
	
	@GetMapping
	public Collection<Departamento> getAllDepartamentos() {
		return departamentos.values();
	}
	


}
