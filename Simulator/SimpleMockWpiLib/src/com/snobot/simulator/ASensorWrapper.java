package com.snobot.simulator;

public class ASensorWrapper
{
    protected String mName;

    public ASensorWrapper(String aName)
    {
        mName = aName;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String aName)
    {
        mName = aName;
    }
}
