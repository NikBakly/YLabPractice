package io.ylab.intensive.lesson05.messagefilter.app;

public class MessageController {
    private final Consumer consumer;

    public MessageController(Consumer consumer) {
        this.consumer = consumer;
    }

    public void startProcessing() {
        consumer.listenMessage();
    }
}
