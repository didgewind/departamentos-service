package profe.ms.empleadosweb.services;

import profe.empleados.model.Empleado;

public interface EmpleadosService {

	Empleado[] getAllEmpleados();
	
	Empleado getEmpleado(String cif);
	
	void insertaEmpleado(Empleado emp);
	
	void modificaEmpleado(Empleado emp);
	
	void eliminaEmpleado(String cif);
	
}
