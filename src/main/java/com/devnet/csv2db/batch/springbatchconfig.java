package com.devnet.csv2db.batch;

import java.beans.BeanProperty;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.devnet.csv2db.model.Customer;
import com.devnet.csv2db.util.DateUtil;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class springbatchconfig {

	@Bean
	public FlatFileItemReader<Customer> reader(){
			return new FlatFileItemReaderBuilder<Customer>() 
				.linesToSkip(1)
				.name("csvReader")
				.resource(new ClassPathResource("customers.csv"))
				.delimited()
				.delimiter(",")
				.names("index", "customerId", "firstName", "lastName", "company", "city",
	                        "country", "phone1", "phone2",
	                        "email", "subscriptionDate", "website")
				 .fieldSetMapper(fieldSet -> Customer.builder()
						  .customerId(fieldSet.readString("customerId"))
						  .firstName(fieldSet.readString("firstName"))
	                        .lastName(fieldSet.readString("lastName"))
	                        .company(fieldSet.readString("company"))
	                        .city(fieldSet.readString("city"))
	                        .country(fieldSet.readString("country"))
	                        .phone1(fieldSet.readString("phone1"))
	                        .phone2(fieldSet.readString("phone2"))
	                        .email(fieldSet.readString("email"))
	                        .subscriptionDate(DateUtil.parseDate(fieldSet.readString("subscriptionDate")))
	                        .website(fieldSet.readString("website"))
	                        .build()
	                ).build();
						 
			
	}
	
	@Bean
	public JpaItemWriter<Customer> writer(EntityManagerFactory entityMangerFactory){
		return new JpaItemWriter<>(entityMangerFactory);
	}
	
	@Bean
	public Job csvImporterJob(Step customerStep,JobRepository jobRepository,
							  ImportJobListener importjobListener) {
		return new JobBuilder("csvImporterJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(importjobListener)
				.flow(customerStep)
				.end()
				.build();
	}
	
	@Bean
	public Step csvImporterStep(ItemReader<Customer> csvReader,ItemWriter<Customer> csvWriter,CustomerJobProcessor processor,
			JobRepository jobRepository,PlatformTransactionManager tx
			) {
		
		return new StepBuilder("csvImporterStep",jobRepository) 
			.<Customer,Customer>chunk(50)
			.transactionManager(tx)
			.reader(csvReader)
			.writer(csvWriter)
			.processor(processor)
			.allowStartIfComplete(true)
			.build();
		
	
	}
	
}

