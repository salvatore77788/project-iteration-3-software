package org.lsmr.software;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CustomerScansItemTest.class, payWithBankNoteTest.class, payWithCoinTest.class, ProductDatabaseTest.class, ShoppingCartReceiptPrinterTest.class })
public class Iter2Tests {

}
