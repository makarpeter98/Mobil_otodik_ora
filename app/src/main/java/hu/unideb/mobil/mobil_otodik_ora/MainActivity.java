package hu.unideb.mobil.mobil_otodik_ora;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
//
public class MainActivity extends AppCompatActivity {

    private static final String PREF_FILE = "shopping_list_pref";
    private static final String TEXTVIEW_DATA = "TEXTVIEW_DATA";

    TextView itemsTextView;
    Button addButton;
    Button deleteButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        itemsTextView = findViewById(R.id.itemsTextView);


        sharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE);


        if (savedInstanceState != null) {
            itemsTextView.setText(savedInstanceState.getString(TEXTVIEW_DATA));
        } else {

            String savedText = sharedPreferences.getString(TEXTVIEW_DATA, getString(R.string.no_items));
            itemsTextView.setText(savedText);
        }


        addButton.setOnClickListener(v -> activityResultLauncher.launch(new Intent(this, ItemsActivity.class)));

        deleteButton.setOnClickListener(v -> itemsTextView.setText(getString(R.string.no_items)));
    }

    // ActivityResultLauncher az ItemsActivity visszaadott adatának kezelésére
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult reply) {
                    Log.d("MainActivity", "activityResultLauncher callback");
                    if (reply.getResultCode() == RESULT_OK) {
                        if (itemsTextView.getText().toString().equals(getString(R.string.no_items)))
                            itemsTextView.setText("");
                        String newItem = reply.getData().getStringExtra("ITEM");
                        if (newItem != null) {
                            itemsTextView.append(newItem + "\n");
                        }
                    }
                }
            }
    );

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXTVIEW_DATA, itemsTextView.getText().toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXTVIEW_DATA, itemsTextView.getText().toString());
        editor.apply();
    }
}