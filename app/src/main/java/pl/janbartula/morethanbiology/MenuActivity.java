package pl.janbartula.morethanbiology;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.janbartula.morethanbiology.Utilities.FlashCard;
import pl.janbartula.morethanbiology.Utilities.FlashDataset;

import java.io.IOException;
import java.io.InputStream;

public class MenuActivity extends AppCompatActivity {

    FlashDataset flashDataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Log.i("JB", "Activity loaded");

        flashDataset = new FlashDataset();

        try
        {
            JSONObject biologyData = new JSONObject(loadJSONFromAsset("biologydata.json"));

            JSONArray flashcards = biologyData.getJSONArray("FLASHCARDS");
            Log.i("JB", "Number of flashcards: " + flashcards.length());


            for (int i = 0; i < flashcards.length(); i++) {
                JSONObject c = flashcards.getJSONObject(i);
                int id = c.getInt("ID");
                String front = c.getString("FRONT");
                String back = c.getString("BACK");

                Log.i("JB", "Flashcard:  " + id + " | " + front +" | "+ back );
                flashDataset.Add(new FlashCard(id,front,back));
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        Button clickButton = findViewById(R.id.defaultbutton);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoadDefinitionActivity();
            }
        });


    }



    public String loadJSONFromAsset(String namefile) {
        String json = null;
        try {
            InputStream is = getAssets().open(namefile);

            int size = is.available();


            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void LoadDefinitionActivity(){
            Intent i = new Intent(MenuActivity.this, DefinitionActivity.class);
            i.putExtra("DataBase", flashDataset);
            startActivityForResult(i, 0);
    }
}
