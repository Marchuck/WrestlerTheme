package marczak.pl.wrestlertheme;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Project "WrestlerTheme"
 * <p>
 * Created by Lukasz Marczak
 * on 07.03.2017.
 */


public class RxSucks {
    public static final String TAG = RxSucks.class.getSimpleName();
    public MediaPlayer name;
    public MediaPlayer trumpets;
    public MediaPlayer andTheName;


    File getFile(String fileName) {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + fileName + ".file.m4a");
    }

    public interface Callback {
        void call();
    }

    public void make(final Context c, final String name, Callback callback) {
        prepareFirst(c, callback, name);
    }

    public void prepare(final Context c, final String name_) {
        try {
            andTheName = MediaPlayer.create(c, R.raw.and_his_name_is);
            andTheName.setVolume(1, 1);
            andTheName.setAudioStreamType(AudioManager.STREAM_MUSIC);
            andTheName.setLooping(false);
            andTheName.prepareAsync(); andTheName.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared:1 ");
                    try {

                        name.setDataSource(getFile(name_).getAbsolutePath());
                        name.setVolume(1, 1);
                        name.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        name.setLooping(false);
                        name.prepareAsync();  name.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {

                                Log.d(TAG, "onPrepared: 2");

                                trumpets = MediaPlayer.create(c, R.raw.trumpets);
                                trumpets.setVolume(1, 1);
                                trumpets.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                trumpets.setLooping(false);
                                trumpets.prepareAsync();  trumpets.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        Log.d(TAG, "onPrepared: 3");
                                    }
                                });

                            }
                        });


                    } catch (Throwable x) {
                        Log.d(TAG, "onPrepared: "+x);
                    }


                }
            });



        } catch (Throwable a) {
            Log.e(TAG, "prepare: ", a);
        }
        Log.d(TAG, "prepare: ");
    }

    void prepareFirst(final Context context, final Callback callback, final String fileName) {
        try {
            andTheName = MediaPlayer.create(context, R.raw.and_his_name_is);
            andTheName.setVolume(1, 1);
            andTheName.setAudioStreamType(AudioManager.STREAM_MUSIC);
            andTheName.setLooping(false);
            andTheName.prepareAsync();
            andTheName.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: #1");
                    prepareSecond(context, callback, fileName);
                }
            });
        } catch (Throwable c) {
            Log.e(TAG, "prepareFirst: ", c);
        }
    }

    public void playAll() {
        andTheName.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                name.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        trumpets.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                Log.d(TAG, "onCompletion: ");
                            }
                        });
                        trumpets.start();
                    }
                });
                name.start();
            }
        });
        andTheName.start();
    }

    private void prepareSecond(final Context context, final Callback callback, String fileName) {
        name = new MediaPlayer();
        try {
            name.setDataSource(getFile(fileName).getAbsolutePath());
            name.setVolume(1, 1);
            name.setAudioStreamType(AudioManager.STREAM_MUSIC);
            name.setLooping(false);
            name.prepareAsync();
            name.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: #2");
                    prepareThird(context, callback);
                }
            });
        } catch (Throwable c) {
            Log.e(TAG, "prepareSecond: ", c);
        }
    }

    void prepareThird(final Context context, final Callback callback) {
        try {
            trumpets = MediaPlayer.create(context, R.raw.trumpets);
            trumpets.setVolume(1, 1);
            trumpets.setAudioStreamType(AudioManager.STREAM_MUSIC);
            trumpets.setLooping(false);
            trumpets.prepareAsync();
            trumpets.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: #3");
                    callback.call();
                }
            });
        } catch (Throwable c) {
            Log.e(TAG, "prepareThird: ", c);
        }
    }

}
