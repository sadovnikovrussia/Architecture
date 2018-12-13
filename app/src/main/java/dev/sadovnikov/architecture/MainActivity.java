package dev.sadovnikov.architecture;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindString(R.string.Superbutton)
    String superButton;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.button)
    Button button;

    List<Hotel> hotels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        button.setOnClickListener(v -> Log.i(TAG, "onCreate: mimi"));
        editText.setText(superButton);

        if (savedInstanceState == null){
            Log.d(TAG, "onCreate: null");
            OttService ottService = ApiFactory.getOttSevice();
            Disposable disposable = ottService.getHotels()
                    .map(stringListMap -> stringListMap.get("hotels"))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .cache()
                    .subscribe(hotels -> {
                        this.hotels = hotels;
                        Log.i(TAG, "onNext: " + this.hotels);
                    });
        } else {
            Log.d(TAG, "onCreate: notNull");
            hotels = (List<Hotel>) savedInstanceState.getSerializable("hotels");
            Log.d(TAG, "onCreate: " + savedInstanceState.getSerializable("hotels"));
        }


        //ViewCollections.set(editText, View.ALPHA, 0.0F);

        //        Observable<Integer> observable = Observable.just(1, 2, 4, 8);
//        observable.subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe: ");
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.d(TAG, "onNext: " + integer + ", " + getLocalClassName());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.w(TAG, "onError: ", e);
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete: ");
//            }
//        });
//
//        List<Integer> integers = new ArrayList<>();
//        for (Integer j = 0; j < 10; j++) {
//            integers.add(j);
//        }
//        List<Integer> integers2 = new ArrayList<>();
//        for (Integer j = 200; j < 300; j++) {
//            integers.add(j);
//        }
//
//        List<Integer> integers3 = new ArrayList<>();
//        for (Integer j = 500; j < 600; j++) {
//            integers3.add(j);
//        }
//        List<String> strings = new ArrayList<>();
//        strings.add("q");
//        strings.add("w");
//        strings.add("e");
//        strings.add("r");
//        strings.add("t");
//        strings.add("y");
//
//
//        Observable<Integer> observable2 = Observable.fromIterable(integers2);
//        Observable<Integer> observable3 = Observable.fromIterable(integers3);
//        Observable<Integer> observable1 = Observable.fromIterable(integers);
//        Observable<String> observable4 = Observable.fromIterable(strings);
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
//        Observable
//                .zip(observable1, observable4, Bukvoed::kaka)
//                .subscribe(s -> Log.d(TAG, "onCreate: " + s));

//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();
//
//        Log.d(TAG, "onCreate: " + Thread.currentThread().getName());
//        Observable<Response> responseObservable = Observable.fromCallable(() -> client.newCall(request).execute());
//        Disposable disposable = responseObservable
//                .map(response -> {
//                    Log.d(TAG, "onMap1" + ", " + Thread.currentThread().getName());
//                    return response.body() != null ? response.body().string() : "Пустота";
//                })
//                .flatMap(s -> Observable.fromArray(new String[]{s, s}))
//                .map(s -> {
//                            Log.d(TAG, "onMap2" + ", " + Thread.currentThread().getName());
//                            return s + s;
//                        }
//                )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(s -> Log.i(TAG, "onNext: " + s));
//        CompositeDisposable compositeDisposable = new CompositeDisposable();
//        compositeDisposable.add(disposable);
//        compositeDisposable.dispose();


//        Observable<Object> observable = Observable.create(emitter -> client.newCall(request).enqueue(
//                new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.w(TAG, "onFailure: " + Thread.currentThread().getName(), e);
//                        emitter.onError(e);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.d(TAG, "onResponse: " + Thread.currentThread().getName());
//                        Log.d(TAG, "onResponse: " + response);
//                        emitter.onNext(response.body().string());
//                        emitter.onComplete();
//                    }
//                }));
//        observable
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<Object>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "onSubscribe: " + Thread.currentThread().getName());
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                        Log.d(TAG, "onNext: " + Thread.currentThread().getName());
//                        Log.d(TAG, "onNext: " + o);
//                        editText.setText(o.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + Thread.currentThread().getName());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: " + Thread.currentThread().getName());
//                    }
//                });


//        Observable<String> observable5 = Observable.fromCallable(callable);
//        observable5.subscribe(s -> Log.d(TAG, "fromCallable: " + s));
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


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putSerializable("hotels", (Serializable) hotels);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
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
