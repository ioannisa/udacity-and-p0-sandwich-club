package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // declare the four text views that will hold the the values of the sandwitch data
    TextView mAlsoKnownAsTV, mIngredientsTV, mDescriptionTV, mPlaceOfOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // associate the resources to the defined member TextView variables
        mAlsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        mDescriptionTV = (TextView) findViewById(R.id.description_tv);
        mPlaceOfOrigin = (TextView) findViewById(R.id.origin_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method retrieves the sandwitch data and displays this data at the
     * defined text views.
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {
        // Retrieve the "Known As" list - populate the text view separating with coma each value
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        String alsoKnownAsStr = "";
        for (int i=0; i<alsoKnownAs.size(); i++){
            if (i>0){
                alsoKnownAsStr += ", ";
            }
            alsoKnownAsStr += alsoKnownAs.get(i);
        }
        mAlsoKnownAsTV.setText(alsoKnownAsStr);

        // Retrieve the "Ingredients" list - populate the text view separating with coma each value
        List<String> ingredients = sandwich.getIngredients();
        String ingredientsStr = "";
        for (int i=0; i<ingredients.size(); i++){
            if (i>0){
                ingredientsStr += ", ";
            }
            ingredientsStr += ingredients.get(i);
        }
        mIngredientsTV.setText(ingredientsStr);

        // Set the fetched data of description
        mDescriptionTV.setText(sandwich.getDescription());

        // Set the fetched data of origin
        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
    }
}
