package pl.janbartula.morethanbiology.Utilities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FlashCardStat extends RealmObject
{
    @PrimaryKey
    private int id;

    private int knownledge;

    void Known()
    {
        knownledge++;
    }

    void Unknown()
    {
        if (knownledge > 0)
        {
            knownledge--;
        }
    }

    public FlashCardStat(){
        knownledge=0;
    }

    int GetKnownledge()
    {
        return knownledge;
    }
}
