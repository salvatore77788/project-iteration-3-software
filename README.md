# SENG-300-Iteration2

## Requirements:
1. Java 17 JDK
2. JUnit 4

### Recommended IDE:
1. Eclipse 2021-12


## Understanding the file structure
* The repository contains 3 main folders (these are 3 Eclipse projects):
    + SCS - Hardware - v2.0
        + This is a project that contains a simulation of hardware for a self checkout station. Written by Dr. Walker.
    + SCS - Software 
        + This project contains all the software classes that interacts with the hardware interface.
        + The class that ties most of the software together is the class named "SelfCheckoutStationSoftware.java"
    + SCS - Software - Test
        + This project contains all the JUnit 4 tests for our software.
            + BarcodeScannerSoftwareTest.java
                + This tests our implementation of the BarcodeScannerObserver interface.
            + ElectronicScaleSoftwareTest.java
                + This tests our implementation of the ElectronicScaleObserver interface.
            + CreditCardTest.java
                + This test our implementation for CreditCardSoftware.
                + This testing provides %100 coverage however due to random probility of swipe and tap features failing, sometimes the testing gives error. 
                + This random probility of error is about 10% of the time.
            + DebitCardTest.java
                + This test our implementation for DebitCardSoftware.
                + This testing provides %100 coverage however due to random probility of swipe and tap features failing, sometimes the testing gives error. 
                + This random probility of error is about 10% of the time.
            + ReturnChangeTest.java
                + This test our implementation for ReturnChangeSoftware. 
                + This test our implementation for "how many bags did you use?"
                + If using it as a testing unit only, then please put in a "0" for all cases
                + If using it as a program, then any number of bag input works
            + ScanMembershipCardTest.java
                + This test our implementation for ScanMembershipCard.
                + This testing provides %100 coverage however due to random probility of swipe and tap features failing, sometimes the testing gives error. 
                + This random probility of error is about 10% of the time.
            + TouchScreenTest.java
                + This test our implementation for TouchScreenSoftware. 
                + This testing is for "how should adding baggs communicate with electronic scale". 
                + The touch screen does not exist yet however the user can still interact with the interface through terminal/command line. 
            + BaggingAreaSofwareTest.java
                + This test our implementation for SelfCheckoutStationSoftware.
                + Bagging area software is inside the SelfCheckoutStationSoftware class.
                + This testing for "customer failing adding items to bagging area.
                + Due to random probility of scanning an item fails, %10 of the time this test will result in failing.
            + PartialPaymentTest.java
                + This test our implementation for PartialPaymentSoftware.
                + This testing is for handling the partial payments.
            + Testing.java
                + This test our implementation for
                + This provides a simulation of our software, including the following functions:
                    + Printing receipt.
                    + Returning change.
            + TestItems.java
                + This test is for our Barcoded Items.
                + It tests each item we have in the shopping cart.

## Downloading the project:
* Easiest way is to clone the repository:
    + [SENG-300-Iteration2 GitHub](https://github.com/benjaminmesser/SENG300ProjectIteration2Group33)
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


##  Contributors:
* Parry Chirakorn
* Allison Grothman
* Emir Avci
* Jessica Hoang
* Jeremy Kimotho
* Kamrul Ahsan Noor
* Kanishka Roy