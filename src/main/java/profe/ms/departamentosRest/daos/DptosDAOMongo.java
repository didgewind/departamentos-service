package profe.ms.departamentosRest.daos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import profe.empleados.model.Departamento;
import profe.empleados.model.Empleado;

@Repository
@Primary
public class DptosDAOMongo implements DptosDAO {

	@Autowired
	private DptosMongoRepository repository;
	
	@Override
	public boolean asignaEmpleadoADpto(String cif, String idDpto) {
		Departamento dpto = repository.findById(idDpto).get();
		dpto.getEmpleados().add(new Empleado(cif, null, null, 0));
		repository.save(dpto);
		return true;
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

	@Override
	public boolean eliminaEmpleadoDeDpto(String cif) {
		getAllDepartamentos().forEach(dpto -> {
			dpto.getEmpleados().remove(new Empleado(cif, null, null, 0));
			repository.save(dpto);
		});
		return true;
	}

}
