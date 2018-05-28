package org.dgc.sandbox.webflux.client.domain;

import java.util.List;

public final class MessageBuilder
{
    private String issuerId;
    private Long duration;
    private byte[] payload;
    private List<MessageProperty> properties;
    private String recipientId;

    private MessageBuilder()
    {
    }

    public static MessageBuilder aMessage()
    {
        return new MessageBuilder();
    }

    public MessageBuilder withIssuerId(String issuerId)
    {
        this.issuerId = issuerId;
        return this;
    }

    public MessageBuilder withDuration(Long duration)
    {
        this.duration = duration;
        return this;
    }

    public MessageBuilder withPayload(byte[] payload)
    {
        this.payload = payload;
        return this;
    }

    public MessageBuilder withProperties(List<MessageProperty> properties)
    {
        this.properties = properties;
        return this;
    }

    public MessageBuilder withRecipientId(String recipientId)
    {
        this.recipientId = recipientId;
        return this;
    }

    public Message build()
    {
        Message message = new Message();
        message.setIssuerId(issuerId);
        message.setDuration(duration);
        message.setPayload(payload);
        message.setProperties(properties);
        message.setRecipientId(recipientId);
        return message;
    }
}
