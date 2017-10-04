package org.rapidpm.frp.v004;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 *
 */
public class Main {

  public static void main(String[] args) {

    final Supplier<String> task = () -> "Hello reactive World";

    final CompletableFuture<String> cf = CompletableFuture.supplyAsync(task);

    final CompletableFuture<Void> acceptA = cf.thenAcceptAsync(System.out::println);


    final ExecutorService fixedThreadPool = Executors
        .newFixedThreadPool(Runtime.getRuntime()
                                   .availableProcessors());

    final CompletableFuture<Void> acceptB = cf.thenAcceptAsync(System.out::println, fixedThreadPool);

  }


}
