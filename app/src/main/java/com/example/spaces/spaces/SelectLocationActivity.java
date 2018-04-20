package com.example.spaces.spaces;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Owen on 4/19/18.
 */

public class SelectLocationActivity extends BaseActivity {

    private EditText nameEntry;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location);

        nameEntry = findViewById(R.id.field_select_location);
        nextButton = findViewById(R.id.button_next);

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameEntry.getText().toString();
                Intent i = new Intent(v.getContext(), ReviewActivity.class);
                // open the add review page for the location specified
                v.getContext().startActivity(
                        i.putExtra("name", name)
                );
            }
        });
    }

}
