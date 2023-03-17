package fpt.demo.fpt_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class AddBookActivity extends AppCompatActivity {

    private EditText bookNameInput;
    private EditText authorNameInput;
    private EditText priceInput;
    private EditText quantityInput;
    private EditText descriptionInput;

    private Button saveButton;
    private Button backButton;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        bookNameInput = findViewById(R.id.book_name_input);
        authorNameInput = findViewById(R.id.author_name_input);
        priceInput = findViewById(R.id.price_input);
        quantityInput = findViewById(R.id.quantity_input);
        descriptionInput = findViewById(R.id.description_input);

        saveButton = findViewById(R.id.save_button);
        backButton = findViewById(R.id.back_button);

        // Create or open the database
        db = openOrCreateDatabase("my_books.db", Context.MODE_PRIVATE, null);

        // Create the books table if it doesn't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_name TEXT, author_name TEXT, price REAL, quantity INTEGER, description TEXT)");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values
                String bookName = bookNameInput.getText().toString().trim();
                String authorName = authorNameInput.getText().toString().trim();
                String priceString = priceInput.getText().toString().trim();
                String quantityString = quantityInput.getText().toString().trim();
                String description = descriptionInput.getText().toString().trim();

                // Validate input values
                if (bookName.isEmpty() || authorName.isEmpty() || priceString.isEmpty() || quantityString.isEmpty()) {
                    Toast.makeText(AddBookActivity.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                double price;
                int quantity;

                try {
                    price = Double.parseDouble(priceString);
                    quantity = Integer.parseInt(quantityString);
                } catch (NumberFormatException e) {
                    Toast.makeText(AddBookActivity.this, "Please enter a valid price and quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert the new book into the database
                ContentValues values = new ContentValues();
                values.put("book_name", bookName);
                values.put("author_name", authorName);
                values.put("price", price);
                values.put("quantity", quantity);
                values.put("description", description);

                long result = db.insert("books", null, values);

                if (result == -1) {
                    Toast.makeText(AddBookActivity.this, "Error saving book to database", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddBookActivity.this, "Book saved successfully", Toast.LENGTH_SHORT).show();
                    // Clear the input fields
                    bookNameInput.setText("");
                    authorNameInput.setText("");
                    priceInput.setText("");
                    quantityInput.setText("");
                    descriptionInput.setText("");
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish this activity and return to the previous one
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database when this activity is destroyed
        db.close();
    }

}
// done a class
