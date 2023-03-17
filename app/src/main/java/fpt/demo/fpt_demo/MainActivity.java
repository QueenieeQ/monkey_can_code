package fpt.demo.fpt_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    private TableLayout mTable;
    private Button mNewFoodButton;
    private MyAdapter MyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTable = findViewById(R.id.table);
        mNewFoodButton = findViewById(R.id.new_food_button);

        // Retrieve the data from the database
        Cursor cursor = getDataFromDatabase();

        // Sort the data in descending order of price
        cursor = sortDataByPrice(cursor, false);

        // Create a cursor adapter to display the data in the table
        MyCursorAdapter adapter = new MyCursorAdapter(this, cursor);
        adapter.bindView(mTable);
    }

    private Cursor getDataFromDatabase() {
        // TODO: Implement the database query to retrieve the data
        return null;
    }

    private Cursor sortDataByPrice(Cursor cursor, boolean ascending) {
        // TODO: Implement the database query to sort the data by price
        return cursor;
    }

}


