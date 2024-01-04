package com.multithreadingexample.aravind.demofor_s_and_p.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class Multithreadingserice implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
      
     
         int numThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> futures = new ArrayList<>();


        String serviceUrl = "http://localhost:8080/api/random";

        // Submit tasks to the executor
        for (int i = 0; i < numThreads; i++) {
            Callable<Integer> task = new RestServiceTask(serviceUrl);
            Future<Integer> future = executorService.submit(task);
            futures.add(future);
        }

       
        executorService.shutdown();

        
        List<Integer> results = new ArrayList<>();
        for (Future<Integer> future : futures) {
            try {
                results.add(future.get()); 
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        
        System.out.println("Final Values: " + results);
    }
     private static class RestServiceTask implements Callable<Integer> {
        private final String serviceUrl;

        public RestServiceTask(String serviceUrl) {
            this.serviceUrl = serviceUrl;
        }

        @Override
        public Integer call() throws Exception {
            
            RestTemplate restTemplate = new RestTemplate();
            Integer result = restTemplate.getForObject(serviceUrl, Integer.class);

            System.out.println("Thread finished: " + Thread.currentThread().getName());

            return result;
        }
    }
    
}
