package io.ylab.intensive.lesson04.eventsourcing;

public class Message {
    private MessageType messageType;
    private Person person;

    public Message() {
    }

    public Message(MessageType messageType, Person person) {
        this.messageType = messageType;
        this.person = person;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
