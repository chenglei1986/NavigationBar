package com.github.chenglei1986.navigationbar.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabFragment extends Fragment {

    private Context mContext;
    private View mContentView;
    @BindView(R.id.position)
    TextView mTextView;

    private static final String ARGS_POS = "args_pos";
    private String mArgs;

    public TabFragment() {
        // Required empty public constructor
    }

    public static TabFragment newInstance(String args) {
        TabFragment fragment = new TabFragment();
        Bundle b = new Bundle();
        b.putString(ARGS_POS, args);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = getArguments().getString(ARGS_POS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(R.layout.fragment_tab, container, false);
            ButterKnife.bind(this, mContentView);
        }
        mTextView.setText(mArgs);
        return mContentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

}
