/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Brett.Smith
 */
public class BorrowUC_CTLTest {
    
    public BorrowUC_CTLTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of initialise method, of class BorrowUC_CTL.
     */
    @Test
    public void testInitialise() {
        System.out.println("initialise");
        BorrowUC_CTL instance = null;
        instance.initialise();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of close method, of class BorrowUC_CTL.
     */
    @Test
    public void testClose() {
        System.out.println("close");
        BorrowUC_CTL instance = null;
        instance.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cardSwiped method, of class BorrowUC_CTL.
     */
    @Test
    public void testCardSwiped() {
        System.out.println("cardSwiped");
        int memberID = 0;
        BorrowUC_CTL instance = null;
        instance.cardSwiped(memberID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of bookScanned method, of class BorrowUC_CTL.
     */
    @Test
    public void testBookScanned() {
        System.out.println("bookScanned");
        int barcode = 0;
        BorrowUC_CTL instance = null;
        instance.bookScanned(barcode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelled method, of class BorrowUC_CTL.
     */
    @Test
    public void testCancelled() {
        System.out.println("cancelled");
        BorrowUC_CTL instance = null;
        instance.cancelled();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scansCompleted method, of class BorrowUC_CTL.
     */
    @Test
    public void testScansCompleted() {
        System.out.println("scansCompleted");
        BorrowUC_CTL instance = null;
        instance.scansCompleted();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loansConfirmed method, of class BorrowUC_CTL.
     */
    @Test
    public void testLoansConfirmed() {
        System.out.println("loansConfirmed");
        BorrowUC_CTL instance = null;
        instance.loansConfirmed();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loansRejected method, of class BorrowUC_CTL.
     */
    @Test
    public void testLoansRejected() {
        System.out.println("loansRejected");
        BorrowUC_CTL instance = null;
        instance.loansRejected();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
