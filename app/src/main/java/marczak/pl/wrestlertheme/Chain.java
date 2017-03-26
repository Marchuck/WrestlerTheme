package marczak.pl.wrestlertheme;

import android.content.Context;
import android.system.Os;
import android.util.Log;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import io.reactivex.internal.subscriptions.BooleanSubscription;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Project "WrestlerTheme"
 * <p>
 * Created by Lukasz Marczak
 * on 26.03.2017.
 */

public class Chain {

    static AtomicInteger ref = new AtomicInteger(0);
    public static Subject<Boolean> preparation = BehaviorSubject.create();

    public static final String TAG = Chain.class.getSimpleName();

    public void chain(Context c, File file) throws Exception {

        final PlayableRaw _andTheNameIs = new PlayableRaw(c, R.raw.and_his_name_is);
        _andTheNameIs.subscribe(null);
        final PlayableRec _cena = new PlayableRec(c, file);
        _cena.subscribe(null);
        final PlayableRaw _trumpets = new PlayableRaw(c, R.raw.trumpets);
        _trumpets.subscribe(null);

        preparation.flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(@NonNull Boolean aBoolean) throws Exception {
                Log.d(TAG, "apply: ");
                return _andTheNameIs.play();
            }
        }).flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(@NonNull Boolean aBoolean) throws Exception {
                Log.d(TAG, "apply: cena");
                return _cena.play();
            }
        }).flatMap(new Function<Boolean, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(@NonNull Boolean aBoolean) throws Exception {
                Log.d(TAG, "apply: tr");
                return _trumpets.play();
            }
        })
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean o) {
                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
        ;
    }
}
