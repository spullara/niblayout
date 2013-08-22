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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/tool/Guidelines.java,v 1.2 2005/03/28 02:21:12 sam Exp $
   $Id: Guidelines.java,v 1.2 2005/03/28 02:21:12 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.nib.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: sam
 * Date: Dec 17, 2004
 * Time: 6:05:06 PM
 */
class Guidelines extends JPanel {

    private java.util.List lines = new ArrayList();
    private java.util.List resizers = new ArrayList();
    private JPopupMenu popup;
    private Editor editor;

    Guidelines(final Editor editor) {
        this.editor = editor;
        Action disableAction = new AbstractAction("Disable") {
            public void actionPerformed(ActionEvent e) {
                editor.setEditing(false);
            }
        };
        JMenuItem disable = new JMenuItem();
        disable.setAction(disableAction);
        popup = new JPopupMenu();
        popup.add(disable);

        add(popup);

        addMouseListener(new PopupListener());
        setOpaque(false);
    }

    void add(Line line) {
        lines.add(line);
    }

    void clearLines() {
        lines = new ArrayList();
    }

    void add(Resizer resizer) {
        resizers.add(resizer);
    }

    void removeResizer(Component c) {
        for (Iterator i = resizers.iterator(); i.hasNext();) {
            Resizer r = (Resizer) i.next();
            if (r.getC() == c) {
                resizers.remove(r);
                return;
            }
        }
    }

    void clearResizers() {
        resizers.clear();
    }

    Resizer findResizer(int x, int y) {
        for (Iterator i = resizers.iterator(); i.hasNext();) {
            Resizer r = (Resizer) i.next();
            if (r.findDot(x, y)) {
                return r;
            }
        }
        return null;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = setupGraphics(g);
        paintComponents(g2d);
        paintMenu(g);
        paintGuides(g2d);
        paintResizers(g2d);
        g2d.dispose();
    }

    private void paintResizers(Graphics2D g2d) {
        for (Iterator i = resizers.iterator(); i.hasNext();) {
            Resizer r = (Resizer) i.next();
            if (r.isSelected()) {
                r.paint(g2d);
                return;
            }
        }
        for (Iterator i = resizers.iterator(); i.hasNext();) {
            Resizer r = (Resizer) i.next();
            r.paint(g2d);
        }
    }

    private void paintGuides(Graphics2D g2d) {
        for (Iterator i = lines.iterator(); i.hasNext();) {
            Line line = (Line) i.next();
            drawLine(g2d, line);
        }
    }

    private void paintComponents(Graphics2D g2d) {
        editor.getParent().paintComponents(g2d);
    }

    private void paintMenu(Graphics g) {
        JMenuBar mb = editor.getFrame().getJMenuBar();
        if (mb != null) mb.paintComponents(g);
    }

    private Graphics2D setupGraphics(Graphics g) {
        Dimension size = editor.getParent().getSize();
        Rectangle bounds = editor.getParent().getBounds();
        g = (Graphics2D) g.create(bounds.x, bounds.y, bounds.width, bounds.height);
        g.clearRect(0, 0, size.width, size.height);
        return (Graphics2D) g;
    }

    private void drawLine(Graphics2D g2d, Line line) {
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, new float[]{5f, 5f}, 0));
        line.paint(g2d);
    }

    private class PopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }
    }
}
