package hu.unideb.mobil.mobil_otodik_ora;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ItemsActivity extends AppCompatActivity {

    public static String ITEM_KEY = "ITEM";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
    }

    public void addItem(View view)
    {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(ITEM_KEY, ((Button)view).getText());
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
