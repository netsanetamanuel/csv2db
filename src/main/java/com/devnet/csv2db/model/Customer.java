package com.devnet.csv2db.model;

import java.time.LocalDate;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name="customers")
@AllArgsConstructor
@NoArgsConstructor

public class Customer {

	@Id
	private String customerId;
	private String firstName;
	private String lastgName;
	private String company;
    private String city;
    private String country;
    private String phone1;
    private String phone2;
    private String email;
    private LocalDate subscriptionDate;
    private String website;
}
