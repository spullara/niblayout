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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/tool/EditFrame.java,v 1.1 2004/12/19 07:56:25 sam Exp $
   $Id: EditFrame.java,v 1.1 2004/12/19 07:56:25 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.nib.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.prefs.Preferences;

/**
 * User: sam
 * Date: Dec 18, 2004
 * Time: 8:05:28 PM
 */
public class EditFrame extends JFrame implements ComponentListener {

    private static Preferences prefs = Preferences.userNodeForPackage(EditFrame.class);

    public EditFrame(String name) {
        super(name);
        Rectangle bounds = new Rectangle(prefs.getInt("x", 0), prefs.getInt("y", 0),
                prefs.getInt("width", 640), prefs.getInt("height", 480));
        setBounds(bounds);
        addComponentListener(this);
        setVisible(true);
    }


    /**
     * Invoked when the component's size changes.
     */
    public void componentResized(ComponentEvent e) {
        if (e.getComponent() == this) {
            Rectangle bounds = getBounds();
            prefs.putInt("x", bounds.x);
            prefs.putInt("y", bounds.y);
            prefs.putInt("width", bounds.width);
            prefs.putInt("height", bounds.height);
        }
    }

    /**
     * Invoked when the component's position changes.
     */
    public void componentMoved(ComponentEvent e) {
    }

    /**
     * Invoked when the component has been made visible.
     */
    public void componentShown(ComponentEvent e) {
    }

    /**
     * Invoked when the component has been made invisible.
     */
    public void componentHidden(ComponentEvent e) {
    }
}
