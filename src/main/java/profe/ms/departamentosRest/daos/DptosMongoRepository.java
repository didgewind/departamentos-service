package profe.ms.departamentosRest.daos;

import org.springframework.data.mongodb.repository.MongoRepository;

import profe.ms.empleados.model.Departamento;

public interface DptosMongoRepository extends MongoRepository<Departamento, String> {

}
