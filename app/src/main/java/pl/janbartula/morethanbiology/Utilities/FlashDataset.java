package pl.janbartula.morethanbiology.Utilities;

import io.realm.Realm;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FlashDataset implements Serializable
{

    private List<FlashCard> flashCardList;
    private List<FlashCardStat> flashStats;
    private int actualIndex;

    public FlashDataset()
    {

        flashCardList = new LinkedList<>();
    }


    public void Add(FlashCard flashCard)
    {
        flashCardList.add(flashCard);
    }


    public void Shuffle()
    {
        //Collections.shuffle(flashCardList);
        actualIndex = 0;
    }

    public FlashCard GetActualCard()
    {
        return flashCardList.get(actualIndex);
    }

    public void Next()
    {
        actualIndex++;
    }

    public FlashCard Find(String text)
    {

        for (FlashCard flashcard : flashCardList
        )
        {
            String word = flashcard.getFront().toLowerCase();

            if (word.contains(text.toLowerCase()))
            {
                return flashcard;
            }
        }

        return null;
    }


    public void ActualCardKnown()
    {
        Realm realm;
        realm = Realm.getDefaultInstance();

        FlashCardStat storedStat = realm.where(FlashCardStat.class).equalTo("id", actualIndex).findFirst();
        realm.beginTransaction();

        if (storedStat == null)
        {
            FlashCardStat newStat = realm.createObject(FlashCardStat.class,actualIndex);
            newStat.Unknown();

        } else
        {
            storedStat.Known();
        }
        realm.commitTransaction();


    }

    public void ActualCardUnknown()
    {
        Realm realm;
        realm = Realm.getDefaultInstance();

        FlashCardStat storedStat = realm.where(FlashCardStat.class).equalTo("id", actualIndex).findFirst();
        realm.beginTransaction();

        if (storedStat == null)
        {
            FlashCardStat newStat = realm.createObject(FlashCardStat.class,actualIndex);

        } else
        {
            storedStat.Unknown();
        }
        realm.commitTransaction();
    }

    public int GetActualKnowledge(){

        Realm realm;
        realm = Realm.getDefaultInstance();

        int knowledge;

        FlashCardStat storedStat = realm.where(FlashCardStat.class).equalTo("id", actualIndex).findFirst();
        realm.beginTransaction();


        if (storedStat == null)
        {
            realm.createObject(FlashCardStat.class, actualIndex);

            knowledge=0;

        } else
        {
            knowledge = storedStat.GetKnownledge();
        }

        realm.commitTransaction();

        return knowledge;


    }
}
