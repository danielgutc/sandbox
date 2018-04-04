package org.dgc.sandbox.webflux.client.domain;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable
{
    private String issuerId;
    private Long duration;
    private byte[] payload;
    private List<MessageProperty> properties;
    private String recipientId;

    //region Getters/Setters

    public String getIssuerId()
    {
        return issuerId;
    }

    public void setIssuerId(String issuerId)
    {
        this.issuerId = issuerId;
    }

    public Long getDuration()
    {
        return duration;
    }

    public void setDuration(Long duration)
    {
        this.duration = duration;
    }

    public byte[] getPayload()
    {
        return payload;
    }

    public void setPayload(byte[] payload)
    {
        this.payload = payload;
    }

    public List<MessageProperty> getProperties()
    {
        return properties;
    }

    public void setProperties(List<MessageProperty> properties)
    {
        this.properties = properties;
    }

    public String getRecipientId()
    {
        return recipientId;
    }

    public void setRecipientId(String recipientId)
    {
        this.recipientId = recipientId;
    }

    //endregion
}
