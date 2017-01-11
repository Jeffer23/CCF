package com.ccf.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.ccf.dao.FamilyDao;
import com.ccf.dao.MemberDao;
import com.ccf.dao.impl.FamilyDaoImpl;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Family;
import com.ccf.persistence.classes.Member;

public class UploadDataFromSpreedSheet {

	public static void main(String[] args) {

		try (InputStream iStream = new FileInputStream(
				"C:/Users/Jeffer/Downloads/MEM SUBN.xls");) {
			HSSFWorkbook workbook = new HSSFWorkbook(iStream);
			HSSFSheet sheet = workbook.getSheet("Sheet1");

			MemberDao dao = new MemberDaoImpl();
			Family family = new Family();
			boolean notFirstTime = false;
			for (int r = 1; r < 902; r++) {
				Row row = sheet.getRow(r);
				Cell B = row.getCell(1);
				Cell C = row.getCell(2);
				Cell D = row.getCell(3);
				Cell E = row.getCell(4);
				Cell F = row.getCell(5);
				Cell G = row.getCell(6);
				Cell H = row.getCell(7);
				Cell I = row.getCell(8);
				Cell J = row.getCell(9);
				Cell K = row.getCell(10);
				Cell L = row.getCell(11);
				Cell M = row.getCell(12);
				Cell N = row.getCell(13);

				float subn = 0;
				float missionary = 0;
				float MF = 0;
				float WF = 0;
				float education = 0;
				float SundaySchool = 0;
				float youth = 0;
				float poor = 0;
				float church = 0;
				float graveyard = 0;
				float total = 0;

				if (D == null)
					subn = 0;
				else
					subn = Float.parseFloat(D.toString());
				if (E == null)
					missionary = 0;
				else
					missionary = Float.parseFloat(E.toString());
				if (F == null)
					MF = 0;
				else
					MF = Float.parseFloat(F.toString());
				if (G == null)
					WF = 0;
				else
					WF = Float.parseFloat(G.toString());
				if (H == null)
					education = 0;
				else
					education = Float.parseFloat(H.toString());
				if (I == null)
					SundaySchool = 0;
				else
					SundaySchool = Float.parseFloat(I.toString());
				if (J == null)
					youth = 0;
				else
					youth = Float.parseFloat(J.toString());
				if (K == null)
					poor = 0;
				else
					poor = Float.parseFloat(K.toString());
				if (L == null)
					church = 0;
				else
					church = Float.parseFloat(L.toString());
				if (M == null)
					graveyard = 0;
				else
					graveyard = Float.parseFloat(M.toString());
				if (N == null)
					total = 0;
				else
					total = subn + missionary + MF + WF + education
							+ SundaySchool + youth + poor + church + graveyard;

			
					if (B != null) {
						System.out.println("New Family");
						/*
						 * Persist previous Family Object
						 */
						/*if (notFirstTime)
							dao.saveFamily(family);
						notFirstTime = true;*/
						
						/*
						 * New Family creation
						 */
						family = new Family();
						family.setNo((int)Float.parseFloat(B.toString()));
						family.setAddress("No Record Found - " + family.getNo());
						family.setPhoneNo(family.getNo());

						Member member = new Member();
						member.setEligibility("Yes");
						member.setFamily(family);
						member.setName(C.toString());
						family.getMembers().add(member);
						
						dao.addMember(member);

					} else {
						System.out.println("New member to Existing Family");
						Member member = new Member();
						member.setEligibility("Yes");
						member.setFamily(family);
						member.setName(C.toString());
						family.getMembers().add(member);
						
						dao.addMember(member);
					}
				
				System.out.println(B + " " + C + " " + D + " " + missionary
						+ " " + MF + " " + WF + " " + education + " "
						+ SundaySchool + " " + youth + " " + poor + " "
						+ church + " " + graveyard + " " + total);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CcfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
