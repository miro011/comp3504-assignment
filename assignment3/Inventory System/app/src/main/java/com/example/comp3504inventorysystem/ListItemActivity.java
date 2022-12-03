package com.example.comp3504inventorysystem;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout;
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

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.listItem_output);

        for (Item item : matchedItems) {
            TextView textView = new TextView(this);
            textView.setText(item.toString());
            linearLayout.addView(textView);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}