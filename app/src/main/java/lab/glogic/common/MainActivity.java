package lab.glogic.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import lab.glogic.common.wizard.WizardDemoActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SIMPLESLIDER_REQUEST = 1;
    private static final int WIZARD_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSimpleDialog = findViewById(R.id.btnSimpleDialog);
        btnSimpleDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSimpleDialog();
            }
        });

        Button btnSimpleSlider = findViewById(R.id.btnSimpleSlider);
        btnSimpleSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSimpleSlider();
            }
        });

        Button btnWizard = findViewById(R.id.btnWizard);
        btnWizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWizard();
            }
        });
    }

    private void openSimpleDialog() {
        Intent intent = new Intent(this, SimpleDialogActivity.class);
        startActivity(intent);
    }

    private void openSimpleSlider() {
        Intent intent = new Intent(this, SimpleSliderDemoActivity.class);
        startActivityForResult(intent, SIMPLESLIDER_REQUEST);
    }

    private void openWizard() {
        Intent intent = new Intent(this, WizardDemoActivity.class);
        startActivityForResult(intent, WIZARD_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == SIMPLESLIDER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.canceled, Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode == WIZARD_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.canceled, Toast.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
