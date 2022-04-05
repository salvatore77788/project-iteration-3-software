package org.lsmr.selfcheckout.software.Listeners;

import org.lsmr.selfcheckout.devices.ReceiptPrinter;

public interface ReceiptPrintListener {

	void noPaper(ReceiptPrinter printer);

	void noInk(ReceiptPrinter printer);

	void paperAdded(ReceiptPrinter printer);

	void inkAdded(ReceiptPrinter printer);

	void inkLow(ReceiptPrinter printer);

	void paperLow(ReceiptPrinter printer);
}
