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

    private static final int ADD_BOOK_REQUEST_CODE = 1;
    private SQLiteDatabase db;
    private TableLayout bookTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addNewBookButton = findViewById(R.id.addButton);
        addNewBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivityForResult(intent, ADD_BOOK_REQUEST_CODE);
            }
        });

        bookTable = findViewById(R.id.bookTable);

        // Create or open the database
        db = openOrCreateDatabase("my_books.db", Context.MODE_PRIVATE, null);

        // Create the books table if it doesn't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, author TEXT, quantity INTEGER, price REAL)");

        // Add the table header
        addTableHeader();

        // Update the table
        updateBookTable();
    }

    private void addTableHeader() {
        TableRow headerRow = new TableRow(this);

        TextView idHeaderTextView = new TextView(this);
        idHeaderTextView.setText("id");
        headerRow.addView(idHeaderTextView);

        TextView nameHeaderTextView = new TextView(this);
        nameHeaderTextView.setText("book_name");
        headerRow.addView(nameHeaderTextView);

        TextView authorHeaderTextView = new TextView(this);
        authorHeaderTextView.setText("author_name");
        headerRow.addView(authorHeaderTextView);

        TextView quantityHeaderTextView = new TextView(this);
        quantityHeaderTextView.setText("quantity");
        headerRow.addView(quantityHeaderTextView);

        TextView priceHeaderTextView = new TextView(this);
        priceHeaderTextView.setText("price");
        headerRow.addView(priceHeaderTextView);

        bookTable.addView(headerRow);
    }

    private void updateBookTable() {
        // Remove all rows except the header
        int rowCount = bookTable.getChildCount();
        for (int i = rowCount - 1; i >= 1; i--) {
            bookTable.removeViewAt(i);
        }

        // Query the database for all books
        Cursor cursor = db.rawQuery("SELECT * FROM books", null);

        // Add each book to the table row by row
        while (cursor.moveToNext()) {
            TableRow row = new TableRow(this);

            TextView idTextView = new TextView(this);
            idTextView.setText(Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("id"))));
            row.addView(idTextView);

            TextView nameTextView = new TextView(this);
            nameTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow("book_name")));
            row.addView(nameTextView);

            TextView authorTextView = new TextView(this);
            authorTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow("author_name")));
            row.addView(authorTextView);

            TextView quantityTextView = new TextView(this);
            quantityTextView.setText(Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("quantity"))));
            row.addView(quantityTextView);

            TextView priceTextView = new TextView(this);
            priceTextView.setText(Float.toString(cursor.getFloat(cursor.getColumnIndexOrThrow("price"))));
            row.addView(priceTextView);

            bookTable.addView(row);
        }

        // Close the cursor
        cursor.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {
            updateBookTable();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}

