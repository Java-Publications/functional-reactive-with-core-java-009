package org.rapidpm.frp.v006;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.rapidpm.frp.model.serial.Pair;


/**
 *
 */
public class Main {


  public static class InputA extends Pair<String, LocalDateTime> {
    public InputA(String value , LocalDateTime timeStamp) {
      super(value , timeStamp);
    }

    public String value() {return getT1();}

    public LocalDateTime timestamp() {return getT2();}
  }

  public static class InputB extends Pair<String, LocalDateTime> {
    public InputB(String value , LocalDateTime timeStamp) {
      super(value , timeStamp);
    }

    public String value() {return getT1();}

    public LocalDateTime timestamp() {return getT2();}
  }

  public static class InputC extends Pair<String, LocalDateTime> {
    public InputC(String value , LocalDateTime timeStamp) {
      super(value , timeStamp);
    }

    public String value() {return getT1();}

    public LocalDateTime timestamp() {return getT2();}
  }


  private static Supplier<InputA> supplierA() {
    return () -> {
      //some time consuming stuff
      final int nextInt = new Random().nextInt(10);
      return new InputA(String.valueOf(nextInt) , LocalDateTime.now());
    };
  }

  private static Supplier<InputB> supplierB() {
    return () -> {
      //some time consuming stuff
      final int nextInt = new Random().nextInt(10);
      return new InputB(String.valueOf(nextInt) , LocalDateTime.now());
    };
  }

  private static Supplier<InputC> supplierC() {
    return () -> {
      //some time consuming stuff
      final int nextInt = new Random().nextInt(10);
      return new InputC(String.valueOf(nextInt) , LocalDateTime.now());
    };
  }

  private static final int nThreads = Runtime.getRuntime()
                                             .availableProcessors();
  private static final ExecutorService poolInputA = Executors
      .newFixedThreadPool(nThreads);
  private static final ExecutorService poolInputB = Executors
      .newFixedThreadPool(nThreads);
  private static final ExecutorService poolInputC = Executors
      .newFixedThreadPool(nThreads);

  public static CompletableFuture<InputA> sourceA() {
    return CompletableFuture.supplyAsync(supplierA() , poolInputA);
  }

  public static CompletableFuture<InputB> sourceB() {
    return CompletableFuture.supplyAsync(supplierB() , poolInputB);
  }

  public static CompletableFuture<InputC> sourceC() {
    return CompletableFuture.supplyAsync(supplierC() , poolInputC);
  }


  public static class ResultOne extends Pair<String, LocalDateTime> {
    public ResultOne(String value , LocalDateTime timeStamp) {
      super(value , timeStamp);
    }

    public String value() {return getT1();}
    public LocalDateTime timestamp() {return getT2();}
  }

  private static final ExecutorService poolOperatorA = Executors
      .newSingleThreadExecutor();

  private static BiFunction<InputA, InputB, ResultOne> operatorOne() {
    return (a , b) -> {
      //for Demo
      System.out.println("operatorOne.a = " + a);
      System.out.println("operatorOne.b = " + b);
      return new ResultOne(a.value() + " + " + b.value() , LocalDateTime.now());
    };
  }

  public static class ResultTwo extends Pair<String, LocalDateTime> {
    public ResultTwo(String value , LocalDateTime timeStamp) {
      super(value , timeStamp);
    }

    public String value() {return getT1();}
    public LocalDateTime timestamp() {return getT2();}
  }

  private static final ExecutorService poolOperatorB = Executors
      .newSingleThreadExecutor();

  private static BiFunction<ResultOne, InputC, ResultTwo> operatorTwo() {
    return (a , b) -> {
      //for Demo
      System.out.println("operatorTwo.a = " + a);
      System.out.println("operatorTwo.b = " + b);
      return new ResultTwo(a.value() + " - " + b.value() , LocalDateTime.now() );
    };
  }

  public static void main(String[] args) {

    sourceA()
        .thenCombineAsync(sourceB() , operatorOne() , poolOperatorA)
        .thenCombineAsync(sourceC() , operatorTwo() , poolOperatorB)
        .thenAcceptAsync(System.out::println)
        .join();

  }
}
