package org.lsmr.selfcheckout.software.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.software.ItemInfo;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSetup;
import org.lsmr.selfcheckout.software.SelfCheckoutStationSoftware;
import org.lsmr.selfcheckout.software.gui.AttendantGui;

public class AttendantGuiTest {
	private AttendantGui gui;

	@Before
	public void setUp() throws Exception {
		gui = new AttendantGui();
	}

	@Test
	public void testInitialState() {
		assertEquals("Incorrect number of stations created.", gui.getNumberOfStations(), AttendantGui.NUM_STATIONS);

		for (int i = 0; i < AttendantGui.NUM_STATIONS; i++) {
			SelfCheckoutStationSoftware s = gui.getSoftware(i);

			// Ensure we start off shutdown
			assertTrue("Station did not start off shutdown.", s.isShutdown);
			assertTrue("Station did not start off blocked.", s.isBlocked);
		}
	}

	@Test
	public void testUnblockStation() {
		SelfCheckoutStationSoftware s = gui.getSoftware(0);
		s.isShutdown = false;
		s.isBlocked = true;
		gui.jButtonRefresh.doClick();

		gui.jListStations.setSelectedIndex(0);
		gui.jButtonUnblockStation.doClick();

		assertFalse("Station still blocked.", s.isBlocked);
	}

	@Test
	public void testBlockStation() {
		SelfCheckoutStationSoftware s = gui.getSoftware(0);
		s.isShutdown = false;
		s.isBlocked = false;
		gui.jButtonRefresh.doClick();

		gui.jListStations.setSelectedIndex(0);
		gui.jButtonBlockStation.doClick();

		assertTrue("Station still unblocked.", s.isBlocked);
	}

	@Test
	public void testShutdown() {
		SelfCheckoutStationSoftware s = gui.getSoftware(0);
		s.isShutdown = false;
		gui.jButtonRefresh.doClick();

		gui.jListStations.setSelectedIndex(0);
		gui.jButtonShutdown.doClick();

		assertTrue("Station still on.", s.isShutdown);
	}

	@Test
	public void testStartup() {
		SelfCheckoutStationSoftware s = gui.getSoftware(0);
		s.isShutdown = true;

		gui.jListStations.setSelectedIndex(0);
		gui.jButtonStartUp.doClick();

		assertFalse("Station still shut down", s.isShutdown);
	}

	@Test
	public void testRefillBanknoteDispenser() {
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);

		s.scs.banknoteDispensers.get(5).unload();

		gui.jListBanknoteDispensers.setSelectedIndex(0);
		gui.jButtonRefillBanknoteDispenser.doClick();

