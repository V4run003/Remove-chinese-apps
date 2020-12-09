package com.sandscape.rmvchinese.Fragments;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sandscape.rmvchinese.Adapters.AppAdapter;
import com.sandscape.rmvchinese.Models.Apps;
import com.sandscape.rmvchinese.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sandscape.rmvchinese.Utils.Utils.getFileSize;

public class AllApps extends Fragment {

    private Context mContext;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_china_apps, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        refreshLayout = view.findViewById(R.id.refresh);
        setRecyclerView();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                setRecyclerView();
            }
        });
        return view;
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        AppAdapter appAdapter = new AppAdapter(mContext, getInstalledApps());
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(appAdapter);
    }

    private ArrayList<Apps> getInstalledApps() {
        PackageManager packageManager = mContext.getPackageManager();
        List<PackageInfo> packList = packageManager.getInstalledPackages(0);
        ArrayList<Apps> apps = new ArrayList<>();
        for (int i = 0; i < packList.size(); i++) {
            PackageInfo packInfo = packList.get(i);
            if ((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = packInfo.applicationInfo.loadLabel(packageManager).toString();
                String packageName = packInfo.packageName;
                Drawable appIcon = packInfo.applicationInfo.loadIcon(packageManager);
                long appSize = new File(packInfo.applicationInfo.publicSourceDir).length();
                apps.add(new Apps(appName, packageName, appIcon, getFileSize(appSize)));
            }
        }
        refreshLayout.setRefreshing(false);
        return apps;
    }
}