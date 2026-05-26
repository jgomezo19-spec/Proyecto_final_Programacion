package com.condominio.vistaverde.utils;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;

public class UIManagerConfig {
    
    private static boolean darkMode = false;
    
    public static void setupUI() {
        try {
            if (darkMode) {
                FlatMacDarkLaf.setup();
            } else {
                FlatMacLightLaf.setup();
            }
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 8);
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.showVerticalLines", true);
            UIManager.put("Table.rowHeight", 32);
            UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 13));
            UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 13));
            UIManager.put("TableHeader.background", new Color(0, 102, 204));
            UIManager.put("TableHeader.foreground", Color.WHITE);
            UIManager.put("TabbedPane.selectedBackground", new Color(0, 120, 215));
            UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void toggleDarkMode() {
        darkMode = !darkMode;
        setupUI();
    }
    
    public static boolean isDarkMode() {
        return darkMode;
    }
}
