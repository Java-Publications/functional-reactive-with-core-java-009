package org.rapidpm.frp.v008;

import static java.lang.String.valueOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.rapidpm.frp.model.Pair;

/**
 *
 */
public class Main {

  private static final int nThreads = Runtime.getRuntime()
                                             .availableProcessors();


  private static final ExecutorService poolToWait = Executors
      .newFixedThreadPool(nThreads * 10);

  private static final ExecutorService poolToWork = Executors
      .newFixedThreadPool(nThreads);

  private static CompletableFuture<Void> createCF() {
    return supplyAsync(() -> valueOf(new Random().nextInt(10)) , poolToWait)
        .thenCombineAsync(supplyAsync(() -> valueOf(new Random().nextInt(10)) , poolToWait) ,
                          (a , b) -> a + " + " + b ,
                          poolToWork)
        .thenCombineAsync(supplyAsync(() -> valueOf(new Random().nextInt(10)) , poolToWait) ,
                          (a , b) -> a + " - " + b ,
                          poolToWork)
        .thenAcceptAsync(System.out::println);
  }


  public static void main(String[] args) {

    IntStream
        .range(0 , 1_000)
        .parallel()
        .mapToObj(value -> new Pair<>(value , createCF()))
        .map(p -> {
               p.getT2().join();
               return p.getT1();
             }
        )
        .forEach(System.out::println);

    poolToWait.shutdown();
    poolToWork.shutdown();
  }


}
