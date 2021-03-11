package profe.ms.departamentosRest.daos;

import java.util.List;

import profe.ms.empleados.model.Departamento;

public interface DptosDAO {

	boolean asignaEmpleadoADpto(String cif, String idDpto);
	
	List<Departamento> getAllDepartamentos();
	
	List<String> getEmpleadosXDpto(String idDpto);

}
