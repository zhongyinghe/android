package test.zyh.com.backupcontacts;
import android.content.Context;
import android.os.Environment;
import android.util.Xml;
import android.widget.Toast;
import org.xmlpull.v1.XmlSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
/**
 * Created by Zhong on 2016/10/4.
 */
public class SavePersonsInfo {
    public static boolean savePersonsInfo(Context context, List<PersonInfo> persons){
        if(persons.size() > 0){
            try {
                File file = new File(Environment.getExternalStorageDirectory(),"persons.xml");
                FileOutputStream fos = new FileOutputStream(file);
                XmlSerializer serializer = Xml.newSerializer();
                serializer.setOutput(fos, "utf-8");
                serializer.startDocument("utf-8", true);
                serializer.startTag(null, "persons");
                for(PersonInfo person : persons){
                    serializer.startTag(null, "person");

                    serializer.startTag(null, "name");
                    serializer.text(person.getName());
                    serializer.endTag(null, "name");

                    serializer.startTag(null, "phoneNum");
                    serializer.text(person.getPhoneNum());
                    serializer.endTag(null, "phoneNum");

                    serializer.endTag(null, "person");
                }
                serializer.endTag(null, "persons");
                serializer.endDocument();
                fos.close();
                Toast.makeText(context, "保存xml成功", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(context, "保存xml失败", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
