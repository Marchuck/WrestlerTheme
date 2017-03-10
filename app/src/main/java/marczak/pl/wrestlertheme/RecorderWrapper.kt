package marczak.pl.wrestlertheme

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import android.util.AndroidException
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import marczak.pl.wrestlertheme.RxSucks.TAG
import rxandroidaudio.AudioRecorder
import rxandroidaudio.PlayConfig
import rxandroidaudio.RxAudioPlayer
import java.io.File


/**
 * Project "WrestlerTheme"
 *
 *
 * Created by Lukasz Marczak
 * on 06.03.2017.
 */

class RecorderWrapper {

    val lastFile: String = "xxxxx"

    fun setup(): RecorderWrapper {

        val mAudioRecorder = AudioRecorder.getInstance()
        val mAudioFile = getFile(lastFile)
        mAudioRecorder.prepareRecord(MediaRecorder.AudioSource.MIC,
                MediaRecorder.OutputFormat.THREE_GPP, MediaRecorder.AudioEncoder.AAC,
                mAudioFile)
        return this
    }

    fun start(): RecorderWrapper {
        val mAudioRecorder = AudioRecorder.getInstance()

        mAudioRecorder.startRecord()
        return this
    }

    fun stop(): RecorderWrapper {
        val mAudioRecorder = AudioRecorder.getInstance()

        mAudioRecorder.stopRecord()
        return this
    }

    fun playLastSequence(context: Context): Observable<Boolean> {
        return playSequence(context, lastFile)
    }

    fun prepareAndHisNameIs(context: Context): Observable<MediaPlayer> {
        val player = RxAudioPlayer.getInstance()

        return player.prepare(PlayConfig.res(context, R.raw.and_his_name_is).build()).toObservable()
    }


    fun prepareTrumpets(context: Context): Observable<MediaPlayer> {
        val player = RxAudioPlayer.getInstance()

        return player.prepare(PlayConfig.res(context, R.raw.trumpets).build()).toObservable()
    }

    fun typedName(fileName: String): Observable<MediaPlayer> {
        val player = RxAudioPlayer.getInstance()

        return player.prepare(PlayConfig.file(getFile(fileName)).build()).toObservable()
    }

    fun playSequence(player1: MediaPlayer, player2: MediaPlayer, player3: MediaPlayer) {
        val player = RxAudioPlayer.getInstance()

        player.play(player1)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { player.play(player2) }
                .flatMap { player.play(player3) }
                .toObservable()
                .subscribe{ Log.d(TAG,"done") }

    }

    fun playSequence(context: Context, fileName: String): Observable<Boolean> {

        val player = RxAudioPlayer.getInstance()

        return Observable.zip<MediaPlayer, MediaPlayer, MediaPlayer, Boolean>(
                prepareAndHisNameIs(context),
                typedName(fileName),
                prepareTrumpets(context),
                Function3<MediaPlayer, MediaPlayer, MediaPlayer, Boolean> { andTheNameIs, johnCena, trumpets ->

                    player.play(andTheNameIs)
                            .flatMap { player.play(johnCena) }
                            .flatMap({ player.play(trumpets) }).toObservable().blockingFirst()
                }).map { o -> false }
    }

    private fun getFile(fileName: String): File {
        return File(Environment.getExternalStorageDirectory().absolutePath +
                File.separator + fileName + suffix)
    }

    companion object {

        var suffix = ".file.m4a"
    }

    fun stopPlaying() {
        RxAudioPlayer.getInstance().stopPlay()
    }
}
