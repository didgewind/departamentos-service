package profe.ms.departamentosRest.negocio;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import profe.ms.departamentosRest.daos.DptosDAO;
import profe.ms.empleados.model.Departamento;
import profe.ms.empleados.model.Empleado;
import profe.ms.empleadosweb.services.EmpleadosService;

@Service
public class DptosNegocioImp implements DptosNegocio {

	@Autowired
	private DptosDAO dao;
	
	@Autowired
	private EmpleadosService empleadosService;

	public boolean asignaEmpleadoADpto(String cif, String idDpto) {
		return dao.asignaEmpleadoADpto(cif, idDpto);
	}

	public List<Departamento> getAllDepartamentos() {
		List<Departamento> dptos = dao.getAllDepartamentos();
		// Recupero todos los empleados del servicio
		HashMap<String, Empleado> empleadosMap = 
				(HashMap<String, Empleado>) Arrays.asList(empleadosService.getAllEmpleados())
				.stream().collect(Collectors.toMap(Empleado::getCif, Function.identity())); 
		// Recupero empleados_departamentos
		dptos.forEach(dpto -> {
			List<Empleado> empleados = dpto.getEmpleados()
					.stream()
					.map(Empleado::getCif)
					.map(cif -> empleadosMap.get(cif)).collect(Collectors.toList());
			dpto.setEmpleados(empleados);
		});
		return dptos;
	}

	@Override
	public List<Departamento> getAllDepartamentosConHystrix() {
		/*
		 * Para probar hystrix vamos a recuperar los empleados del servicio uno a uno,
		 * y para los que fallen devolveremos un empleado dummy
		 */
		// Para cada departamento
		List<Departamento> departamentos = dao.getAllDepartamentos();
		departamentos.forEach((dpto) -> {
			List<Empleado> empleados = dpto.getEmpleados();
			List<String> empleadosCif = dao.getEmpleadosXDpto(dpto.getId());
			// Para cada empleado
			// Recuperar ese empleado del servicio, y sustituirlo en el array
			empleadosCif.forEach(cif -> empleados.add(empleadosService.getEmpleado(cif)));
		});
		return departamentos;
	}
	
	
}
