package com.sandscape.rmvchinese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        installedApps();


    }

    public void installedApps(){
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packList.size();i++){
            PackageInfo packInfo = packList.get(i);
            if ((packInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM) == 0){
                String appname = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                String packagename = packInfo.packageName;
                // Drawable appicon = packInfo.applicationInfo.loadIcon(getPackageManager());
                Log.e("desc"+ i,packagename);
                Log.e("app name"+ i,appname);
            }
        }
    }

}
