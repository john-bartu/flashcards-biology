package pl.janbartula.morethanbiology;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import io.realm.Realm;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pl.janbartula.morethanbiology.Utilities.FlashCard;
import pl.janbartula.morethanbiology.Utilities.FlashDataset;

import java.io.IOException;
import java.io.InputStream;

public class MenuActivity extends AppCompatActivity
{

    private FlashDataset flashDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        Realm.init(this.getApplicationContext());


        final TextView searchedDefinition;
        final EditText searchWord;
        final TextView searchedWord;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Log.i("JB", "Activity loaded");

        flashDataset = new FlashDataset();


        Button clickButton = findViewById(R.id.defaultbutton);

        searchWord = findViewById(R.id.textSearch);
        searchedWord = findViewById(R.id.textWord);
        searchedDefinition = findViewById(R.id.textDefinition);
        clickButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                LoadDefinitionActivity();
            }
        });


        searchedWord.setText("");
        searchedDefinition.setText("");


        try
        {
            JSONObject biologyData = new JSONObject(loadJSONFromAsset("biologydata.json"));

            JSONArray flashcards = biologyData.getJSONArray("FLASHCARDS");
            Log.i("JB", "Number of flashcards: " + flashcards.length());


            for (int i = 0; i < flashcards.length(); i++)
            {
                JSONObject c = flashcards.getJSONObject(i);
                int id = c.getInt("ID");
                String front = c.getString("FRONT");
                String back = c.getString("BACK");

                Log.i("JB", "Flashcard:  " + id + " | " + front + " | " + back);
                flashDataset.Add(new FlashCard(id, front, back));
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        searchWord.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            public void afterTextChanged(Editable s)
            {
                String wordToFindText = searchWord.getText().toString();

                if (wordToFindText.length() >= 2)
                {
                    FlashCard foundedFlash = flashDataset.Find(wordToFindText);

                    if (foundedFlash != null)
                    {

                        searchedWord.setText(foundedFlash.getFront());
                        searchedDefinition.setText(foundedFlash.getBack());
                    } else
                    {
                        searchedWord.setText(R.string.nothing_found);
                        searchedDefinition.setText("");
                    }
                }
            }
        });
    }


    private String loadJSONFromAsset(String namefile)
    {
        String json;
        try
        {
            InputStream is = getAssets().open(namefile);

            int size = is.available();


            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void LoadDefinitionActivity()
    {
        Intent i = new Intent(MenuActivity.this, DefinitionActivity.class);
        i.putExtra("DataBase", flashDataset);
        startActivityForResult(i, 0);
    }
}
