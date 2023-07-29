package com.batch.playground;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private String JOB_NAME = "JOB";

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step", jobRepository)
                .tasklet(
                        getTasklet(null), platformTransactionManager).build();
    }

    @StepScope
    @Bean
    public Tasklet getTasklet(@Value("#{jobParameters['param']}") String param) {
        return (contribution, chunkContext) -> {
            System.out.printf("Olá, %s", param);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step).build();
    }

}
