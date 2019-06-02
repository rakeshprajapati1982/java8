package com.readtorakesh.java8.executor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainApp {

	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		
		//mainApp.fixedThreadPoolExecutorExample();
		//mainApp.singleThreadExecutorExample();
		//mainApp.singleThreadExecutorSubmitCallableExample();
		//mainApp.singleThreadExecutorInvokeAllExample();
		
		//mainApp.scheduleOnetimeRunnableExample();
		//mainApp.scheduleOnetimeCallableExample();
		//mainApp.scheduleAtFixedRateExample();
		mainApp.scheduleWithFixedDelayExample();
		
	}
	
	private void manuallyInstantiateExecutorService() {
		int corePoolSize = 10;
		int maximumPoolSize = 10;
		long keepAliveTime = 0L;
		TimeUnit unit = TimeUnit.SECONDS;
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

		ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
				workQueue);
	}
	
	/**
	 *  Executors.newFixedThreadPool
	 */
	private void fixedThreadPoolExecutorExample() {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		for (int i = 1; i <= 5; i++) {
			int val = i;
			executorService.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				print("Value=" + val + " Square=" + (val * val));
			});
		}
		
		print("Tasks sent to executor service");
		shutdownExecutor(executorService);
	}
	
	private void singleThreadExecutorExample() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		for (int i = 1; i <= 5; i++) {
			int val = i;
			executorService.execute(() -> {
				if(val==2) {
					throw new RuntimeException("Exception thrown for val="+2);
				}
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				print("Value=" + val + " Square=" + (val * val));
			});
		}
		
		print("Tasks sent to executor service");
		shutdownExecutor(executorService);
	}
	
	private void singleThreadExecutorSubmitCallableExample() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		List<Future<String>> futures = new ArrayList<>();
		
		for (int i = 1; i <= 5; i++) {
			int val = i;
			Future<String> future = executorService.submit(() -> {
				return "Value=" + val + " Square=" + (val * val);
			});
			futures.add(future);
		}
		
		print("Tasks submitted");
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(Future<String> future: futures) {
			try {
				print(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		shutdownExecutor(executorService);
	}
	
	private void singleThreadExecutorInvokeAllExample() {
		
		Callable<String> callable1 = () -> {return "Callable 1"; };
		Callable<String> callable2 = () -> {return "Callable 2"; };
		Callable<String> callable3 = () -> {return "Callable 3"; };
		
		List<Callable<String>> tasks = new ArrayList<>();
		tasks.add(callable1);
		tasks.add(callable2);
		tasks.add(callable3);
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		print("Tasks submitted");
		
		try {
			print("Output of tasks from Future object");
			List<Future<String>> futures = executorService.invokeAll(tasks);
			for(Future<String> future: futures) {
				print(future.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		shutdownExecutor(executorService);
	}
	
	private void scheduleOnetimeRunnableExample() {
		Runnable task = () -> {
			print("Task is running");
			print("Task is complete");
		};
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(task, 10, TimeUnit.SECONDS);
		print("Task scheduled to run after 10 seconds");
		
		try {
			TimeUnit.SECONDS.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		print("isDone(): "+scheduledFuture.isDone());
		print("getDelay(): "+scheduledFuture.getDelay(TimeUnit.SECONDS));
		
		shutdownExecutor(scheduledExecutorService);
	}
	
	private void scheduleOnetimeCallableExample() {
		Callable<String> task = () -> {
			print("Task is running");
			print("Task is complete");
			return "This is return value";
		};
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		ScheduledFuture<String> scheduledFuture = scheduledExecutorService.schedule(task, 10, TimeUnit.SECONDS);
		print("Task scheduled to run after 10 seconds");
		
		try {
			TimeUnit.SECONDS.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		print("isDone(): "+scheduledFuture.isDone());
		try {
			print("Return Value: "+ scheduledFuture.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		shutdownExecutor(scheduledExecutorService);
	}
	
	private void scheduleAtFixedRateExample() {
		
		Runnable task = () -> {
			print("Task is running");
			print("Task is complete");
		};
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(task, 3, 2, TimeUnit.SECONDS);
		print("Task scheduled");
		try {
			TimeUnit.SECONDS.sleep(9);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		shutdownExecutor(scheduledExecutorService);
	}

	private void scheduleWithFixedDelayExample() {
		
		Runnable task = () -> { 
			print("Task is running");
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			print("Task is complete");
		};
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleWithFixedDelay(task, 3, 2, TimeUnit.SECONDS);
		print("Task scheduled");
		try {
			TimeUnit.SECONDS.sleep(18);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		shutdownExecutor(scheduledExecutorService);
	}
	
	private void shutdownExecutor(ExecutorService executorService) {
		try {
			System.out.println("Shutdown executor service");
			// Request shutdown, it should stop taking more tasks.
			executorService.shutdown();
			
			System.out.println("Waiting for pending tasks to finish");
			//wait for pending tasks to finish for maximum 10 seconds
			executorService.awaitTermination(10, TimeUnit.SECONDS);
		}catch (InterruptedException e) {
			//Log exception and carry on with finally block
			System.out.println(e.getMessage());
		}finally {
			//Check if all pending tasks completed
			if(!executorService.isTerminated()) {
				System.out.println("All tasks not completed. Doing force shutdown, pending tasks not be done.");
			}
			
			// Do force shutdown anyways. Even if all pending tasks completed, it will not hurt
			executorService.shutdownNow();
			System.out.println("Shutdown finish");
		}
	}
	
	private static void print(String str) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		System.out.println(dateFormat.format(new Date()) + " - " + Thread.currentThread().getName() + " -> " + str);
	}
}

