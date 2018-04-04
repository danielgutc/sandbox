package org.dgc.sandbox.webflux.client.domain;

import java.io.Serializable;

public class MessageProperty implements Serializable
{
    private String key;
    private String value;

    //region Getters/Setters

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
    //endregion
}
