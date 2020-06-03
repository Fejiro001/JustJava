/*
IMPORTANT: Make sure you are using the correct package name.
* This example uses the package name:
* package com.example.android.justjava
* If you get an error when copying this code into Android studio, update it to match teh package name found
* in the project's AndroidManifest.xml file.
*/

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

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Show an error message
            Toast.makeText(this, getString(R.string.increment_toast_message), Toast.LENGTH_SHORT).show();
            //Exit method
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //Show an error message
            Toast.makeText(this, getString(R.string.decrement_toast_message), Toast.LENGTH_SHORT).show();
            //Exit method
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get the customers name
        EditText customersName = findViewById(R.id.customer_name);
        String name = customersName.getText().toString();

        // Find out if the customer wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Find out if the customer wants chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Display the order summary on the screen
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        // Sends the order summary to the email app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name) );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param whippedCreamPrice if the customer wants whipped cream topping.
     * @param chocolatePrice if the customer wants chocolate topping.
     * @return total price.
     */
    private int calculatePrice(boolean whippedCreamPrice, boolean chocolatePrice) {
        // Price for one cup of coffee
        int basePrice = 5;

        // Price for one cup of coffee with whipped cream topping
        if (whippedCreamPrice) {
            basePrice += 1;
        }

        // Price for one cup of coffee with chocolate topping
        if (chocolatePrice) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * Creates summary of the order.
     *
     * @param price of the order.
     * @param addWhippedCream checks if you want whipped cream or not on the coffee
     * @param addChocolate checks if you want chocolate or not on the coffee
     * @param nameOfCustomer is the name of the customer ordering coffee
     * @return text summary.
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String nameOfCustomer){
        String priceMessage = " " + getString(R.string.order_summary_name, nameOfCustomer);
        priceMessage += "\n" + getString(R.string.add_whipped_cream_topping, addWhippedCream);
        priceMessage += "\n" + getString(R.string.add_chocolate_topping, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int price) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + price);
    }

}