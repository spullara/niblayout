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
   $Date: 2004/12/18 02:12:50 $
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/tool/BaseEditor.java,v 1.3 2004/12/18 02:12:50 sam Exp $
   $Id: BaseEditor.java,v 1.3 2004/12/18 02:12:50 sam Exp $

   Revisions:
   $Log: BaseEditor.java,v $
   Revision 1.3  2004/12/18 02:12:50  sam
   Start on resizing of components.  Experimented with saving / restoring.

   Revision 1.2  2004/12/17 23:00:37  sam
   More light refactoring to clean things up.

   Revision 1.1  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.nib.tool;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

/**
 * User: sam
 * Date: Dec 15, 2004
 * Time: 3:49:03 PM
 */
public abstract class BaseEditor implements MouseListener, KeyListener, MouseMotionListener, ImageObserver {

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}
