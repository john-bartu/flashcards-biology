package pl.janbartula.morethanbiology;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import pl.janbartula.morethanbiology.Utilities.FlashCard;
import pl.janbartula.morethanbiology.Utilities.FlashDataset;
import pl.janbartula.morethanbiology.Utilities.Utils;

public class DefinitionActivity extends AppCompatActivity
{
    private AnimatorSet animRightDisappear;
    private AnimatorSet animLeftAppear;
    private boolean mIsBackVisible = false;
    private boolean IsReadyToFlip = true;
    private View FrontSide;
    private View BackSide;

    TextView frontText;
    TextView backText;

    private FlashDataset flashDataset;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        flashDataset = (FlashDataset) getIntent().getExtras().getSerializable("DataBase");

        setContentView(R.layout.activity_words);
        findViews();
        setUpCard();
        loadAnimations();
        changeCameraDistance();



        Button clickButton = findViewById(R.id.button_next);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nextFlash();
            }
        });

        flashDataset.Shuffle();
        refreshFlash();
    }

    private void refreshFlash(){
        FlashCard flashCard = flashDataset.GetActualCard();

        if(mIsBackVisible)
        {
            AnimateAppear(FrontSide);
            AnimateDisappear(BackSide);
            mIsBackVisible = false;
            setUpCard();
        }
        setUpText(flashCard.getFront(), flashCard.getBack());
    }

    private void nextFlash(){
        flashDataset.Next();
        refreshFlash();


    }




    private void setUpText(String front, String back){
        frontText.setText(front);
        backText.setText(back);
    }

    private void setUpCard()
    {
        ///Hide Back Side
        BackSide.setAlpha(0);
        BackSide.setRotationY(180);
    }

    private void changeCameraDistance()
    {
        int distance = 10000;
        float scale = getResources().getDisplayMetrics().density * distance;
        FrontSide.setCameraDistance(scale);
        BackSide.setCameraDistance(scale);
    }

    private void loadAnimations()
    {
        animRightDisappear = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        animLeftAppear = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews()
    {
        FrontSide = findViewById(R.id.frontCard);
        BackSide = findViewById(R.id.backCard);

        frontText = findViewById(R.id.textFront);
        backText = findViewById(R.id.textBack);
    }

    public void flipCard(View view)
    {

        Log.i("INFO", "FlipClicked");
        if (IsReadyToFlip)
        {
            IsReadyToFlip = false;

            Utils.delay((getResources().getInteger(R.integer.animation_length)), new Utils.DelayCallback()
            {
                @Override
                public void afterDelay()
                {
                    IsReadyToFlip = true;
                }
            });

            Utils.delay(getResources().getInteger(R.integer.animation_length_half), new Utils.DelayCallback()
            {
                @Override
                public void afterDelay()
                {

                }
            });

            if (!mIsBackVisible)
            {
                AnimateAppear(BackSide);
                AnimateDisappear(FrontSide);

                mIsBackVisible = true;
            } else
            {
                AnimateAppear(FrontSide);
                AnimateDisappear(BackSide);
                mIsBackVisible = false;
            }
        }
    }

    private void AnimateAppear(View view)
    {
        animLeftAppear.setTarget(view);
        animLeftAppear.start();
    }

    private void AnimateDisappear(View view)
    {
        animRightDisappear.setTarget(view);
        animRightDisappear.start();
    }

}
