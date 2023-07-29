package com.batch.playground.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    private String JOB_NAME = "JOB";

    @Bean
    public Job printEvenOrOdd(JobRepository jobRepository, Step step) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }







}
