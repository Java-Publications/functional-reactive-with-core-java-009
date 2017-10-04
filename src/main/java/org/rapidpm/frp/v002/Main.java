package org.rapidpm.frp.v002;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class Main {

  public static void main(String[] args) {


    final CompletableFuture<Void> cfRunnableB = CompletableFuture
        .runAsync(() -> System.out.println("Hello reactive World"));

    try {
      final Void aVoidA = cfRunnableB.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    try {
      final Void aVoidB = cfRunnableB.get(1_000 , MILLISECONDS);
    } catch (InterruptedException | TimeoutException | ExecutionException e) {
      e.printStackTrace();
    }

    //cfRunnableB.getNow()

    final CompletableFuture<String> cfCallableB = CompletableFuture
        .supplyAsync(() -> "Hello reactive World");

    final String now = cfCallableB.getNow("alternative");




  }
}
