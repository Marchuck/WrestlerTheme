package marczak.pl.wrestlertheme;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.List;

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
            andTheName.prepareAsync();
            andTheName.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared:1 ");
                    try {

                        name.setDataSource(getFile(name_).getAbsolutePath());
                        name.setVolume(1, 1);
                        name.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        name.setLooping(false);
                        name.prepareAsync();
                        name.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {

                                Log.d(TAG, "onPrepared: 2");

                                trumpets = MediaPlayer.create(c, R.raw.trumpets);
                                trumpets.setVolume(1, 1);
                                trumpets.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                trumpets.setLooping(false);
                                trumpets.prepareAsync();
                                trumpets.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        Log.d(TAG, "onPrepared: 3");
                                    }
                                });

                            }
                        });


                    } catch (Throwable x) {
                        Log.d(TAG, "onPrepared: " + x);
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
//todo:
    void combine(Context c) {
        try {
            FileInputStream fis1 = new FileInputStream("android.resource://" + c.getPackageName() + "/and_his_name_is.mp3");
            FileInputStream fis2 = new FileInputStream("android.resource://" + c.getPackageName() + "/and_his_name_is.mp3");
            FileInputStream fis3 = new FileInputStream("android.resource://" + c.getPackageName() + "/trumpets.mp3");
            SequenceInputStream sis = new SequenceInputStream(fis1, fis2);
            FileOutputStream fos = new FileOutputStream(new File("/sdcard/MJdangerousMJBad.wav"));

            int temp;

            try {
                while ((temp = sis.read()) != -1) {

                    fos.write(temp);

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    private void mixFiles(Context c) {
//        try {
//            InputStream is1 = c.getResources().openRawResource(R.raw.and_his_name_is);
//            List<Short> music1 = createMusicArray(is1);
//            InputStream is2 = c.getResources().openRawResource(R.raw.trumpets);
//            List<Short> music2 = createMusicArray(is2);
//
//            completeStreams(music1, music2);
//            short[] music1Array = buildShortArray(music1);
//            short[] music2Array = buildShortArray(music2);
//
//            short[] output = new short[music1Array.length];
//            for (int i = 0; i < output.length; i++) {
//                float samplef1 = music1Array[i] / 32768.0f;
//                float samplef2 = music2Array[i] / 32768.0f;
//
//                float mixed = samplef1 + samplef2;
//// reduce the volume a bit:
//                mixed *= 0.8;
//// hard clipping
//                if (mixed > 1.0f) mixed = 1.0f;
//                if (mixed < -1.0f) mixed = -1.0f;
//                short outputSample = (short) (mixed * 32768.0f);
//                output[i] = outputSample;
//            }
//            saveToFile(output);
//        } catch (NotFoundException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

    public void playAll(Context c) {

        try {
            new Chain().chain(c, getFile("xxxxx"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "playAll: ", e);
        }
//        andTheName.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                name.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        trumpets.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                            @Override
//                            public void onCompletion(MediaPlayer mp) {
//                                Log.d(TAG, "onCompletion: ");
//                            }
//                        });
//                        trumpets.start();
//                    }
//                });
//                name.start();
//            }
//        });
//        andTheName.start();
    }

    private void prepareSecond(final Context context, final Callback callback, String fileName) {
        name = new MediaPlayer();
        try {
            name.setDataSource(getFile(fileName).getAbsolutePath());
            name.setVolume(1, 1);
            name.setAudioStreamType(AudioManager.STREAM_MUSIC);
            name.setLooping(false);

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
