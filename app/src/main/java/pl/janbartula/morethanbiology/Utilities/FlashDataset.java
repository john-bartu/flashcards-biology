package pl.janbartula.morethanbiology.Utilities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FlashDataset implements Serializable
{
    List<FlashCard> flashCardList;
    int actualIndex;

    public FlashDataset(){
        flashCardList = new LinkedList<>();
    }


    public void Add(FlashCard flashCard){
        flashCardList.add(flashCard);
    }

    public void Shuffle(){
        Collections.shuffle(flashCardList);
        actualIndex=0;
    }

    public FlashCard GetActualCard(){
        return flashCardList.get(actualIndex);
    }

    public void Next(){
        actualIndex++;
    }

}
