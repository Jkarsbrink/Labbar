package com.company.lab6;

import javax.swing.*;

/**
 * This method is just used for a pop-up window if the user is out of turns.
 */
public class LooseOptionPaneMessage {
    JFrame f;
    LooseOptionPaneMessage(){
        f = new JFrame();
        JOptionPane.showMessageDialog(f,"HAHAHAH, DU förlorade");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
