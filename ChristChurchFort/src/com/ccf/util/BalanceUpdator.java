package com.ccf.util;

import com.ccf.persistence.classes.BankBuildingAccount;
import com.ccf.persistence.classes.BankEducationalFundAccount;
import com.ccf.persistence.classes.BankGraveyardAccount;
import com.ccf.persistence.classes.BankMensAccount;
import com.ccf.persistence.classes.BankMissionaryAccount;
import com.ccf.persistence.classes.BankPCAccount;
import com.ccf.persistence.classes.BankSundaySchoolAccount;
import com.ccf.persistence.classes.BankWomensAccount;
import com.ccf.persistence.classes.BankYouthAccount;
import com.ccf.persistence.classes.BuildingAccount;
import com.ccf.persistence.classes.EducationalFundAccount;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.thread.BalanceUpdateThread;

public class BalanceUpdator {

	private static BalanceUpdator balanceUpdator = new BalanceUpdator();
	private BalanceUpdator(){
		
	}
	
	public static BalanceUpdator getInstance(){
		return balanceUpdator;
	}
	
	public void updateAllBalances(){
		BalanceUpdateThread but = new BalanceUpdateThread(PCAccount.class);
		Thread pcThread = new Thread(but, "pcThread");
		pcThread.start();
		
		but = new BalanceUpdateThread(BankPCAccount.class);
		Thread bankPcThread = new Thread(but, "bankPcThread");
		bankPcThread.start();
		
		but = new BalanceUpdateThread(MissionaryAccount.class);
		Thread missionaryThread = new Thread(but, "missionaryThread");
		missionaryThread.start();
		
		but = new BalanceUpdateThread(BankMissionaryAccount.class);
		Thread bankMissThread = new Thread(but, "bankMissThread");
		bankMissThread.start();
		
		but = new BalanceUpdateThread(MensAccount.class);
		Thread mensThread = new Thread(but, "mensThread");
		mensThread.start();
		
		but = new BalanceUpdateThread(BankMensAccount.class);
		Thread bankMensThread = new Thread(but, "bankMensThread");
		bankMensThread.start();
		
		but = new BalanceUpdateThread(WomensAccount.class);
		Thread womensThread = new Thread(but, "womensThread");
		womensThread.start();
		
		but = new BalanceUpdateThread(BankWomensAccount.class);
		Thread bankWomensThread = new Thread(but, "bankWomensThread");
		bankWomensThread.start();
		
		but = new BalanceUpdateThread(SundaySchoolAccount.class);
		Thread ssaThread = new Thread(but, "Sunday School Thread");
		ssaThread.start();
		
		but = new BalanceUpdateThread(BankSundaySchoolAccount.class);
		Thread bankSsaThread = new Thread(but, "Bank Sunday School Thread");
		bankSsaThread.start();
		
		but = new BalanceUpdateThread(YouthAccount.class);
		Thread youthThread = new Thread(but, "youthThread");
		youthThread.start();
		
		but = new BalanceUpdateThread(BankYouthAccount.class);
		Thread bankYouthThread = new Thread(but,  "bankYouthThread");
		bankYouthThread.start();
		
		but = new BalanceUpdateThread(BuildingAccount.class);
		Thread buildingThread = new Thread(but, "buildingThread");
		buildingThread.start();
		
		but = new BalanceUpdateThread(BankBuildingAccount.class);
		Thread bankBuildingThread = new Thread(but, "bankBuildingThread");
		bankBuildingThread.start();
		
		but = new BalanceUpdateThread(GraveyardAccount.class);
		Thread graveyardThread = new Thread(but, "graveyardThread");
		graveyardThread.start();
		
		but = new BalanceUpdateThread(BankGraveyardAccount.class);
		Thread bankGraveyardThread = new Thread(but, "bankGraveyardThread");
		bankGraveyardThread.start();
		
		but = new BalanceUpdateThread(EducationalFundAccount.class);
		Thread educationalThread = new Thread(but, "educationalThread");
		educationalThread.start();
		
		but = new BalanceUpdateThread(BankEducationalFundAccount.class);
		Thread bankEducationalThread = new Thread(but, "bankEducationalThread");
		bankEducationalThread.start();
		
		
	}
}
