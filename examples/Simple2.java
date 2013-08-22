/*
   Copyright 2004 Sam Pullara

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   $Author: sam $
   $Date: 2005/03/28 02:21:12 $
   $Header: /home/cvs/root/NIBLayout/examples/Simple2.java,v 1.11 2005/03/28 02:21:12 sam Exp $
   $Id: Simple2.java,v 1.11 2005/03/28 02:21:12 sam Exp $

   Revisions:
   $Log: Simple2.java,v $
   Revision 1.11  2005/03/28 02:21:12  sam
   Separate the NIB like editable layout system from the programmatic one

   Revision 1.10  2004/12/19 07:56:25  sam
   Preparing for a new style

   Revision 1.9  2004/12/19 01:49:26  sam
   Allow reinititalization on load

   Revision 1.8  2004/12/19 00:57:56  sam
   exit on window close

   Revision 1.7  2004/12/18 02:12:50  sam
   Start on resizing of components.  Experimented with saving / restoring.

   Revision 1.6  2004/12/17 23:00:36  sam
   More light refactoring to clean things up.

   Revision 1.5  2004/12/16 20:46:12  sam
   Fixes the baseline to work with other jdk versions

   Revision 1.4  2004/12/16 11:26:13  sam
   Adds a popup menu to editing.  Fixes it on windows or without the Mac menubar.

   Revision 1.3  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */

import com.sampullara.nib.tool.Editor;
import com.sampullara.relative.RC;
import com.sampullara.relative.RelativeLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Simple2 {
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        final JFrame frame = new JFrame("Simple Dialog");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
            }

            public void windowClosed(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(320, 150);

        JPanel panel = new JPanel(new RelativeLayoutManager());
        frame.setContentPane(panel);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JPasswordField();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        JButton browse = new JButton("Browse");

        panel.add(ok, new RC("nwbr"));
        RC nc = new RC("nwb");
        nc.setLocationX(120);
        panel.add(cancel, nc);

        Component[] labels = new Component[]{usernameLabel, passwordLabel};
        panel.add(usernameLabel, new RC("setl").mwo(labels));
        panel.add(usernameField, new RC("sx").awvco(usernameLabel).ro(usernameLabel).eet(browse));
        panel.add(browse, new RC("swr").awblo(usernameLabel));
        panel.add(passwordLabel, new RC("se").awro(usernameLabel).b(usernameField).mwo(labels));
        panel.add(passwordField, new RC("sx").b(usernameField).awvco(passwordLabel).ro(passwordLabel).eet(panel));

        final Editor editor = new Editor(frame);
        JMenuBar menuBar = new JMenuBar();
        JMenu windowMenu = new JMenu("Window");
        final JMenuItem editingItem = new JMenuItem();
        Action editingAction = new AbstractAction("Edit Layout") {
            public void actionPerformed(ActionEvent e) {
                editor.setEditing(true);
            }
        };
        editingItem.setAction(editingAction);
        windowMenu.add(editingItem);

        final JMenuItem outputItem = new JMenuItem();
        Action outputAction = new AbstractAction("Save layout") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                int result = jFileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    XMLEncoder xmle = null;
                    try {
                        xmle = new XMLEncoder(new FileOutputStream(jFileChooser.getSelectedFile()));
                        xmle.writeObject(frame.getContentPane());
                        xmle.close();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
        outputItem.setAction(outputAction);
        windowMenu.add(outputItem);

        final JMenuItem inputItem = new JMenuItem();
        Action inputAction = new AbstractAction("Load layout") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                int result = jFileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    XMLDecoder xmld = null;
                    try {
                        xmld = new XMLDecoder(new FileInputStream(jFileChooser.getSelectedFile()));
                        Container panel = (JPanel) xmld.readObject();
                        frame.setContentPane(panel);
                        xmld.close();
                        editor.setFrame(frame);
                        frame.validate();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
        inputItem.setAction(inputAction);
        windowMenu.add(inputItem);

        menuBar.add(windowMenu);

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
        frame.validate();
    }
}