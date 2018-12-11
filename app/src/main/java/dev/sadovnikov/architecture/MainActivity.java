package dev.sadovnikov.architecture;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
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

    @SuppressLint("CheckResult")
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
        for (Integer j = 0; j < 10; j++) {
            integers.add(j);
        }
        List<Integer> integers2 = new ArrayList<>();
        for (Integer j = 200; j < 300; j++) {
            integers.add(j);
        }

        List<Integer> integers3 = new ArrayList<>();
        for (Integer j = 500; j < 600; j++) {
            integers3.add(j);
        }
        List<String> strings = new ArrayList<>();
        strings.add("q");
        strings.add("w");
        strings.add("e");
        strings.add("r");
        strings.add("t");
        strings.add("y");


        Observable<Integer> observable2 = Observable.fromIterable(integers2);
        Observable<Integer> observable3 = Observable.fromIterable(integers3);
        Observable<Integer> observable1 = Observable.fromIterable(integers);
        Observable<String> observable4 = Observable.fromIterable(strings);
//        observable1
//                .filter(integer -> integer > 10)
//                .map(integer -> integer * 2)
//                .map(integer -> integer - 500)
//                .compose(new AsyncTransformer<>())
//                .subscribe(
//                        str -> Log.d(TAG, str),
//                        e -> Log.w(TAG, e));
//        Observable.merge(observable1, observable2, observable3)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        str -> Log.d(TAG, str.toString()),
//                        e -> Log.w(TAG, e));
        Observable
                .zip(observable1, observable4, Bukvoed::kaka)
                .subscribe(s -> Log.d(TAG, "onCreate: " + s));

//        RxSQLite.get().querySingle(RequestTable.TABLE, where)
//                .compose(RxSchedulers.async())
//                .flatMap(request -> {
//                    if (request.getStatus() == RequestStatus.IN_PROGRESS) {
//                        mLoadingView.showLoadingIndicator();
//                        return Observable.empty();
//                    } else if (request.getStatus() == RequestStatus.ERROR) {
//                        return Observable.error(new IOException(request.getError()));
//                    }
//                    return RxSQLite.get().querySingle(CityTable.TABLE).compose(RxSchedulers.async());
//                })


    }


    static class Bukvoed {
        String s;
        int i;

        public Bukvoed(int i, String s) {
            this.s = s;
            this.i = i;
        }

        static String kaka(Integer i, String s) {
            ArrayList<String> strings = new ArrayList<>();
            strings.add("qqq1");
            strings.add("wwww1");

            return s + " : " + i;
        }

        @NonNull
        @Override
        public String toString() {
            return s + " : " + i;
        }
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
