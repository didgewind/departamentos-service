package profe.ms.departamentosRest.daos;

import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import profe.empleados.model.Departamento;

@Repository(value = "daoDptosJdbc")
@Lazy
public class DptosDAOJdbc implements DptosDAO {

	private JdbcTemplate jdbcTemplate;

	private interface ConstantesSQL {
		String INSERTA_EMPLEADO_DPTO = "insert into empleados_departamentos values(?, ?)";
		String SELECT_ALL_DEPARTAMENTOS = "select * from departamentos";
		String SELECT_EMPLEADOS_X_DPTO = "select cif from empleados_departamentos where id_departamento = ?";
	}

	RowMapper<Departamento> dptoRowMapper = (ResultSet rs, int numRow) -> {
			Departamento dpto = new Departamento();
			dpto.setId(rs.getString("id"));
			dpto.setDesc(rs.getString("descripcion"));
			return dpto;
	};

	private Logger logger = Logger.getLogger(DptosDAOJdbc.class.getName());

	@Autowired
	public void setDataSource(DataSource departamentosDataSource) {
		this.jdbcTemplate = new JdbcTemplate(departamentosDataSource);
	}

	@Override
	public boolean asignaEmpleadoADpto(String cif, String idDpto) {
		this.jdbcTemplate.update(ConstantesSQL.INSERTA_EMPLEADO_DPTO, cif, idDpto);
		return true;
	}
	
	@Override
	public List<Departamento> getAllDepartamentos() {
		return this.jdbcTemplate.query(ConstantesSQL.SELECT_ALL_DEPARTAMENTOS, 
				dptoRowMapper);
	}


	@Override
	public List<String> getEmpleadosXDpto(String idDpto) {
		return this.jdbcTemplate.queryForList(ConstantesSQL.SELECT_EMPLEADOS_X_DPTO, String.class, idDpto); 
	}

}
