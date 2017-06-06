package arora.kushank.timetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class NotificationDB {

    private static final String DATABASE_NAME = "noti";
    private static final String DATABASE_TABLE = "noti_table";
    private static final int DATABASE_VERSION = 1;

    public static final String KEY_ID = "notiid";
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_SUBTITLE = "subtitle";
    public static final String KEY_DETAIL = "detail";
    public static final String KEY_OTHER = "other";
    public static final String KEY_TIME = "senttime";


    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ("
                    + KEY_ID + " VARCHAR(10), "
                    + KEY_TITLE + " VARCHAR(50), "
                    + KEY_BODY + " VARCHAR(100), "
                    + KEY_SUBTITLE + " VARCHAR(100), "
                    + KEY_DETAIL + " VARCHAR(100), "
                    + KEY_OTHER + " VARCHAR(100), "
                    + KEY_TIME + " VARCHAR(100)"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
    public NotificationDB(Context c) {
        ourContext = c;
    }

    public NotificationDB open() {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void delTable(){
        ourDatabase.delete(DATABASE_TABLE,null,null);
    }

    public void close() {
        ourHelper.close();
    }

    public long create(NotiDS noti) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, noti.ID);
        cv.put(KEY_TITLE, noti.title);
        cv.put(KEY_BODY, noti.body);
        cv.put(KEY_SUBTITLE, noti.subTitle);
        cv.put(KEY_DETAIL, noti.details);
        cv.put(KEY_OTHER, noti.other);
        cv.put(KEY_TIME, noti.sentTime);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }
    public void del(String id){
        ourDatabase.delete(DATABASE_TABLE, KEY_ID + "='" + id+"'", null);
    }

    public ArrayList<NotiDS> getNotifications() {
        ArrayList<NotiDS> list=new ArrayList<>();
        String[] columns = new String[]{KEY_ID,KEY_BODY,KEY_TITLE,KEY_SUBTITLE,KEY_DETAIL,KEY_OTHER,KEY_TIME};
        Cursor c = ourDatabase.query(true,DATABASE_TABLE, columns,null, null, null, null,null,null);

        int iID = c.getColumnIndex(KEY_ID);
        int iBody=c.getColumnIndex(KEY_BODY);
        int iTitle = c.getColumnIndex(KEY_TITLE);
        int iSubTitle=c.getColumnIndex(KEY_SUBTITLE);
        int iDetail = c.getColumnIndex(KEY_DETAIL);
        int iOther=c.getColumnIndex(KEY_OTHER);
        int iTime=c.getColumnIndex(KEY_TIME);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            NotiDS temp=new NotiDS();
            temp.ID =c.getString(iID);
            temp.body =c.getString(iBody);
            temp.title =c.getString(iTitle);
            temp.subTitle =c.getString(iSubTitle);
            temp.details =c.getString(iDetail);
            temp.other=c.getString(iOther);
            temp.sentTime= Long.parseLong(c.getString(iTime));

            list.add(temp);
        }
        c.close();

        ArrayList<NotiDS> listRes=new ArrayList<>();
        for(int i=list.size()-1;i>=0;i--)
            listRes.add(list.get(i));

        return listRes;
    }
    public NotiDS getNotification(String id) {
        String[] columns = new String[]{KEY_ID,KEY_BODY,KEY_TITLE,KEY_SUBTITLE,KEY_DETAIL,KEY_OTHER,KEY_TIME};
        Cursor c = ourDatabase.query(true,DATABASE_TABLE, columns,KEY_ID+"='"+id+"'",null , null, null,null,null);

        int iID = c.getColumnIndex(KEY_ID);
        int iBody=c.getColumnIndex(KEY_BODY);
        int iTitle = c.getColumnIndex(KEY_TITLE);
        int iSubTitle=c.getColumnIndex(KEY_SUBTITLE);
        int iDetail = c.getColumnIndex(KEY_DETAIL);
        int iOther=c.getColumnIndex(KEY_OTHER);
        int iTime=c.getColumnIndex(KEY_TIME);

        NotiDS gotAns=new NotiDS();

        c.moveToFirst();
        {
            gotAns.ID =c.getString(iID);
            gotAns.body =c.getString(iBody);
            gotAns.title =c.getString(iTitle);
            gotAns.subTitle =c.getString(iSubTitle);
            gotAns.details =c.getString(iDetail);
            gotAns.other=c.getString(iOther);
            gotAns.sentTime= Long.parseLong(c.getString(iTime));
        }
        c.close();

        return gotAns;
    }

}
