package profe.ms.departamentosRest.negocio;

import java.util.List;

import profe.ms.empleados.model.Departamento;

public interface DptosNegocio {

	boolean asignaEmpleadoADpto(String cif, String idDpto);
	
	List<Departamento> getAllDepartamentos();

	List<Departamento> getAllDepartamentosConHystrix();
}
