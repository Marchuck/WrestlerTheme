package marczak.pl.wrestlertheme;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Project "WrestlerTheme"
 * <p>
 * Created by Lukasz Marczak
 * on 26.03.2017.
 */

public class PlayableRec implements ObservableOnSubscribe<Boolean>,
        Disposable, Cancellable, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
    Context context;
    AtomicBoolean disposed = new AtomicBoolean(false);
    AtomicBoolean prepared = new AtomicBoolean(false);
    AtomicBoolean completed = new AtomicBoolean(false);
    @Nullable
    MediaPlayer player;
    @Nullable
    ObservableEmitter<Boolean> emitter;

    File file;

    public PlayableRec(Context context, File file) {
        this.context = context;
        this.file = file;
    }

    @Override
    public void subscribe(@Nullable ObservableEmitter<Boolean> _emitter) throws Exception {
        player = new MediaPlayer();
        emitter = _emitter;
        player.setDataSource(file.getAbsolutePath());

        if (emitter != null) {
            emitter.setDisposable(this);
            emitter.setCancellable(this);
            if (player == null) {
                emitter.onError(new NullPointerException("Failed to create media player!!!"));
            } else {
                player.setOnPreparedListener(this);
                player.prepareAsync();
                player.setOnCompletionListener(this);

            }
        }

    }

    public MediaPlayer getPlayer() {
        return player;
    }
    Subject<Boolean> sub = PublishSubject.create();

    /**
     * must be called after first item emitted!
     */
    public Observable<Boolean> play() {
        if (player != null) {
            player.start();
        }
        return sub;

    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed.set(true);
        if (prepared.get() && player != null) {
            player.release();
        }
    }

    @Override
    public boolean isDisposed() {
        return disposed.get();
    }

    @Override
    public void cancel() throws Exception {
        dispose();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        prepared.set(true);
        if (emitter != null) emitter.onNext(true);
        Chain.preparation.onNext(true);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        completed.set(true);
        if (emitter != null) emitter.onNext(true);
        sub.onNext(true);
    }
}
