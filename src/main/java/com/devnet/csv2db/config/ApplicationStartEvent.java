package com.devnet.csv2db.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecutionException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationStartEvent {
	
	private final JobOperator jobOperator;
	private final Job csvImporterJob;
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void onReadyEvent() throws JobExecutionException{
		 JobParameters jobParameters = new JobParametersBuilder()
				 .addString("ignoreCountry","india")
				 .addLong("StartTime",System.currentTimeMillis())
				 .toJobParameters();
		 
		 jobOperator.start(csvImporterJob, jobParameters);
	}

}
