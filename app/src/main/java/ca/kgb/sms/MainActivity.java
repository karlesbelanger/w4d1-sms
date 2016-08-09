package ca.kgb.sms;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String number = "6789084843";
    private String message = "Hello this is your app talking, keep working. Great work :)";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Uri sms_uri = Uri.parse("smsto:+6789084843");
//        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
//        sms_intent.putExtra("sms_body", "Good Morning ! how r U ?");
//        startActivity(sms_intent);

        try {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("smsto:" + Uri.encode(number)));
            smsIntent.putExtra("address", number);
            smsIntent.putExtra("sms_body", message);

            PackageManager pm = getPackageManager();
            List<ResolveInfo> resInfo = pm.queryIntentActivities(smsIntent, 0);

            for (int i = 0; i < resInfo.size(); i++) {
                ResolveInfo ri = resInfo.get(i);
                String packageName = ri.activityInfo.packageName;

                if (packageName.contains("sms")) {
                    //Log.d("TAG", packageName + " : " + ri.activityInfo.name);
                    smsIntent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                }
            }
            startActivity(smsIntent);
        } catch (Exception e) {
            // Handle Error
        }
    }
}
