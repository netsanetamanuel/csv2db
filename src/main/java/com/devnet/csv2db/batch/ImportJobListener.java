package com.devnet.csv2db.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImportJobListener implements JobExecutionListener{

	
	@Override
	public void beforeJob(JobExecution jobExcution) {
		log.info("Job:{} Excution Started",jobExcution.getJobInstance().getJobName());
	}
	
	@Override
	public void afterJob(JobExecution jobExcution) {
		
		if(jobExcution.getStatus()==BatchStatus.COMPLETED) {
			log.info("Job completed: {}",jobExcution.getJobInstance().getJobName());
		}else if(jobExcution.getStatus()==BatchStatus.FAILED){
			log.error("Error while running job: {}",jobExcution.getJobInstance().getJobName());
		}
	}
}
