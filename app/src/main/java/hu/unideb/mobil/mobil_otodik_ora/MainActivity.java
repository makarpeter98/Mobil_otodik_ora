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

public class MainActivity extends AppCompatActivity {
    // teszt
    private static final String PREF_FILE = "shopping_list_pref";
    private static final String TEXTVIEW_DATA = "TEXTVIEW_DATA";

    TextView itemsTextView;
    Button addButton;
    Button deleteButton;

    SharedPreferences sharedPreferences;

    ActivityResultLauncher activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult reply) {
                    Log.d("teszt", "activityResultLauncher");
                    if(reply.getResultCode() == RESULT_OK)
                    {
                        if(itemsTextView.getText().toString().equals(getString(R.string.no_items)))
                            itemsTextView.setText("");
                        itemsTextView.append(reply.getData().getStringExtra("ITEM") + "\n");
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        itemsTextView = findViewById(R.id.itemsTextView);

        addButton.setOnClickListener( v -> activityResultLauncher.launch(new Intent(this, ItemsActivity.class)) );
        deleteButton.setOnClickListener(v -> {itemsTextView.setText(getString(R.string.no_items));});
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}