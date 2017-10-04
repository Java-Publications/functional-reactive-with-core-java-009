package org.rapidpm.frp.v005;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 */
public class Main {

  public static void main(String[] args) {

    final ExecutorService fixedThreadPool = Executors
        .newFixedThreadPool(Runtime.getRuntime()
                                   .availableProcessors());

    final Supplier<String> task = () -> "Hello reactive World";
    final Consumer<String> consumer = System.out::println;

    final CompletableFuture<Void> cfA = CompletableFuture
        .supplyAsync(task)
        .thenAcceptAsync(consumer , fixedThreadPool);


    final CompletableFuture<Void> cfB = CompletableFuture
        .supplyAsync(() -> "Hello reactive World")
        .thenAcceptAsync(System.out::println , fixedThreadPool);

    final CompletableFuture<Void> cfC = CompletableFuture
        .supplyAsync(() -> "Hello reactive World")
        .thenAcceptAsync(System.out::println);

  }
}
