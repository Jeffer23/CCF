package com.ccf.vo;

import java.util.Date;

public interface Account {

	void setAmount(float amount);
	void setBalance(float balance);
	void setDescription(String description);
	void setCr_dr(String cr_dr);
	void setDate(Date date);
	Date getDate();
	float getAmount();
	float getBalance();
	String getDescription();
	String getCr_dr();
}
