package profe.ms.departamentosRest.daos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import profe.ms.empleados.model.Departamento;
import profe.ms.empleados.model.Empleado;

@Repository
@Primary
public class DptosDAOMongo implements DptosDAO {

	@Autowired
	private DptosMongoRepository repository;
	
	@Override
	public boolean asignaEmpleadoADpto(String cif, String idDpto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Departamento> getAllDepartamentos() {
		return repository.findAll();
	}

	@Override
	public List<String> getEmpleadosXDpto(String idDpto) {
		return repository.findById(idDpto).get().getEmpleados().stream()
				.map(Empleado::getCif).collect(Collectors.toList());
	}

}
