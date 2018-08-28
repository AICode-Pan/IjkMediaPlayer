package com.custom.videoview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.media.ijkmediaplayer.IjkVideoView;

/**
 * <pre>
 *     author : panbeixing
 *     time : 2018/8/23
 *     desc :
 *     version : 1.0
 * </pre>
 */

public class MainActivity extends Activity {
    private String TAG = "MainActivity";
    private IjkVideoView ijkVideoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ijkVideoView = new IjkVideoView(this);
        ijkVideoView.setVideoPath("http://liveng.alicdn.com/mediaplatform/48017889-38a4-4f09-8ae2-d42b379de2ae.m3u8?auth_key=1537781028-0-0-0569b06bcf53fadf50afd4be2e0f85a7");


        setContentView(ijkVideoView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ijkVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkVideoView.stop();
        ijkVideoView.release();
    }
}
