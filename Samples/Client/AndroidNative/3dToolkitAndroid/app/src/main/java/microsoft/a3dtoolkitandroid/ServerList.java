package microsoft.a3dtoolkitandroid;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.id.list;

public class ServerList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list);
        final ListView listview = (ListView) findViewById(R.id.ServerListView);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final Intent nextIntent = new Intent(this, Stream.class);

        List<String> serverListString = new ArrayList<String>(Arrays.asList(intent.getStringExtra(Connect.SERVER_LIST).split("\n")));
        String myID = serverListString.remove(0);

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, serverListString);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                //todo: do action with chosen item
                startActivity(nextIntent);

            }
        });

    }
}
