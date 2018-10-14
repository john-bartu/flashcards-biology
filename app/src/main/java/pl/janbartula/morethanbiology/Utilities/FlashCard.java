package pl.janbartula.morethanbiology.Utilities;

import java.io.Serializable;

public class FlashCard implements Serializable
{
    private int id;
    private String front;
    private String back;

    public FlashCard(int id, String front, String back)
    {
        this.id = id;
        this.front = front;
        this.back = back;
    }

    public int getID()
    {
        return id;
    }

    public String getFront()
    {
        return front;
    }

    public String getBack()
    {
        return back;
    }

}
