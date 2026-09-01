package com.devnet.csv2db.batch;

import java.beans.BeanProperty;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.devnet.csv2db.model.Customer;

@Configuration
@EnableBatchProcessing
public class springbatchconfig {

	@Bean
	public FlatFileItemReader<Customer> reader(){
			return new FlatFileItemReaderBuilder<Customer>() 
				.linesToSkip(1)
				.name("cstItemReader")
				.resource(new ClassPathResource("customer.csv"))
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
	                        
						 )
			
	}
	
	
}
