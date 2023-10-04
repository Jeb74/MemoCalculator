package org.mccode;

import org.mccode.graphic.Display;
import org.mccode.math.Calculator;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        double v = 3_142_062 + 6_466_436 + 2_003_840 + 3_590_155 + 3_435_781 + 2_354_294 + 2_136_624 + 3_229_264 + 4_259_221;
        Double[] arr = {v};
        Calculator c = new Calculator();
        System.out.println(c.execute("y - y / 10", arr).intValue());
        /*
        setLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            Display window = new Display();
            window.setVisible(true);
        });
        */
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
        } catch(Exception ignored) {}
    }
}