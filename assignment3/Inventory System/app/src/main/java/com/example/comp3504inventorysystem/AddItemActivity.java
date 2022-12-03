package com.example.comp3504inventorysystem;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.comp3504inventorysystem.databinding.ActivityAddItemViewBinding;

import org.json.JSONObject;

public class AddItemActivity extends DrawerBaseActivity {
    private long backClickTime;
    ActivityAddItemViewBinding activityAddItemViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();
        super.onCreate(savedInstanceState);
        activityAddItemViewBinding = ActivityAddItemViewBinding.inflate(getLayoutInflater());
        setContentView(activityAddItemViewBinding.getRoot());
        allocateActivityTitle("Add Item");

        Button buttonOne = (Button) findViewById(R.id.addItem_submit);
        buttonOne.setOnClickListener(new HandleSubmit(this));

        TextView id = (TextView) findViewById(R.id.addItem_textbox_itemID);
        id.setOnFocusChangeListener(new HandleFocus());

        TextView name = (TextView) findViewById(R.id.addItem_textbox_itemName);
        name.setOnFocusChangeListener(new HandleFocus());

        TextView quantity = (TextView) findViewById(R.id.addItem_textbox_quantity);
        quantity.setOnFocusChangeListener(new HandleFocus());

        TextView price = (TextView) findViewById(R.id.addItem_textbox_price);
        price.setOnFocusChangeListener(new HandleFocus());

        TextView supplierId = (TextView) findViewById(R.id.addItem_textbox_supplierID);
        supplierId.setOnFocusChangeListener(new HandleFocus());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class HandleFocus implements View.OnFocusChangeListener {
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                clearErrorOnField((TextView) view);
            }
        }
    }

    private class HandleSubmit implements View.OnClickListener {
        AddItemActivity parentClass;

        public HandleSubmit(AddItemActivity parentClass) {
            this.parentClass = parentClass;
        }

        public void onClick(View view) {
            TextView id = (TextView) findViewById(R.id.addItem_textbox_itemID);
            TextView name = (TextView) findViewById(R.id.addItem_textbox_itemName);
            TextView quantity = (TextView) findViewById(R.id.addItem_textbox_quantity);
            TextView price = (TextView) findViewById(R.id.addItem_textbox_price);
            TextView supplierID = (TextView) findViewById(R.id.addItem_textbox_supplierID);
            Item item = new Item();

            try {
                item.setIdStr(id.getText().toString());
            } catch (IllegalArgumentException e) {
                setErrorOnField(id);
                showPopup(activityAddItemViewBinding.getRoot(), "invalid id format", "error");
                return;
            }
            try {
                item.setName(name.getText().toString());
            } catch (IllegalArgumentException e) {
                setErrorOnField(name);
                showPopup(activityAddItemViewBinding.getRoot(), "invalid name format", "error");
                return;
            }
            try {
                item.setQuantityStr(quantity.getText().toString());
            } catch (IllegalArgumentException e) {
                setErrorOnField(quantity);
                showPopup(activityAddItemViewBinding.getRoot(), "invalid quantity format", "error");
                return;
            }
            try {
                item.setPriceStr(price.getText().toString());
            } catch (IllegalArgumentException e) {
                setErrorOnField(price);
                showPopup(activityAddItemViewBinding.getRoot(), "invalid price format", "error");
                return;
            }
            try {
                item.setSupplierIdStr(supplierID.getText().toString());
            } catch (IllegalArgumentException e) {
                setErrorOnField(supplierID);
                showPopup(activityAddItemViewBinding.getRoot(), "invalid supplier id format", "error");
                return;
            }

            JSONObject response = api.talkToApi("POST", item.toJsonString());
            if (api.apiResponseError(response)) {
                showPopup(activityAddItemViewBinding.getRoot(), api.apiResponseGetError(response), "error");
            } else {
                showPopup(activityAddItemViewBinding.getRoot(), "Item added!", "success");
            }
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
}