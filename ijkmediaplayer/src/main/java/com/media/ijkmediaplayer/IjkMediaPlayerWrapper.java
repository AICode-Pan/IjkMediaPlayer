package com.media.ijkmediaplayer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.MediaInfo;
import tv.danmaku.ijk.media.player.misc.IMediaDataSource;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;

/**
 * Created by zhujun on 10/04/2017.
 */

public class IjkMediaPlayerWrapper implements IMediaPlayer {
    private final String TAG = "IjkMediaPlayer";
    private IjkMediaPlayer mPlayer;

    private final static Object sInitLock = new Object();

    private static IjkMediaPlayerWrapper instance;

    private boolean supportHarCode = false;

    public static IjkMediaPlayerWrapper getInstance() {
        if (instance == null) {
            synchronized (sInitLock) {
                if (instance == null)
                    instance = new IjkMediaPlayerWrapper();
            }
        }
        return instance;
    }

    private IjkMediaPlayerWrapper() {
        init();
    }

    private void init() {
        supportHarCode = isHarCode();
        Log.d(TAG, "IjkMediaPlayerWrapper init supportHarCode : " + supportHarCode);

        mPlayer = new IjkMediaPlayer();
        mPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT);

        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", supportHarCode ? 1 : 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);

        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 5);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);

    }


    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        mPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void setDataSource(Context context, Uri uri) throws
            IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        reset();
        mPlayer.setDataSource(context, uri);
    }

    @Override
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws
            IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        reset();
        mPlayer.setDataSource(context, uri, map);
    }

    @Override
    public void setDataSource(FileDescriptor fileDescriptor) throws
            IOException, IllegalArgumentException, IllegalStateException {
        reset();
        mPlayer.setDataSource(fileDescriptor);
    }

    @Override
    public void setDataSource(String s) throws
            IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        reset();
        mPlayer.setDataSource(s);
    }

    @Override
    public String getDataSource() {
        return mPlayer.getDataSource();
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        mPlayer.prepareAsync();
    }

    @Override
    public void start() throws IllegalStateException {
        Log.d("test", "mPlayer start");
        mPlayer.start();
    }

    @Override
    public void stop() throws IllegalStateException {
        mPlayer.stop();
    }

    @Override
    public void pause() throws IllegalStateException {
        mPlayer.pause();
    }

    @Override
    public void setScreenOnWhilePlaying(boolean b) {
        mPlayer.setScreenOnWhilePlaying(b);
    }

    @Override
    public int getVideoWidth() {
        return mPlayer.getVideoWidth();
    }

    @Override
    public int getVideoHeight() {
        return mPlayer.getVideoHeight();
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void seekTo(long l) throws IllegalStateException {
        mPlayer.seekTo(l);
    }

    @Override
    public long getCurrentPosition() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public void release() {
        //TODO
        mPlayer.release();
    }

    @Override
    public void reset() {
        mPlayer.reset();
        mPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT);

        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", supportHarCode ? 1 : 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);

        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
    }

    @Override
    public void setVolume(float v, float v1) {
        mPlayer.setVolume(v, v1);
    }

    @Override
    public int getAudioSessionId() {
        return mPlayer.getAudioSessionId();
    }

    @Override
    public MediaInfo getMediaInfo() {
        return mPlayer.getMediaInfo();
    }

    @Override
    public void setLogEnabled(boolean b) {
        mPlayer.setLogEnabled(b);
    }

    @Override
    public boolean isPlayable() {
        return mPlayer.isPlayable();
    }

    @Override
    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        mPlayer.setOnPreparedListener(onPreparedListener);
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        mPlayer.setOnCompletionListener(onCompletionListener);
    }

    @Override
    public void setOnBufferingUpdateListener(OnBufferingUpdateListener
                                                     onBufferingUpdateListener) {
        mPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
    }

    @Override
    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        mPlayer.setOnSeekCompleteListener(onSeekCompleteListener);
    }

    @Override
    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener
                                                      onVideoSizeChangedListener) {
        mPlayer.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
    }

    @Override
    public void setOnErrorListener(OnErrorListener onErrorListener) {
        mPlayer.setOnErrorListener(onErrorListener);
    }

    @Override
    public void setOnInfoListener(OnInfoListener onInfoListener) {
        mPlayer.setOnInfoListener(onInfoListener);
    }

    @Override
    public void setOnTimedTextListener(OnTimedTextListener onTimedTextListener) {

    }

//    @Override
//    public void setOnTimedTextListener(IMediaPlayer.OnTimedTextListener onTimedTextListener) {
//        mPlayer.setOnTimedTextListener(onTimedTextListener);
//    }

    @Override
    public void setAudioStreamType(int i) {
        mPlayer.setAudioStreamType(i);
    }

    @Override
    public void setKeepInBackground(boolean b) {
        mPlayer.setKeepInBackground(b);
    }

    @Override
    public int getVideoSarNum() {
        return mPlayer.getVideoSarNum();
    }

    @Override
    public int getVideoSarDen() {
        return mPlayer.getVideoSarDen();
    }

    @Override
    public void setWakeMode(Context context, int i) {
        mPlayer.setWakeMode(context, i);
    }

    @Override
    public void setLooping(boolean b) {
        mPlayer.setLooping(b);
    }

    @Override
    public boolean isLooping() {
        return mPlayer.isLooping();
    }

    @Override
    public ITrackInfo[] getTrackInfo() {
        return mPlayer.getTrackInfo();
    }

    @Override
    public void setSurface(Surface surface) {
        mPlayer.setSurface(surface);
    }

    @Override
    public void setDataSource(IMediaDataSource iMediaDataSource) {
        mPlayer.setDataSource(iMediaDataSource);
    }

    /**
     * 判断是否支持硬解码
     * @return
     */
    private boolean isHarCode() {
        //读取系统配置文件/system/etc/media_codecc.xml
        File file = new File("/system/etc/media_codecs.xml");
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (Exception e) {
            // TODO: handle exception
        }

        XmlPullParserFactory pullFactory;
        try {
            pullFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = pullFactory.newPullParser();
            xmlPullParser.setInput(in, "UTF-8");
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("MediaCodec".equals(tagName)) {
                            String componentName = xmlPullParser.getAttributeValue(0);
                            if(componentName.startsWith("OMX.")) {
                                if(!componentName.startsWith("OMX.google.")) {
                                    return true;
                                }
                            }
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}
