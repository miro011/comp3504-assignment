package com.example.comp3504inventorysystem;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.comp3504inventorysystem.databinding.ActivitySearchItemViewBinding;

import org.json.JSONObject;

import android.content.Intent;

public class SearchItemActivity extends DrawerBaseActivity {
    private long backClickTime;
    ActivitySearchItemViewBinding activitySearchItemViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchItemViewBinding = ActivitySearchItemViewBinding.inflate(getLayoutInflater());
        setContentView(activitySearchItemViewBinding.getRoot());
        allocateActivityTitle("Search Item");

        Button buttonOne = (Button) findViewById(R.id.searchItem_submit);
        buttonOne.setOnClickListener(btnClickHandler());

        TextView id = (TextView) findViewById(R.id.addItem_textbox_itemID);
        id.setOnFocusChangeListener(focusHandler());

        TextView name = (TextView) findViewById(R.id.addItem_textbox_itemName);
        name.setOnFocusChangeListener(focusHandler());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public View.OnFocusChangeListener focusHandler()
    {
        return new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    clearErrorOnField((TextView) view);
                }
            }
        };
    }

    public View.OnClickListener btnClickHandler()
    {
        return new View.OnClickListener() {
            public void onClick(View view) {
                EditText id = (EditText) findViewById(R.id.addItem_textbox_itemID);
                EditText name = (EditText) findViewById(R.id.addItem_textbox_itemName);
                Item item = new Item();

                if (!id.getText().toString().equals("") && !name.getText().toString().equals("")) {
                    showPopup(activitySearchItemViewBinding.getRoot(), "can only search for one thing", "error");
                } else if (!id.getText().toString().equals("")) {
                    try {
                        item.setIdStr(id.getText().toString());
                    } catch (IllegalArgumentException e) {
                        setErrorOnField(id);
                        showPopup(activitySearchItemViewBinding.getRoot(), "invalid id format", "error");
                        return;
                    }
                } else if (!name.getText().toString().equals("")) {
                    try {
                        item.setName(name.getText().toString());
                    } catch (IllegalArgumentException e) {
                        setErrorOnField(name);
                        showPopup(activitySearchItemViewBinding.getRoot(), "invalid name format", "error");
                        return;
                    }
                }

                JSONObject response = api.talkToApi("GET", item.toJsonString());
                if (api.apiResponseEmpty(response)) {
                    showPopup(activitySearchItemViewBinding.getRoot(), "no match found", "error");
                } else if (api.apiResponseError(response)) {
                    showPopup(activitySearchItemViewBinding.getRoot(), api.apiResponseGetError(response), "error");
                } else {
                    Intent i = new Intent(SearchItemActivity.this, ListItemActivity.class);
                    i.putExtra("jsondata", response.toString());
                    startActivity(i);
                }
                //Clear the form after submit
                id.setText("");
                name.setText("");
            }
        };
    }


    public void setErrorOnField(TextView field) {
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(4, ContextCompat.getColor(field.getContext(), R.color.invalid_field_red));
        gd.setCornerRadius(5);
        field.setBackground(gd);
        field.setSelected(true);
    }

    public void clearErrorOnField(TextView field) {
        GradientDrawable gd = new GradientDrawable();
        System.out.println("Clearing error highlight");
        field.setBackground(gd);
    }

    /*
    private class HandleSubmit implements View.OnClickListener {
        SearchItemActivity parentClass;

        public HandleSubmit(SearchItemActivity parentClass) {
            this.parentClass = parentClass;
        }

        public void onClick(View view) {
            TextView id = (TextView) findViewById(R.id.addItem_textbox_itemID);
            TextView name = (TextView) findViewById(R.id.addItem_textbox_itemName);
            Item item = new Item();

            try {
                item.setIdStr(id.getText().toString());
            } catch (IllegalArgumentException e) {
                setErrorOnField(id);
                //showPopup(parentClass.activitySearchItemViewBinding.getRoot(), "invalid id format", "error");
                return;
            }
        }

        public void setErrorOnField(TextView field) {
            GradientDrawable gd = new GradientDrawable();
            gd.setStroke(4, ContextCompat.getColor(field.getContext(), R.color.invalid_field_red));
            gd.setCornerRadius(5);
            field.setBackground(gd);
            field.setSelected(true);
        }

        public void clearErrorOnField(TextView field) {
            GradientDrawable gd = new GradientDrawable();
            System.out.println("Clearing error highlight");
            field.setBackground(gd);
        }
    }*/
}