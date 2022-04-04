package org.lsmr.selfcheckout.software;

/**
 * This is an imaginary touch screen/display/GUI where we can show output
 * also we can show option for process, etc
 */
public class TouchScreen {

    public static void invalidMembershipCardMessage(){
        System.out.println("The scanned card is not a membership card, please try again.");
    }

    public static void invalidCardMessage(){
        System.out.println("The scanned card is not valid, please try again.");
    }

    // show message for card's invalid pin
    public static void invalidPinMessage() {
        System.out.println("Given pin is invalid.");
    }
}
