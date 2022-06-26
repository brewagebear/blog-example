package io.github.brewagebear.rxjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PubSubExampleTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    @DisplayName("ObServable를 활용하여 Pub-Sub 구조를 만들 수 있다.")
    void observableTest() {
        Observable<String> observable = Observable.create(
            sub -> {
               sub.onNext("Hello, reactive World!");
               sub.onComplete();
            });

        observable.subscribe(System.out::println);

        Disposable subscribe = Observable.create(
            sub -> {
                sub.onNext("Hello, reactive World!");
                sub.onComplete();
            }).subscribe(
            System.out::println,
            System.err::println,
            () -> System.out.println("Done!")
        );

        subscribe.dispose();
    }


    @Test
    @DisplayName("ObServable 학습 테스트")
    void ObservableStudyTest() {
        Observable<String> test1 = Observable.just("1", "2", "3", "4");
        test1.subscribe(System.out::println);

        Observable<String> test2 = Observable.fromArray(new String[]{"A", "B", "C"});
        test2.subscribe(System.out::println);

        Observable<String> test3 = Observable.fromIterable(Arrays.asList("D", "E", "F"));
        test3.subscribe(System.out::println);

        // 각 스트림을 소비하면서 값을 다운스트림으로 내려준다.
        // 처리 순서는 concat의 인자 값과 같다.
        Observable.concat(Observable.just("Hello "),
                Observable.just("World"),
                Observable.just("!"))
            .forEach(System.out::print);
    }

    @Test
    @DisplayName("메인 쓰레드가 아닌 데몬 쓰레드에서 처리")
    void daemonThreadTest() throws InterruptedException {
        Observable.interval(1, TimeUnit.SECONDS)
            .subscribe(event -> System.out.println("Received: " + event));

        Thread.sleep(5000);
        assertTrue(outContent.toString().contains("Received: 4"));
    }

    @Test
    @DisplayName("메인 쓰레드 슬립을 안걸면 아무것도 출력이 안된다.")
    void daemonThreadTest2(){
        Observable.interval(1, TimeUnit.SECONDS)
            .subscribe(event -> System.out.println("Received: " + event));

        assertEquals(outContent.toString(), "");
    }

}