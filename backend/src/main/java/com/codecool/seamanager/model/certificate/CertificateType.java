package com.codecool.seamanager.model.certificate;

public enum CertificateType {
	PASSPORT("Passport"),
	COC("Certificate Of Competency"),
	SEAMANS_BOOK("Seaman's Book"),
	FLAG_SEAMANS_BOOK("Flag Seaman's Book"),
	MEDICAL("Medical Certificate"),
	US_VISA("US Visa"),
	AUS_VISA("Australian Visa"),
	VACCINATION_BOOK("Vaccination Book"),
	NATIONAL_ENDORSEMENT("National Endorsement"),
	FLAG_ENDORSEMENT("Flag Endorsement"),
	DC_ENDORSEMENT("Dangerous Cargo Endorsement"),
	BT("IMO Basic Training"),
	AFF("Advanced Fire Fighting"),
	MFA("Medical First Aid"),
	MEFA("Medical Emergency First Aid"),
	PSCRB("Proficiency In Survival Craft And Rescue Boats"),
	GMDSS("GMDSS"),
	SSD("Security Awareness For Seafarers With Designated Security Duties"),
	BTRM("Bridge Team And Resource Management"),
	LPG_BT("LPG Familiarisation"),
	LPG_ADV("LPG Advanced Training"),
	OIL_BT("Oil Familiarisation"),
	OIL_ADV("Oil Advanced Training"),
	SSO("Ship Security Officer"),
	HV("High Voltage"),
	FOOD_HANDLING("Food Handling and Hygiene"),
	HELM("Leadership and Management Training, such as Human Element, Leadership, and Management"),
	RAC("Refrigeration and Air Conditioning"),
	ARPA("Automatic Radar Plotting Aid"),
	ERM("Engine Room Resource Management"),
	ECDIS("Electronic Chart Display Information System");

	private final String description;

	CertificateType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
