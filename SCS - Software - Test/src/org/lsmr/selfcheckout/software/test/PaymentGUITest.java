package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSetup;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;
import org.lsmr.selfcheckout.software.gui.PaymentGUI;
import org.lsmr.selfcheckout.software.gui.PaymentGUI.PaymentGUIState;

public class PaymentGUITest {
	PaymentGUI gui;
	SelfCheckoutStationSoftware software;

	@Before
	public void setUp() throws Exception {
		software = new SelfCheckoutStationSoftware(SelfCheckoutStationSetup.createSelfCheckoutStationFromInit());
		gui = new PaymentGUI(software);
	}

	@Test
	public void testPayWithCash() {
		gui.jButtonPayCash.doClick();

		assertEquals("GUI state incorrect.", PaymentGUI.PaymentGUIState.CASH, gui.getState());
	}

	@Test
	public void testPayWithCredit() {
		gui.jButtonPayCredit.doClick();

		assertEquals("GUI state incorrect.", PaymentGUI.PaymentGUIState.CREDIT, gui.getState());
	}

	@Test
	public void testPayWithDebit() {
		gui.jButtonPayDebit.doClick();

		assertEquals("GUI state incorrect.", PaymentGUI.PaymentGUIState.DEBIT, gui.getState());
	}

	@Test
	public void testPayWithGift() {
		gui.jButtonPayGiftcard.doClick();

		assertEquals("GUI state incorrect.", PaymentGUI.PaymentGUIState.GIFT, gui.getState());
	}

	@Test
	public void testScanMembership() {
		gui.jButtonScanMembershipCard.doClick();

		assertEquals("GUI state incorrect.", PaymentGUI.PaymentGUIState.MEMBERSHIP, gui.getState());
	}

	@Test
	public void testReturnToSelection() {
		gui.jButtonPayCash.doClick();
		assertEquals("GUI state incorrect.", PaymentGUI.PaymentGUIState.CASH, gui.getState());

		gui.jButtonInfoScreenBack.doClick();
		assertEquals("GUI state incorrect.", PaymentGUI.PaymentGUIState.SELECTION, gui.getState());
	}

	@Test
	public void testPaymentDueUpdate() {
		BigDecimal newDue = new BigDecimal("500.00");
		software.amountDue = newDue;

		gui.updatePaymentLabels();

		String exp = "Amount Due: $" + newDue;
		assertEquals("Payment due label not updated.", exp, gui.jLabelAmountDue.getText());
	}

	@Test
	public void testPaymentPaidUpdate() {
		Coin c = new Coin(SelfCheckoutStationSetup.currency, SelfCheckoutStationSetup.coinDenoms[0]);

		try {
			gui.jButtonPayCash.doClick();
			software.scs.coinSlot.accept(c);

			gui.updatePaymentLabels();

			String exp = "Amount Paid: $" + c.getValue();
			assertEquals("Payment paid label not updated.", exp, gui.jLabelAmountPaid.getText());
		} catch (DisabledException | OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testPaymentBackToSelection() {
		software.amountDue = new BigDecimal("500.00");
		software.cardSoftware.paymentAmount = new BigDecimal("100.00");
		
		Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.YEAR, 5);
		Card d = new Card("debit", "395025822", "Test", "111", "1234", false, false);
		software.cardSoftware.cardIssuer.addCardData("395025822", "Test", expiry, "111",
				new BigDecimal("100.00"));

		gui.jButtonPayDebit.doClick();

		int i = 0;
		while (i++ < 5 && gui.getState() == PaymentGUIState.DEBIT)
			try {
				software.scs.cardReader.swipe(d);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		assertEquals("State incorrect.", PaymentGUIState.SELECTION, gui.getState());
	}

	@Test
	public void testScanMembershipCard() {
		Card m = new Card("member", "54398734", "Test", "111", "1234", false, false);
		software.membersRecord.members.put("54398734", "Test");
		

		gui.jButtonScanMembershipCard.doClick();

		int i = 0;
		while (i++ < 100 && !gui.membershipCardScanned)
			try {
				software.scs.cardReader.swipe(m);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		assertTrue("Membership card not scanned.", gui.membershipCardScanned);
		assertFalse("Scan membership button not disabled", gui.jButtonScanMembershipCard.isEnabled());
	}
}
