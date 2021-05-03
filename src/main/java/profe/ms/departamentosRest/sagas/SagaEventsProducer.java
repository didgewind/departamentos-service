package profe.ms.departamentosRest.sagas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import profe.empleados.model.EmpleadosEvent;
import profe.empleados.model.SagaControlEvent;
import profe.empleados.model.SagaOperationResult;

@Service
public class SagaEventsProducer {

	private static final Logger logger = LoggerFactory.getLogger(SagaEventsProducer.class);

	@Autowired
    private KafkaTemplate<String, SagaControlEvent> kafkaTemplate;

    @Value("${app.sagaTopic}")
    private String sagaTopic;

    public void commitSagaEvent(EmpleadosEvent event) {
    	this.sendSagaControlEvent(new SagaControlEvent(event, SagaOperationResult.COMMIT)); 

    }
    
    public void rollbackSagaEvent(EmpleadosEvent event) {
    	this.sendSagaControlEvent(new SagaControlEvent(event, SagaOperationResult.ROLLBACK));

    }
    
    public void sendSagaControlEvent(SagaControlEvent event) {
    	kafkaTemplate.send(sagaTopic, event);
    	logger.info("Enviado el mensaje " + event);
    }
}
