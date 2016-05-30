package com.github.chenglei1986.navigationbar.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends Activity {

    @BindView(R.id.listView)
    ListView mListView;

    private IntentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupListView();
    }

    private void setupListView() {
        List<ResolveInfo> infos = getAllResolveInfos(this, "com.github.chenglei1986.navigationbar.SAMPLE");
        mAdapter = new IntentAdapter(infos);
        mListView.setAdapter(mAdapter);
    }

    private class IntentAdapter extends BaseAdapter {

        private List<ResolveInfo> mResolveInfos;

        public IntentAdapter(List<ResolveInfo> resolveInfos) {
            mResolveInfos = resolveInfos;
        }

        public List<ResolveInfo> getResolveInfos() {
            return mResolveInfos;
        }

        @Override
        public int getCount() {
            return mResolveInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return mResolveInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (null == convertView) {
                convertView = getLayoutInflater().inflate(R.layout.activity_list_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ResolveInfo resolveInfo = mResolveInfos.get(position % getCount());
            holder.name.setText(resolveInfo.loadLabel(getPackageManager()));
            return convertView;
        }
    }

    static class ViewHolder {
        @BindView(R.id.acticity_name)
        TextView name;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

    @OnItemClick(R.id.listView)
    void onItemClick(int position) {
        ResolveInfo resolveInfo = mAdapter.getResolveInfos().get(position % mAdapter.getCount());
        Intent intent = new Intent();
        intent.setClassName(this, resolveInfo.activityInfo.name);
        String title = resolveInfo.loadLabel(getPackageManager()).toString();
        intent.putExtra("title", title);
        startActivity(intent);
    }

    public static List<ResolveInfo> getAllResolveInfos(Context context, String category) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(category);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        return infos;
    }

}
