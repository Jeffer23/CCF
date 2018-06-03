package com.ccf.thread;

import org.apache.log4j.Logger;

import com.ccf.dao.AccountsDao;
import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.exception.CcfException;

public class BalanceUpdateThread implements Runnable {
	final static Logger logger = Logger.getLogger(BalanceUpdateThread.class);
	Class entity;
	
	public BalanceUpdateThread(Class entity){
		this.entity = entity;
	}

	@Override
	public void run() {
		logger.info("Updating balances for " + entity.getName() +" Starts..");
		logger.info("Running..." + Thread.currentThread().getName());
		AccountsDao impl = new AccountsDaoImpl();
		
		try {
			impl.updateBalance(entity);
		} catch (CcfException e) {
			e.printStackTrace();
		}
		
		logger.info("Updating balances for " + entity.getName() +" Ends..");
	}

}
