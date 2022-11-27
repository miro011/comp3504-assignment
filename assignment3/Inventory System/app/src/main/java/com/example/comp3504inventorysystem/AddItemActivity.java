package com.example.comp3504inventorysystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import com.example.comp3504inventorysystem.databinding.ActivityAddItemViewBinding;

public class AddItemActivity extends DrawerBaseActivity {
    private long backClickTime;
    ActivityAddItemViewBinding activityAddItemViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddItemViewBinding = ActivityAddItemViewBinding.inflate(getLayoutInflater());
        setContentView(activityAddItemViewBinding.getRoot());
        allocateActivityTitle("Add Item");

        Button buttonOne = (Button) findViewById(R.id.addItem_submit);
        buttonOne.setOnClickListener(new HandleSubmit(this));

        TextView id = (TextView) findViewById(R.id.addItem_textbox_itemID);
        id.setOnClickListener(new HandleFocus());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class HandleFocus implements View.OnClickListener {
        public void onClick(View view) {
            if (view.getId() == R.id.addItem_textbox_itemID) {
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
            // Button btn = (Button)view;
            TextView id = (TextView) findViewById(R.id.addItem_textbox_itemID);
            TextView name = (TextView) findViewById(R.id.addItem_textbox_itemName);
            TextView quantity = (TextView) findViewById(R.id.addItem_textbox_quantity);
            TextView price = (TextView) findViewById(R.id.addItem_textbox_price);
            TextView supplierID = (TextView) findViewById(R.id.addItem_textbox_supplierID);
            // update the TextView text
            Item item = new Item();

            try {
                item.setIdStr(id.getText().toString());
            } catch (IllegalArgumentException e) {
                setErrorOnField(id);
            }

            parentClass.api.addItem(item);
        }
    }

    public void setErrorOnField(TextView field) {
        field.setBackgroundColor(ContextCompat.getColor(field.getContext(), R.color.invalid_field_red));
        field.setSelected(true);
    }

    public void clearErrorOnField(TextView field) {
        field.setBackgroundResource(android.R.drawable.btn_default);
    }
}