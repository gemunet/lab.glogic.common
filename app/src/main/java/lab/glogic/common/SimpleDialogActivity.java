package lab.glogic.common;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import lab.glogic.common.simpledialog.LoadingDialogFragment;
import lab.glogic.common.simpledialog.MessageDialogFragment;

public class SimpleDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_dialog);

        Button btnLoading = findViewById(R.id.btnLoading);
        btnLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialogFragment.show(getSupportFragmentManager());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingDialogFragment.hide(getSupportFragmentManager());
                    }
                }, 3000);
            }
        });

        Button btnAlert = findViewById(R.id.btnAlert);
        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageDialogFragment.Alert("Hi world!").show(getSupportFragmentManager());
            }
        });

        Button btnSuccess = findViewById(R.id.btnSuccess);
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageDialogFragment.Success("Hi world!").setTitle("My title").show(getSupportFragmentManager());
            }
        });

        Button btnInfo = findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageDialogFragment.Info("Hi world!").show(getSupportFragmentManager());
            }
        });

        Button btnWarning = findViewById(R.id.btnWarning);
        btnWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageDialogFragment.Warning("Hi world!").show(getSupportFragmentManager());
            }
        });

        Button btnError = findViewById(R.id.btnError);
        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageDialogFragment.Error("Hi world!").show(getSupportFragmentManager());
            }
        });

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageDialogFragment.Confirm("Hi world!")
                        .setPositiveListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(SimpleDialogActivity.this, "Positive", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show(getSupportFragmentManager());
            }
        });

        Button btnErrorRetry = findViewById(R.id.btnErrorRetry);
        btnErrorRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageDialogFragment.ErrorRetry("Hi world!")
                        .setPositiveListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(SimpleDialogActivity.this, "Retry", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(SimpleDialogActivity.this, "Cancel", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show(getSupportFragmentManager());

            }
        });

    }
}
