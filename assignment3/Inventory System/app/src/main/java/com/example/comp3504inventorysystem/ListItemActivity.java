package com.example.comp3504inventorysystem;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.comp3504inventorysystem.databinding.ActivityListItemViewBinding;

import org.json.JSONObject;

import java.util.ArrayList;


public class ListItemActivity extends DrawerBaseActivity {

    private long backClickTime;
    ActivityListItemViewBinding activityListItemViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListItemViewBinding = ActivityListItemViewBinding.inflate(getLayoutInflater());
        setContentView(activityListItemViewBinding.getRoot());
        allocateActivityTitle("List Item");

        ArrayList<Item> matchedItems = new ArrayList<Item>();

        // Display list of all items or item/s that were found through search
        // Start activity with parameters (after having searched for an item): https://stackoverflow.com/a/3913735
        Bundle extraParams = getIntent().getExtras();
        if (extraParams != null) {
            String jsonStr = extraParams.getString("jsondata");
            matchedItems = api.getItemsListFromApiResponse(null, jsonStr);
        } else {
            JSONObject response = api.talkToApi("GET", "");
            if (this.api.apiResponseError(response)) {
                showPopup(activityListItemViewBinding.getRoot(), "error getting items", "error");
            } else {
                matchedItems = api.getItemsListFromApiResponse(response, null);
            }
        }

        // Display

//        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.listItem_output);
//
//        for (Item item : matchedItems) {
//            TextView textView = new TextView(this);
//            textView.setText(item.toString());
//            linearLayout.addView(textView);
//        }

        TableLayout linearLayout = findViewById(R.id.table_output);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,5,10,5);


        TableRow tbHeader = (TableRow)findViewById(R.id.table_header);
        tbHeader.setGravity(Gravity.CENTER);
        Typeface font = getResources().getFont(R.font.acme);


        for (Item item : matchedItems) {

            TableRow tbr = new TableRow(this);
            tbr.setGravity(Gravity.CENTER);
            TextView idView = new TextView(this);
            TextView nameView = new TextView(this);
            TextView quantityView = new TextView(this);
            TextView priceView = new TextView(this);
            TextView supplyIdView = new TextView(this);

            idView.setLayoutParams(params);
            nameView.setLayoutParams(params);
            quantityView.setLayoutParams(params);
            priceView.setLayoutParams(params);
            supplyIdView.setLayoutParams(params);

            idView.setGravity(Gravity.CENTER | Gravity.CENTER);
            nameView.setGravity(Gravity.CENTER | Gravity.CENTER);
            quantityView.setGravity(Gravity.CENTER | Gravity.CENTER);
            priceView.setGravity(Gravity.CENTER | Gravity.CENTER);
            supplyIdView.setGravity(Gravity.CENTER | Gravity.CENTER);

            idView.setTypeface(font);
            nameView.setTypeface(font);
            quantityView.setTypeface(font);
            priceView.setTypeface(font);
            supplyIdView.setTypeface(font);



            idView.setText(String.valueOf(item.getId()));
            nameView.setText(item.getName());
            quantityView.setText(String.valueOf(item.getQuantity()));
            priceView.setText(String.valueOf(item.getPrice()));
            supplyIdView.setText(String.valueOf(item.getSupplierId()));
            tbr.addView(idView);
            tbr.addView(nameView);
            tbr.addView(quantityView);
            tbr.addView(priceView);
            tbr.addView(supplyIdView);

            linearLayout.addView(tbr);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}