package com.batch.playground.jobs.steps;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class PrintStepConfig {

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step", jobRepository)
                .<Integer, String>chunk(10, platformTransactionManager)
                .reader(countTo10Reader())
                .processor(evenOrOdd())
                .writer(print())
                .build();
    }

    private ItemWriter<? super String> print() {
        return items -> items.forEach(System.out::println);
    }

    private FunctionItemProcessor<? super Integer, String> evenOrOdd() {
        return new FunctionItemProcessor<Integer, String>(item -> item % 2 == 0 ?
                String.format("%s Par", item) :
                String.format("%s ímpar", item));
    }

    private IteratorItemReader<Integer> countTo10Reader() {
        return new IteratorItemReader<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10).iterator());
    }
}
