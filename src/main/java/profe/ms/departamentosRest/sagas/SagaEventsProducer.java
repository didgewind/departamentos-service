package profe.ms.departamentosRest.sagas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import profe.empleados.model.EmpleadosEvent;
import profe.empleados.model.EmpleadosEventType;
import profe.empleados.model.SagaControlEvent;

@Service
public class SagaEventsProducer {

    @Autowired
    private KafkaTemplate<String, SagaControlEvent> kafkaTemplate;

    @Value("${app.sagaTopic}")
    private String sagaTopic;

    public void sendSagaControlEvent(SagaControlEvent event) {
    	kafkaTemplate.send(sagaTopic, event);
    }
}
