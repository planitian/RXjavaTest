package com.example.administrator.rxjavatest;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button   button;
    private int anInt = 1;
    private Disposable disposable;
    private ListView listView;
    private Operate one;
    private List<Operate> operates;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        button = findViewById(R.id.buttonPanel);
        listView = findViewById(R.id.list_view);
        one = new Operate("one");
        operates = new ArrayList<>();
        operates.add(new Operate("two"));
        operates.add(one);
        operates.add(new Operate("three"));
        button.setOnClickListener(v -> {
            if ((anInt % 2) == 0) {
                RxUtils.unsubscribe(disposable);
            } else {
                method();
            }
        });


    }
  public String  inter(){
      OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).build();
      Request request=new Request.Builder().url("http://wwww.baidu.com").build();
      try {
          Response response = okHttpClient.newCall(request).execute();
          System.out.println("<<<<<<<<<<  Response ");
          Thread.sleep(10000);
          if(response.isSuccessful()){
              return response.message();
          }else {
              return "error";
          }

      } catch (IOException | InterruptedException e) {
          e.printStackTrace();
          return "error";
      }
  }

    private void method() {
      /*  List<Integer> integerList = new ArrayList<>();
        integerList.add(10);
        integerList.add(11);
        integerList.add(12);
        List<String> stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });
*/
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println("<<<<<<<<<<  subscribe ");
                e.onNext(inter());
                e.onComplete();
            }
        });
       observable1.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
           @Override
           public void onSubscribe(Disposable d) {
               System.out.println("<<<<<<<<<<  subscribe observer");
               show();
           }

           @Override
           public void onNext(String s) {
               System.out.println("<<<<<<<<<<  next ");
               System.out.println(s);
           }

           @Override
           public void onError(Throwable e) {

           }

           @Override
           public void onComplete() {
                 hide();
           }
       });

       /* disposable = Observable.just(1,2,3,4).collect(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return new ArrayList<>();
            }
        }, new BiConsumer<List<String>, Integer>() {
            @Override
            public void accept(List<String> strings, Integer integer) throws Exception {
                System.out.println(integer);
                strings.add(String.valueOf(integer));
            }
        })
//                .zip(observable, observable1, (BiFunction<Integer,String,String>)(i,s)-> i+""+s)
                 *//*   .doOnNext(integer -> System.out.println("线程" + Thread.currentThread().getName()))
                    .subscribeOn(Schedulers.newThread())
                    .buffer(3, 2)
                    .concatMap((Function<List<Integer>, ObservableSource<String>>) integer -> {
                        System.out.println("第二次线程" + Thread.currentThread().getName());
                        return Observable.just(String.valueOf(integer.toString()));
                    })
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            System.out.println("订阅线程" + Thread.currentThread().getName());
                        }
                    })*//*
                 .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        System.out.println(strings.toString());
                    }
                });*/
    }

    public void show(){
        alertDialog=new AlertDialog.Builder(this).setMessage("我是过程动画").setCancelable(false).show();
    }

    public void hide(){
        if (alertDialog!=null){
            alertDialog.dismiss();
        }
    }
}
