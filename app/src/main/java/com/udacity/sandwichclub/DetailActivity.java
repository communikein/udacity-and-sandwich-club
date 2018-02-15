package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mBinding.imageImageview);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if (sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (String item : sandwich.getAlsoKnownAs())
                builder.append(item).append(", ");
            builder.deleteCharAt(builder.length() - 1);
            builder.deleteCharAt(builder.length() - 1);

            mBinding.alsoKnownAsTextview.setText(builder.toString());
        }
        else {
            mBinding.alsoKnownAsLabel.setVisibility(View.GONE);
            mBinding.alsoKnownAsTextview.setVisibility(View.GONE);
        }


        if (!sandwich.getDescription().isEmpty()) {
            mBinding.descriptionTextview.setText(sandwich.getDescription());
        }
        else {
            mBinding.descriptionLabel.setVisibility(View.GONE);
            mBinding.descriptionTextview.setVisibility(View.GONE);
        }


        if (sandwich.getIngredients().size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (String item : sandwich.getIngredients())
                builder.append(item).append(", ");
            builder.deleteCharAt(builder.length() - 1);
            builder.deleteCharAt(builder.length() - 1);

            mBinding.ingredientsTextview.setText(builder.toString());
        }
        else {
            mBinding.ingredientsLabel.setVisibility(View.GONE);
            mBinding.ingredientsTextview.setVisibility(View.GONE);
        }


        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            mBinding.originTextview.setText(sandwich.getPlaceOfOrigin());
        }
        else {
            mBinding.originLabel.setVisibility(View.GONE);
            mBinding.originTextview.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();

                return true;
        }

        return false;
    }
}
