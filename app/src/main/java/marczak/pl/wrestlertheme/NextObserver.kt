package marczak.pl.slowatrudne.utils

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Project "SlowaTrudne"
 *
 *
 * Created by Lukasz Marczak
 * on 05.03.2017.
 */

abstract class NextObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable?) {
        Log.e("TAG", e.toString())

    }
}
