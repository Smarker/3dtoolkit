package microsoft.a3dtoolkitandroid;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Connect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    /**
     * Connects to remote server with provided info
     * @param view
     */
    public void connect(View view) {
        Intent intent = new Intent(this, Stream.class);
        String server = ((EditText) findViewById(R.id.server)).getText().toString().toLowerCase();
        String port = ((EditText) findViewById(R.id.port)).getText().toString().toLowerCase();
        if (server.length() == 0 || port.length() == 0) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Missing Info")
                    .setMessage(server.length() == 0 ? "Please fill server" : "Please fill port");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            startActivity(intent);
        }
    }
}
