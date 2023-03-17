package fpt.demo.fpt_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addNewBookButton = findViewById(R.id.addButton);
        addNewBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        // Get a reference to the book table
        TableLayout bookTable = findViewById(R.id.bookTable);

        // Create or open the database
        db = openOrCreateDatabase("BooksDB", Context.MODE_PRIVATE, null);

        // Create the books table if it doesn't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, author TEXT, quantity INTEGER, price REAL)");

        // Query the database for all books
        Cursor cursor = db.rawQuery("SELECT * FROM books", null);

        // Add each book to the table row by row
        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);

            TextView idTextView = new TextView(this);
            idTextView.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("id"))));
            row.addView(idTextView);

            TextView nameTextView = new TextView(this);
            nameTextView.setText(cursor.getString(cursor.getColumnIndex("name")));
            row.addView(nameTextView);

            TextView authorTextView = new TextView(this);
            authorTextView.setText(cursor.getString(cursor.getColumnIndex("author")));
            row.addView(authorTextView);

            TextView quantityTextView = new TextView(this);
            quantityTextView.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("quantity"))));
            row.addView(quantityTextView);

            TextView priceTextView = new TextView(this);
            priceTextView.setText(Float.toString(cursor.getFloat(cursor.getColumnIndex("price"))));
            row.addView(priceTextView);

            bookTable.addView(row);
        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }
}
