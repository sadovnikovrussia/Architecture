package dev.sadovnikov.architecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        Observable<Integer> observable = Observable.just(1, 2, 4, 8);
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer + ", " + getLocalClassName());
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });

        List<Integer> integers = new ArrayList<>();
        for (Integer j = 0; j < 10000; j++) {
            integers.add(j);
        }

        Observable<Integer> observable1 = Observable.fromIterable(integers);
        observable1
                .filter(integer -> integer > 10)
                .map(integer -> integer * 2)
                .map(integer -> integer - 500)
                .compose(new AsyncTransformer<>())
                .subscribe(
                        str -> Log.d(TAG, str),
                        e -> Log.w(TAG, e));

    }

    class AsyncTransformer<T> implements ObservableTransformer<T, String> {

        @Override
        public ObservableSource<String> apply(Observable<T> upstream) {
            return upstream.
                    map(String::valueOf)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
