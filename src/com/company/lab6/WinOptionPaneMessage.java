package com.company.lab6;

import javax.swing.*;

/**
 * This method is just used for a pop-up window if the users gusses all the letters in the secretWord
 */

public class WinOptionPaneMessage {
    JFrame f;
    WinOptionPaneMessage(){
        f = new JFrame();
        JOptionPane.showMessageDialog(f, "WOW du klarade det, Grattis!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