		assertEquals("Banknotes not refilled.", SelfCheckoutStation.BANKNOTE_DISPENSER_CAPACITY,
				s.funds.getBanknoteCount(5));
	}

	@Test
	public void testRefillCoinDispenser() {
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);

		BigDecimal d = new BigDecimal("0.05");
		s.scs.coinDispensers.get(d).unload();

		gui.jListCoinDispensers.setSelectedIndex(0);
		gui.jButtonRefillCoinDispenser.doClick();

		assertEquals("Coins not refilled.", SelfCheckoutStation.COIN_DISPENSER_CAPACITY, s.funds.getCoinCount(d));
	}

	@Test
	public void testRefillInk() {
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		
		assertEquals("Ink should initially be empty.", 0, s.getPercentageInkLeft());

		gui.jButtonRefillInk.doClick();

		assertEquals("Ink not refilled.", 100, s.getPercentageInkLeft());
	}
	
	@Test
	public void testRefillPaper() {
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		
		assertEquals("Paper should initially be empty.", 0, s.getPercentagePaperLeft());

		gui.jButtonRefillPaper.doClick();

		assertEquals("Paper not refilled.", 100, s.getPercentagePaperLeft());
	}
	
	@Test
	public void testUnloadBanknoteStorage() {
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		
		// Fill up the banknote storage
		Banknote bn = new Banknote(SelfCheckoutStationSetup.currency, 5);
		while(!s.funds.getIsBanknoteStorageFull())
			try {
				s.scs.banknoteStorage.accept(bn);
			} catch (DisabledException | OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		gui.jButtonRefresh.doClick();
		gui.jButtonUnloadBanknoteStorage.doClick();
		
		assertFalse("Coin storage not unloaded.", s.funds.getIsBanknoteStorageFull());
	}
	
	@Test
	public void testUnloadCoinStorage() {
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		
		// Fill up the coin storage
		Coin c = new Coin(SelfCheckoutStationSetup.currency, SelfCheckoutStationSetup.coinDenoms[0]);
		while(!s.funds.getIsCoinStorageFull())
			try {
				s.scs.coinStorage.accept(c);
			} catch (DisabledException | OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		gui.jButtonRefresh.doClick();
		gui.jButtonUnloadCoinStorage.doClick();
		
		assertFalse("Coin storage not unloaded.", s.funds.getIsCoinStorageFull());
	}
	
	@Test
	public void testStartUpAll() {
		// Have one station already started up to see that nothing weird happens
		SelfCheckoutStationSoftware _s = getAndSetUpSoftware(0);
		
		gui.jMenuItemMBStartUpAll.doClick();
		
		for (int i = 0; i < AttendantGui.NUM_STATIONS; i++) {
			SelfCheckoutStationSoftware s = gui.getSoftware(i);

			// Ensure we start off shutdown
			assertFalse("Station " + i + " did not start up.", s.isShutdown);
		}
	}
	
	@Test
	public void testShutDownAll() {
		// Shut down one station
		SelfCheckoutStationSoftware _s = getAndSetUpSoftware(0);
		_s.isShutdown = true;
		
		gui.jMenuItemMBStartUpAll.doClick();
		gui.jMenuItemMBShutDownAll.doClick();
		
		for (int i = 0; i < AttendantGui.NUM_STATIONS; i++) {
			SelfCheckoutStationSoftware s = gui.getSoftware(i);

			// Ensure we start off shutdown
			assertTrue("Station " + i + " did not shut down.", s.isShutdown);
		}
	}
	
	@Test
	public void testTryGetInvalidStation() {
		int invalidIdx = AttendantGui.NUM_STATIONS;
		
		Object s = gui.getSoftware(invalidIdx);
		assertNull("Station too large id should be null.", s);
		
		s = gui.getSoftware(-100);
		assertNull("Station with negative id should be null.", s);
	}
	
	@Test
	public void testStationShoppingList() {
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		s.itemsScanned.add(new ItemInfo(new BigDecimal("9.99"), 1.00, "Walnuts"));
		
		gui.jButtonRefresh.doClick();
		
		assertEquals("Item not put in shopping cart list", 1, gui.jListShoppingCart.getModel().getSize());
	}
	
	@Test
	public void testStationInWarningMode() {
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		s.status = SelfCheckoutStationSoftware.SCSStatus.WARNING;
		gui.jButtonRefresh.doClick();
		gui.jListStations.updateUI();
		
		String t = gui.jLabelStatusCode.getText();
		assertEquals("Station warning not being shown.", "WARNING", t);
		
	}
	
	@Test
	public void testAttendantRemoveItem() {
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		s.itemsScanned.add(new ItemInfo(new BigDecimal("9.99"), 1.00, "Walnuts"));
		
		gui.jButtonRefresh.doClick();
		gui.jListShoppingCart.setSelectedIndex(0);
		gui.jMenuItemSCRemoveItem.doClick();
		
		assertEquals("Item not removed from shopping cart.", 0, gui.jListShoppingCart.getModel().getSize());
	}
	
	@Test
	public void testAttendantRemoveNoItem() {
		// Test that nothing is removed when no item is selected to be removed
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		s.itemsScanned.add(new ItemInfo(new BigDecimal("9.99"), 1.00, "Walnuts"));
		
		gui.jButtonRefresh.doClick();
		gui.jListShoppingCart.setSelectedIndex(-1);
		gui.jMenuItemSCRemoveItem.doClick();
		
		assertEquals("Item not removed from shopping cart.", 1, gui.jListShoppingCart.getModel().getSize());
	}
	
	@Test
	public void testNoBanknoteDispenserRefill() {
		// Test that none of the banknote dispensers are refilled when none are selected to be refilled.
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		
		int[] denoms = SelfCheckoutStationSetup.banknoteDenoms;
		int idx = 0;
		for(int d : denoms) {
			s.scs.banknoteDispensers.get(d).unload();

			gui.jListBanknoteDispensers.setSelectedIndex(idx++);
			gui.jButtonRefillBanknoteDispenser.doClick();

			assertEquals("Banknote " + d + " not refilled.", SelfCheckoutStation.BANKNOTE_DISPENSER_CAPACITY,
					s.funds.getBanknoteCount(d));
		}
	}
	
	@Test
	public void testNoCoinDispenserRefill() {
		// Test that none of the banknote dispensers are refilled when none are selected to be refilled.
		SelfCheckoutStationSoftware s = getAndSetUpSoftware(0);
		
		BigDecimal[] denoms = SelfCheckoutStationSetup.coinDenoms;
		int idx = 0;
		for(BigDecimal d : denoms) {
			s.scs.coinDispensers.get(d).unload();

			gui.jListCoinDispensers.setSelectedIndex(idx++);
			gui.jButtonRefillCoinDispenser.doClick();

			assertEquals("Coin " + d + " not refilled.", SelfCheckoutStation.COIN_DISPENSER_CAPACITY,
					s.funds.getCoinCount(d));
		}
	}

	private SelfCheckoutStationSoftware getAndSetUpSoftware(int index) {
		SelfCheckoutStationSoftware s = gui.getSoftware(index);
		gui.jListStations.setSelectedIndex(index);
		gui.jButtonStartUp.doClick();
		gui.jButtonUnblockStation.doClick();
		gui.jButtonRefresh.doClick();

		return s;
	}
}
