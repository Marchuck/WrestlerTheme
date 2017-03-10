package marczak.pl.wrestlertheme

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import marczak.pl.slowatrudne.utils.NextObserver
import rxandroidaudio.RxAudioPlayer
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity.javaClass.simpleName
    }

    var playDisposable: Disposable? = null;

    val rxPermissions  by lazy { RxPermissions(MainActivity@ this) }

    var fileName: String? = null

    val rxAudio = RxAudioPlayer.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rxPermissions.request(
                Manifest.permission.RECORD_AUDIO,
                //                Manifest.permission.CAPTURE_AUDIO_OUTPUT,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        ).filter { o -> o }
                .subscribe {
                    setupAll()
                }
    }


    private fun setupAll() {
        Log.d(TAG, "setupAll")
        val wrapper = RecorderWrapper()

        val start = findViewById(R.id.start) as Button
        val stop = findViewById(R.id.stop) as Button
        val play = findViewById(R.id.play) as Button
        val prepare = findViewById(R.id.prepare) as Button

        wrapper.setup()
        start.setOnClickListener {
            playDisposable?.dispose()

            wrapper.start()
            Toast.makeText(MainActivity@ this, "Started", Toast.LENGTH_LONG).show()
        }
        stop.setOnClickListener {

            wrapper.stop()
            Toast.makeText(MainActivity@ this, "Stopped, saved", Toast.LENGTH_LONG).show()
        }

        val sucks = RxSucks()

        prepare.setOnClickListener {

            Toast.makeText(MainActivity@ this, "preparing", Toast.LENGTH_LONG).show()
            sucks.prepare(this, wrapper.lastFile)
        }

        play.setOnClickListener {

            Toast.makeText(MainActivity@ this, "preparing", Toast.LENGTH_LONG).show()
            sucks.playAll()
        }

    }

    override fun onStop() {
        super.onStop()
        playDisposable?.dispose()
    }
}
