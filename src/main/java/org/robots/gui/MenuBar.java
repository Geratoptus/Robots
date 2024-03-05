package org.robots.gui;

import javax.swing.*;

import org.robots.log.Logger;

import java.awt.event.*;

public class MenuBar extends JMenuBar{
    private final MainApplicationFrame parent;

    public MenuBar(MainApplicationFrame parent) {
        this.parent = parent;
        this.add(initTestMenu());
        this.add(initLookAndFeelMenu());
        this.add(initExitMenu());
    }

    private JMenu createJMenu(String label, int mnemonic, String accessDescription){
        JMenu menu = new JMenu(label);
        menu.setMnemonic(mnemonic);
        menu.getAccessibleContext().setAccessibleDescription(accessDescription);

        return menu;
    }

    private JMenu initExitMenu(){
        JMenu menu = createJMenu("Выход", KeyEvent.VK_E,
                "Выход из приложения");

        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                parent.confirmWindowClose();
            }
        });

        return menu;
    }
    private JMenuItem createJMenuItem(String label, KeyStroke key, ActionListener listener){
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.setAccelerator(key);
        menuItem.addActionListener(listener);

        return menuItem;
    }

    private void setLookAndFeel(String className){
        try{
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(parent);
        }

        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException exception) {
                //ignore
        }

        finally{
            parent.invalidate();
        }

    }

    private JMenu initLookAndFeelMenu(){
        JMenu menu = createJMenu("Режим отображения", KeyEvent.VK_V,
                "Управление режимом отображения приложения");

        menu.add(createJMenuItem("Системная схема", KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK),
                event -> setLookAndFeel(UIManager.getSystemLookAndFeelClassName())));

        menu.add(createJMenuItem("Универсальная схема", KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK),
                event -> setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName())));

        return menu;
    }
    private JMenu initTestMenu(){
        JMenu menu = createJMenu("Тесты", KeyEvent.VK_T,
                "Тестовые команды");

        menu.add(createJMenuItem("Сообщение в лог", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_MASK),
                event -> Logger.debug("Новая строка")));

        return menu;
    }



}
