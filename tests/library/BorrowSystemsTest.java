/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.Calendar;
import java.util.Date;
import library.daos.BookDAO;
import library.daos.BookHelper;
import library.daos.LoanHelper;
import library.daos.LoanMapDAO;
import library.daos.MemberHelper;
import library.daos.MemberMapDAO;
import library.hardware.CardReader;
import library.hardware.Display;
import library.hardware.Printer;
import library.hardware.Scanner;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

/**
 *
 * @author Brett Smith
 */
public class BorrowSystemsTest {
    private Display display;
    private CardReader reader;
    private Scanner scanner;
    private Printer printer;
    private IBookDAO bookDAO;
    private ILoanDAO loanDAO;
    private IMemberDAO memberDAO;
    
    
    public static void main(String[] args)
    {
        BorrowSystemsTest borrowSystemsTest = new BorrowSystemsTest();
        System.out.println("Test Result: " + borrowSystemsTest.runTests());
    }
    
    public BorrowSystemsTest()
    {
        setupTestData();
        setupAdditionalTestData();
    }
    
    public boolean runTests()
    {
        // At this point, need to run systems tests.
        return false;
    }
    
    private void setupTestData() {
        reader = new CardReader();
        scanner = new Scanner();
        printer = new Printer();
        display = new Display();
        
        bookDAO = new BookDAO(new BookHelper());
        loanDAO = new LoanMapDAO(new LoanHelper());
        memberDAO = new MemberMapDAO(new MemberHelper());
        
        IBook[] book = new IBook[15];
        IMember[] member = new IMember[6];

        book[0]  = bookDAO.addBook("author1", "title1", "callNo1");
        book[1]  = bookDAO.addBook("author1", "title2", "callNo2");
        book[2]  = bookDAO.addBook("author1", "title3", "callNo3");
        book[3]  = bookDAO.addBook("author1", "title4", "callNo4");
        book[4]  = bookDAO.addBook("author2", "title5", "callNo5");
        book[5]  = bookDAO.addBook("author2", "title6", "callNo6");
        book[6]  = bookDAO.addBook("author2", "title7", "callNo7");
        book[7]  = bookDAO.addBook("author2", "title8", "callNo8");
        book[8]  = bookDAO.addBook("author3", "title9", "callNo9");
        book[9]  = bookDAO.addBook("author3", "title10", "callNo10");
        book[10] = bookDAO.addBook("author4", "title11", "callNo11");
        book[11] = bookDAO.addBook("author4", "title12", "callNo12");
        book[12] = bookDAO.addBook("author5", "title13", "callNo13");
        book[13] = bookDAO.addBook("author5", "title14", "callNo14");
        book[14] = bookDAO.addBook("author5", "title15", "callNo15");

        member[0] = memberDAO.addMember("fName0", "lName0", "0001", "email0");
        member[1] = memberDAO.addMember("fName1", "lName1", "0002", "email1");
        member[2] = memberDAO.addMember("fName2", "lName2", "0003", "email2");
        member[3] = memberDAO.addMember("fName3", "lName3", "0004", "email3");
        member[4] = memberDAO.addMember("fName4", "lName4", "0005", "email4");
        member[5] = memberDAO.addMember("fName5", "lName5", "0006", "email5");

        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();

        //create a member with overdue loans		
        for (int i=0; i<2; i++) {
                ILoan loan = loanDAO.createLoan(member[1], book[i]);
                loanDAO.commitLoan(loan);
        }
        cal.setTime(now);
        cal.add(Calendar.DATE, ILoan.LOAN_PERIOD + 1);
        Date checkDate = cal.getTime();		
        loanDAO.updateOverDueStatus(checkDate);

        //create a member with maxed out unpaid fines
        member[2].addFine(10.0f);

        //create a member with maxed out loans
        for (int i=2; i<7; i++) {
                ILoan loan = loanDAO.createLoan(member[3], book[i]);
                loanDAO.commitLoan(loan);
        }

        //a member with a fine, but not over the limit
        member[4].addFine(5.0f);

        //a member with a couple of loans but not over the limit
        for (int i=7; i<9; i++) {
                ILoan loan = loanDAO.createLoan(member[5], book[i]);
                loanDAO.commitLoan(loan);
        }
    }
    
    private void setupAdditionalTestData()
    {      
        memberDAO.addMember("fName0", "lName0", "0001", "email0"); //Member 7
        memberDAO.addMember("fName0", "lName0", "0001", "email0"); //Member 8
        memberDAO.addMember("fName0", "lName0", "0001", "email0"); //Member 9
        bookDAO.addBook("author5", "title15", "callNo16");
        bookDAO.addBook("author5", "title15", "callNo17");
        bookDAO.addBook("author5", "title15", "callNo18");
        bookDAO.addBook("author5", "title15", "callNo19");
        bookDAO.addBook("author5", "title15", "callNo20");
        bookDAO.addBook("author5", "title15", "callNo21");
        bookDAO.addBook("author5", "title15", "callNo22");
    }
}