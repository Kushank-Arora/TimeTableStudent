package arora.kushank.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DisplayNoti extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvNoti;
    private ArrayList<NotiDS> noti;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaynoti);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        lvNoti=(ListView)findViewById(R.id.listNoti);

        lvNoti.setOnItemClickListener(this);

        registerForContextMenu(lvNoti);
        lvNoti.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.context_menu,menu);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this,"OnResume",Toast.LENGTH_SHORT).show();
        NotificationDB db=new NotificationDB(this);
        db.open();
        noti=db.getNotifications();
        db.close();

        setListAdapter();
    }

    private void setListAdapter() {
        String items[]=new String[noti.size()];
        for(int i=0;i<noti.size();i++){
            Date d=new Date();
            d.setTime(noti.get(i).sentTime);
            DateFormat df=DateFormat.getTimeInstance();
            String sentTimeHrs=df.format(d);
            items[i]= sentTimeHrs+" |  "+noti.get(i).body;
        }
        lvNoti.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex=item.getItemId();
        if(menuItemIndex==R.id.action_delNoti){

            NotificationDB db=new NotificationDB(this);
            db.open();
            db.del(noti.get(info.position).ID);
            db.close();

            noti.remove(info.position);
            setListAdapter();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i=new Intent(this,DailogBoxForNoti.class);
        Bundle b=new Bundle();
        b.putString("ID",noti.get(position).ID);
        i.putExtras(b);
        startActivity(i);
    }
}
