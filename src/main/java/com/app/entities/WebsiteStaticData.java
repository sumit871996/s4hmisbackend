package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "website_data")
public class WebsiteStaticData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long policyEntry;

	@Column
	private String privacyPolicy;
	@Column
	private String aboutUs;
	@Column
	private String contactUs;
	@Column
	private String ambulanceContact;
	@Column
	private String urgentTeleSupportContact;
	@Column
	private String mainLineContact;
	@Column
	private double hospitalFeedback;

}
