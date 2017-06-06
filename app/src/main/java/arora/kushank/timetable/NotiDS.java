package arora.kushank.timetable;

public class NotiDS {
    String ID;
    String title;
    String body;
    String subTitle;
    String details;
    String other;
    long sentTime;
    NotiDS(){
        ID ="";
        title ="";
        body ="";
        subTitle ="";
        details ="";
        other="";
        sentTime=0;
    }
    NotiDS(int id){
        ID =id+"";
        title ="";
        body ="Noti"+ ID;
        subTitle ="";
        details ="";
        other="";
        sentTime=0;
    }
}
