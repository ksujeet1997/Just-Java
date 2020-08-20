package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * This method is called when the order button is clicked.
     */

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        boolean haswhippedCream = whippedCreamCheckBox.isChecked();
        int price = calculatePrice(haswhippedCream, hasChocolate);
        String message = createOrderSummary(name, price, haswhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param cream is for the whipped cream topping
     * @param chocolate is for the chocolate topping
     * @return the total price of the order
     */
    private int calculatePrice(boolean cream, boolean chocolate) {
        int basePrice = 5;

        //add 1 if user choose cream topping
        if (cream)
            basePrice += 1;

        //adds 2 if user choose chocolate topping
        if(chocolate)
            basePrice += 2;

        //calculates the total price of order and multiply it with quantity
        return quantity * basePrice;
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot order more than 100 Coffees", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot order less than 1 Coffees", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(getString(R.string.quantity_selector,numberOfCoffees));
    }

    /**
     * This method shows the summary of the order made
     *
     * @param price of the order
     * @param addWhippedCream Is whether or not customer want whipped cream topping
     * @param addChocolate is whether or not customer want chocolate topping
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name : " + name;
        priceMessage += "\nAdd Whipped Cream? : " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? : " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}


