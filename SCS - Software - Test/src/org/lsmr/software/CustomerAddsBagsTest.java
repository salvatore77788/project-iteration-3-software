package org.lsmr.software;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;


public class CustomerAddsBagsTest {
        @Test
        public void testAddCustomerBagByWeight() throws OverloadException {
                SelfCheckoutStation testStation = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
                CustomerAddsBags testCustomerAddsBags = new CustomerAddsBags(testStation);
                double normalBagWeight = 10;
                Barcode testBarcode = new Barcode(new Numeral[] {Numeral.nine, Numeral.nine, Numeral.nine, Numeral.nine});
                Item testBag = new BarcodedItem(testBarcode, normalBagWeight);

                testCustomerAddsBags.placeStoreBag(testBag);
                Assert.assertTrue(normalBagWeight >= (testStation.baggingArea.getCurrentWeight() - 0.1));
        }

        @Test
        public void testAddBagByListContents() throws OverloadException {
                SelfCheckoutStation testStation = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
                CustomerAddsBags testCustomerAddsBags = new CustomerAddsBags(testStation);

                double normalBagWeight = 10;
                Barcode testBarcode = new Barcode(new Numeral[] {Numeral.nine, Numeral.nine, Numeral.nine, Numeral.nine});
                Item testBag = new BarcodedItem(testBarcode, normalBagWeight);

                testCustomerAddsBags.customerAddsTheirOwnBag(testBag);

                ArrayList<Item> expectedList = new ArrayList<>();
                expectedList.add(testBag);


                ArrayList<Item> actualList = testCustomerAddsBags.bagList;
                assertEquals(actualList, expectedList);
        }

        @Test (expected = OverloadException.class)
        public void testBagIsTooHeavy() throws OverloadException {
                SelfCheckoutStation testStation = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
                CustomerAddsBags testCustomerAddsBags = new CustomerAddsBags(testStation);

                double heavyWeight = 5000;
                Barcode testBarcode = new Barcode(new Numeral[] {Numeral.nine, Numeral.nine, Numeral.nine, Numeral.nine});
                Item testBag = new BarcodedItem(testBarcode, heavyWeight);

                testCustomerAddsBags.customerAddsTheirOwnBag(testBag);
        }

        @Test
        public void testRemoveBagSuccess() throws OverloadException {
                SelfCheckoutStation testStation = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
                CustomerAddsBags testCustomerAddsBags = new CustomerAddsBags(testStation);

                double normalBagWeight = 10;
                Barcode testBarcode = new Barcode(new Numeral[] {Numeral.nine, Numeral.nine, Numeral.nine, Numeral.nine});
                Item testBag = new BarcodedItem(testBarcode, normalBagWeight);

                testCustomerAddsBags.customerAddsTheirOwnBag(testBag);

                Assert.assertTrue(testCustomerAddsBags.removeBag(testBag));
        }

        @Test
        public void testRemoveBagFailure() throws OverloadException {
                SelfCheckoutStation testStation = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
                CustomerAddsBags testCustomerAddsBags = new CustomerAddsBags(testStation);

                double normalBagWeight = 10;
                Barcode testBarcode = new Barcode(new Numeral[] {Numeral.nine, Numeral.nine, Numeral.nine, Numeral.nine});
                Item testBag = new BarcodedItem(testBarcode, normalBagWeight);

                Assert.assertFalse(testCustomerAddsBags.removeBag(testBag));
        }

        @Test
        public void testGetAttendantPermission() {
                SelfCheckoutStation testStation = SelfCheckoutStationSetup.createSelfCheckoutStationFromInit();
                CustomerAddsBags testCustomerAddsBags = new CustomerAddsBags(testStation);

                double normalBagWeight = 10;
                Barcode testBarcode = new Barcode(new Numeral[] {Numeral.nine, Numeral.nine, Numeral.nine, Numeral.nine});
                Item testBag = new BarcodedItem(testBarcode, normalBagWeight);

                Assert.assertTrue(testCustomerAddsBags.getAttendantPermission(testBag));
        }
 }
