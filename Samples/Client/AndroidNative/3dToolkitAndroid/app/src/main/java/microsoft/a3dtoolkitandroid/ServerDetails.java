package microsoft.a3dtoolkitandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.TextView;

public class ServerDetails extends AppCompatActivity {

    private GestureDetectorCompat mDetector;
    private ScaleGestureDetector mScaleDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_details);

        mDetector = new GestureDetectorCompat(this, new GestureListener());
        mScaleDetector = new ScaleGestureDetector(this, new ScaleGestureListener());

        Intent intent = getIntent();
        String serverName = intent.getStringExtra(ServerList.SERVER_NAME);

        TextView textView = (TextView) findViewById(R.id.serverNameText);
        textView.setText(serverName);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        this.mScaleDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
