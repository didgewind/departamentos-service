package profe.ms.departamentosRest.sagas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import profe.empleados.model.EmpleadosEvent;
import profe.ms.departamentosRest.daos.DptosDAO;

@Service
@Transactional
public class EmpleadosEventsListener {

	@Autowired
	private DptosDAO dao;
	
	@Autowired
	private SagaEventsProducer sagaProducer;
	
	private static final Logger logger = LoggerFactory.getLogger(EmpleadosEventsListener.class);

    @KafkaListener(topics = "${app.empleadosTopic}")
    public void receive(@Payload EmpleadosEvent event,
                        @Headers MessageHeaders headers) {
    	logger.info("Recibido el mensaje: " + event);
        switch (event.getEventType()) {
        
        case DELETE:
        	dao.eliminaEmpleadoDeDpto(event.getEmpleado().getCif());
        	sagaProducer.commitSagaEvent(event);
        	break;
        	
        case CREATE:
        	dao.asignaEmpleadoADpto(event.getEmpleado().getCif(), event.getIdDepartamento());
        	sagaProducer.commitSagaEvent(event);
        	break;
        }
    }
}
