package arora.kushank.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Password on 14-Mar-17.
 */
public class DailogBoxForNoti extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailognoti);
        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        String id=b.getString("ID");
        NotificationDB db=new NotificationDB(this);
        db.open();
        NotiDS noti=db.getNotification(id);
        db.close();

        Date date=new Date();
        date.setTime(noti.sentTime);
        DateFormat d=DateFormat.getDateTimeInstance();

        String sentTimeS=d.format(date);

        LinearLayout first= (LinearLayout) findViewById(R.id.firstll);

        LinearLayout.LayoutParams l=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        String options[]=new String[]{"ID","Title","SubTitle","Body","Detail","SentTime","Other"};
        String res[]    =new String[]{noti.ID,noti.title,noti.subTitle,noti.body,noti.details,sentTimeS,noti.other};
        if(first != null){
            for(int i=0;i<options.length;i++) {

                LinearLayout temp = new LinearLayout(this);
                temp.setOrientation(LinearLayout.HORIZONTAL);
                temp.setLayoutParams(l);
                temp.setPadding(10,10,10,10);
                TextView tv=new TextView(this);
                tv.setText(options[i]+": "+res[i]);
                temp.addView(tv);
                first.addView(temp);
            }
        }
    }
}
