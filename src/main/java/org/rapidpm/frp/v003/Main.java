package org.rapidpm.frp.v003;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 *
 */
public class Main {


  public static void main(String[] args) {

    final Supplier<String> task = ()-> "Hello reactive World";

    final CompletableFuture<String> cf = CompletableFuture.supplyAsync(task);

    final CompletableFuture<Void> acceptA = cf.thenAccept(System.out::println);
  }
}
