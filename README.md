# SENG 300 Self Checkout Station Final Iteration 

## Table of Contents
- [Description](#Description)
- [Requirements](#Requirements)
- [Understanding the file structure](#understanding-the-file-structure)
- [Authors](#Authors)
- [License](#License)


## Description
This project was made by Computer Science Undergraduate students from the University of Calgary
for the SENG 300 Winter 2022 Introduction to Software Engineering course.


## Requirements:
1. Java 17 JDK
2. JUnit 4

### Recommended IDE:
1. Eclipse 2021-12


## Understanding the file structure
* The repository contains 3 main folders (these are 3 Eclipse projects):
    + SCS - Hardware - v3.1
        + This is a project that contains a simulation of hardware for a self checkout station. Written by Dr. Walker.
    + SCS - Software 
        + This project contains all the software and gui classes that interacts with the hardware interface.
        + The class that ties most of the software together is the class named **"SelfCheckoutStationSoftware.java"**
        + In order to run our Graphical User Interface please run **SelfCheckoutStationSoftwareGUI.java**
        * Our Graphical User Interface implements the following usecases:
            + Scanning Items
                + RemoveItemPromptGUI
                + StationItemGUI
                + RemoveItemGUI
                + ScanLargeItemGUI
                + WrongWeightGUI
            + Checkout
                + SelfCheckoutStationSoftwareGUI
                + VirtualKeypad
                + BagsGUI
                + PersonalBagPromptGUI
                + TakeBagPromptGUI
            + Customer makes a Payment
                + EnterMemberCard
                + PaymentGUI
                + PaymentTesterGUI
                + PaymentCompleteGUI
            + Attendant Panel
                + Login
                + FailedLogin
                + AttendantRemoveItemGUI
                + AttendantApproveLargeItemGUI
                + RequestAssistanceGUI
    + SCS - Software - Test
        + This project contains all the JUnit 4 tests for our software.
         + The class that combines most of the usecases togeher is called **"Testing.java"**. This class is simulating the SelfCheckoutStation through command prompt.

            + BaggingAreaSofwareTest.java
                + This tests our implementation for SelfCheckoutStationSoftware.
                + Bagging area software is inside the SelfCheckoutStationSoftware class.
                + This testing for "customer failing adding items to bagging area.
                + Due to random probility of scanning an item fails, %10 of the time this test will result in failing.

            + BarcodeScannerSoftwareTest.java
                + This tests our implementation of the BarcodeScannerObserver interface.

            + BanknoteSlotTest.java
                + This tests our implementation for BanknoteSlotSoftware.
                + This testing checks any payments with banknotes.

            + CardTest.java
                + This tests our implementation for CardSoftware (payment with credit, debit, and gift card).
                + This testing provides %100 coverage however due to random probility of swipe and tap features failing, sometimes the testing gives error. 
                + The random probility of error is about 10% of the time.

            + CoinSlotSoftwareTest.java
                + This tests our implementation for CoinSlotSoftware
                + This testing checks any payments with coins.

            + ElectronicScaleSoftwareTest.java
                + This tests our implementation of the ElectronicScaleObserver interface.

            + EnterMembershipCardTest.java
                + This tests our implementation for ScanMembershipCard.
                + This testing provides %100 coverage however due to random probility of swipe and tap features failing, sometimes the testing gives error. 
                + The random probility of error is about 10% of the time.

            + PartialPaymentTest.java
                + This tests our implementation for PartialPaymentSoftware.
                + This testing is for handling the partial payments.

            + ReceiptPrintTest.java
                + This tests our implementation of the ReceiptPrint.
                + This testing our implementation of the ink and paper.

            + ReturnChangeTest.java
                + This tests our implementation for ReturnChangeSoftware. 
                + This tests our implementation for "how many bags did you use?"
                + If using it as a testing unit only, then please put in a "0" for all cases, otherwise program will not run!
                + If using it as a program, then any number of bag input works
                 
            + TestHardware.java
                + This tests our implementation for TestHardware.

            + Testing.java
                + This tests our implementation for
                + This provides a simulation of our software through command prompt, make sure to enter your specified number of bags to run this program.

            + TestItems.java
                + This test is for our Barcoded Items.
                + It tests each item we have in the shopping cart.

            + TouchScreenTest.java
                + This tests our implementation for TouchScreenSoftware. 
                + This testing is for "how should adding bags communicate with electronic scale and the station.".

## Downloading the project:
* Easiest way is to clone the repository:
    + [SENG 300 Self Checkout Station Final Iteration ](https://github.com/salvatore77788/project-iteration-3-software)
        + You will need to be added to the repository as it is a private repository.
    + Alternatively, if you're a TA or Professor, you need to move our code into a file system where you have permissions to run and compile code.

## Compiling the code
1. Using Eclipse:
    1. File
    2. Import
    3. Existing Projects into Workspace (in the general dropdown) 
    4. Click browse and select the folder that *contains the 3 projects Eclipse* 
    5. You should see the 3 projects listed above, select all 3 projects.
    6. Click finish.
        * In your project explorer, you should see all 3 projects that we listed above.
    7. Expand SCS - Software - Test -> src -> org.lsmr.selfcheckout.software.test
        * Inside this package you have all the tests and running code.
        * Choose your class you would like to run and click the run button on the top tool bar of eclipse.
            + Note: You will need JUnit 4 installed and you may need to fix your project setup so that it includes JUnit 4.


## AUTHORS:
* Emir Avci
* Quyanna Campbell
* Doowen Cho
* MD AZHARUL ISLAM FAHIM
* Jordan Humenjuk
* James Khalil
* Alisha Lalani
* Paul Latkovic
* Raine Legary
* Guillaume Pluta
* Vincent Salvatore
* Anson Sieu
* Sean Stacey
* Jordan Tran
* Carlos Veintimilla
* Mitchell Wilson
* Chirag Asrani
* Cebrail Durna

## LICENSE
[MIT](https://github.com/salvatore77788/project-iteration-3-software/blob/main/LICENSE)
