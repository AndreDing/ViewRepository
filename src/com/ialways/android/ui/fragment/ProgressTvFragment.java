package com.ialways.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ialways.android.R;
import com.ialways.android.ui.view.ProgressTextView;

/**
 * 用于显示反色TextView的Fragment
 * @author dingchao
 * @email gmdingchao@gmail.com
 */
public class ProgressTvFragment extends Fragment {

    private static Context mCtx;
    private static final int MSG_SET_PROGRESS = 0;
    private ProgressTextView mProgressTv;
    private float progress = 0.01f;
    private float step = 0.01f;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_SET_PROGRESS:
                System.out.println("set progress");
                progress += step;
                mProgressTv.setProgress(progress);
                mHandler.sendEmptyMessageDelayed(MSG_SET_PROGRESS, 500);
                break;
            }
        }
    };
    
    public static ProgressTvFragment getInstance(Context context, Bundle bundle) {
        mCtx = context;
        ProgressTvFragment progressTvFragment = new ProgressTvFragment();
        progressTvFragment.setArguments(bundle);
        return progressTvFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_progress_tv, null);
        mProgressTv = (ProgressTextView) view.findViewById(R.id.down_progress);
        return view;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        progress = 0;
        mHandler.sendEmptyMessageDelayed(MSG_SET_PROGRESS, 500);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        mHandler.removeMessages(MSG_SET_PROGRESS);
    }
}
