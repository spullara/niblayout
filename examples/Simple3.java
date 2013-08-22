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
   $Date: 2004/12/19 07:56:25 $
   $Header: /home/cvs/root/NIBLayout/examples/Simple3.java,v 1.1 2004/12/19 07:56:25 sam Exp $
   $Id: Simple3.java,v 1.1 2004/12/19 07:56:25 sam Exp $

   Revisions:


   Version: $Name:  $
 */

import com.sampullara.nib.NC;
import com.sampullara.nib.NIBLayoutManager;
import com.sampullara.nib.tool.Editor;

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

public class Simple3 {
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

        JPanel panel = new JPanel(new NIBLayoutManager());
        frame.setContentPane(panel);

        JButton cancel = new JButton("Cancel");

        NC nc = new NC("nwb");
        nc.setLocationX(120);
        panel.add(cancel, nc);

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
                        panel.setSize(frame.getContentPane().getSize());
                        frame.setContentPane(panel);
                        frame.setVisible(true);
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
    }
}