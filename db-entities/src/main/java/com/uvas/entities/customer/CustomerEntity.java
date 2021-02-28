package com.uvas.entities.customer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uvas.pojos.customer.CustomerTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity extends AbstractTimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sid")
	private long sid;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "ADDR_LINE_1")
	private String addressline1;

	@Column(name = "ADDR_LINE_2")
	private String addressline2;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "CUSTOMER_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private CustomerTypeEnum customerType;

	@Column(name = "MSG_CREATED_DATE", nullable = false)
	private LocalDateTime msgCreateDateTime;

	@Column(name = "ENROLL_DATE")
	private LocalDate enrollDate;

	@Column(name = "EVENT_PAYLOAD", columnDefinition = "text")
	private String payload;

}
