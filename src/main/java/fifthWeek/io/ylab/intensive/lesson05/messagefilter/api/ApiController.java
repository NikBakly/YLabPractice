package fifthWeek.io.ylab.intensive.lesson05.messagefilter.api;

import org.springframework.stereotype.Component;

@Component
public class ApiController {
    private final ConsumerApi consumerApi;
    private final ProducerApi producerApi;

    public ApiController(ConsumerApi consumerApi, ProducerApi producerApi) {
        this.consumerApi = consumerApi;
        this.producerApi = producerApi;
    }

    public String messageProcessing(String message) {
        producerApi.sendMessage(message);
        return consumerApi.getProcessedMessage();
    }


}
