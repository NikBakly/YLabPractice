package io.ylab.intensive.lesson05.messagefilter.app.manage;

import io.ylab.intensive.lesson05.messagefilter.app.Producer;

public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;
    private final Producer producer;

    public MessageServiceImpl(MessageDao messageDao, Producer producer) {
        this.messageDao = messageDao;
        this.producer = producer;
    }

    @Override
    public void processMessage(String message) {
        StringBuilder result = new StringBuilder(message);
        int leftPointer = 0;
        int rigthPointer = 0;

        while (rigthPointer < message.length()) {
            if (checkCharByPointer(result, rigthPointer)) {
                if (!checkCharByPointer(result, leftPointer)) {
                    leftPointer = rigthPointer;
                }
            } else {
                if (checkCharByPointer(result, leftPointer)) {
                    String world = result.substring(leftPointer, rigthPointer);
                    if (world.length() >= 3 && messageDao.containsWorld(world)) {
                        result.replace(leftPointer + 1, rigthPointer - 1, "*".repeat(rigthPointer - leftPointer - 2));
                    }
                    leftPointer = rigthPointer;
                }
            }
            ++rigthPointer;
        }

        if (checkCharByPointer(result, leftPointer)) {
            String world = result.substring(leftPointer, rigthPointer);
            if (world.length() >= 3 && messageDao.containsWorld(world)) {
                result.replace(leftPointer + 1, rigthPointer - 1, "*".repeat(rigthPointer - leftPointer - 2));
            }
        }

        producer.sendProcessedMessage(result.toString());
    }

    private boolean checkCharByPointer(StringBuilder result, int pointer) {
        return (result.charAt(pointer) >= 'А' && result.charAt(pointer) <= 'Я')
                || (result.charAt(pointer) >= 'а' && result.charAt(pointer) <= 'я');
    }
}
