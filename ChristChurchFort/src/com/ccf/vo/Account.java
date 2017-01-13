package com.ccf.vo;

import java.util.Date;

public interface Account {

	void setAmount(float amount);
	void setDescription(String description);
	void setCr_dr(String cr_dr);
	void setDate(Date date);
	Date getDate();
	float getAmount();
	String getDescription();
	String getCr_dr();
}
