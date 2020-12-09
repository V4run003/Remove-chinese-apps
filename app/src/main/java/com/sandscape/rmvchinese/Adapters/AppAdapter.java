package com.sandscape.rmvchinese.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sandscape.rmvchinese.Models.Apps;
import com.sandscape.rmvchinese.R;

import java.util.ArrayList;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Apps> mApps;

    public void setApps(ArrayList<Apps> mApps) {
        this.mApps = mApps;
    }

    public AppAdapter(Context mContext, ArrayList<Apps> mApps) {
        this.mContext = mContext;
        this.mApps = mApps;
    }

    @NonNull
    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_app_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AppAdapter.ViewHolder holder, final int position) {
        final Apps app = mApps.get(position);
        holder.appName.setText(app.getName());
        holder.packageName.setText(app.getPackageName());
        holder.appIcon.setImageDrawable(app.getIcon());
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.feature_item_animation));
        holder.container.setVisibility(View.VISIBLE);
        holder.appSize.setText(app.getAppSize());

        holder.uninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_DELETE)
                        .setData(Uri.parse("package:" + app.getPackageName())));
            }
        });

        holder.playStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("market://details?id=" + app.getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    goToMarket.addFlags(
                            Intent.FLAG_ACTIVITY_NO_HISTORY |
                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    mContext.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + app.getPackageName())));
                }
            }
        });

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(position);
                if (holder.expand.getVisibility() == View.VISIBLE) {
                    holder.expand.setVisibility(View.GONE);
                    //holder.expand.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.collapse));
                } else {
                    holder.expand.setVisibility(View.VISIBLE);
                    //holder.expand.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.expand));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView appName, appSize, packageName;
        private ImageView appIcon;
        private LinearLayout expand;
        private CardView container;
        private Button uninstall, playStore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.app_name);
            packageName = itemView.findViewById(R.id.package_name);
            appIcon = itemView.findViewById(R.id.icon);
            expand = itemView.findViewById(R.id.expand);
            container = itemView.findViewById(R.id.card_view);
            appSize = itemView.findViewById(R.id.app_size);
            uninstall = itemView.findViewById(R.id.uninstall);
            playStore = itemView.findViewById(R.id.play_store);
            expand.setVisibility(View.GONE);
            container.setVisibility(View.GONE);

        }
    }
}
