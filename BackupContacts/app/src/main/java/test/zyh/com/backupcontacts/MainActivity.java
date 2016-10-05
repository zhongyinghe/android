package test.zyh.com.backupcontacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void backup(View v){
        //Toast.makeText(this, "我是显示器", Toast.LENGTH_SHORT).show();
        readContact();
    }

    public void readContact(){
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri data_uri = Uri.parse("content://com.android.contacts/data");

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);

       /* String[] names = cursor.getColumnNames();
        for(String name : names){
            System.out.println(name);
        }*/
        List<PersonInfo> persons = new ArrayList<PersonInfo>();
        while(cursor.moveToNext()){
            String contact_id = cursor.getString(cursor.getColumnIndex("contact_id"));
            if(contact_id != null){
                String name = "";
                String phoneNum = "";
                Cursor datacursor = resolver.query(data_uri, null, "raw_contact_id=?",
                        new String[]{contact_id}, null);
                while(datacursor.moveToNext()){
                    String data1 = datacursor.getString(datacursor.getColumnIndex("data1"));
                    String mimetype = datacursor.getString(datacursor.getColumnIndex("mimetype"));
                    //System.out.println(data1 + "-----" + mimetype);
                    if(mimetype.equals("vnd.android.cursor.item/name")){
                        name = data1;
                    }else if(mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        phoneNum = data1;
                    }
                }

                if(name != null && phoneNum != null){
                    PersonInfo person = new PersonInfo(name,phoneNum);
                    persons.add(person);
                }
                datacursor.close();
            }
        }
        cursor.close();
        SavePersonsInfo.savePersonsInfo(this,persons);
        /*for(PersonInfo p : persons){
            System.out.println(p.toString());
        }*/

    }
}
