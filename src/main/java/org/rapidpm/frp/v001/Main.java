package org.rapidpm.frp.v001;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 *
 */
public class Main {

  public static void main(String[] args) {
    final CompletableFuture<Void> cfRunnableA = CompletableFuture.runAsync(new Runnable() {
      @Override
      public void run() {
        System.out.println("Hello reactive World");
      }
    });

    final CompletableFuture<Void> cfRunnableB = CompletableFuture
        .runAsync(() -> System.out.println("Hello reactive World"));


    final CompletableFuture<String> cfCallableA = CompletableFuture
        .supplyAsync(new Supplier<String>() {
          @Override
          public String get() {
            return "Hello reactive World";
          }
        });

    final CompletableFuture<String> cfCallableB = CompletableFuture
        .supplyAsync(() -> "Hello reactive World");

  }
}
