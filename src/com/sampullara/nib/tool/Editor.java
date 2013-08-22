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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/tool/Editor.java,v 1.18 2005/03/28 02:21:12 sam Exp $
   $Id: Editor.java,v 1.18 2005/03/28 02:21:12 sam Exp $

   Revisions:
   $Log: Editor.java,v $
   Revision 1.18  2005/03/28 02:21:12  sam
   Separate the NIB like editable layout system from the programmatic one

   Revision 1.17  2004/12/19 07:56:25  sam
   Preparing for a new style

   Revision 1.16  2004/12/19 01:49:26  sam
   Allow reinititalization on load

   Revision 1.15  2004/12/19 00:53:52  sam
   Light refactoring

   Revision 1.14  2004/12/19 00:38:58  sam
   Resizing now takes into consideration which way you are resizing for guides

   Revision 1.13  2004/12/19 00:00:28  sam
   Snap to guide resizing now works.

   Revision 1.12  2004/12/18 21:02:17  sam
   debug statement

   Revision 1.11  2004/12/18 20:56:41  sam
   Snap to grid for resizing is hard.

   Revision 1.10  2004/12/18 20:20:32  sam
   Resizing now working.  Now we need to snap to grid.

   Revision 1.9  2004/12/18 02:28:46  sam
   Resizing still busted, pulled out inner classes to clean it up a bit.

   Revision 1.8  2004/12/18 02:12:50  sam
   Start on resizing of components.  Experimented with saving / restoring.

   Revision 1.7  2004/12/17 23:00:37  sam
   More light refactoring to clean things up.

   Revision 1.6  2004/12/17 06:24:54  sam
   More light refactoring to clean things up.

   Revision 1.5  2004/12/17 01:06:41  sam
   Large scale refactoring of the editor.  Still need to comment a ton of things.

   Revision 1.4  2004/12/16 21:10:49  sam
   Better setup for distance between components

   Revision 1.3  2004/12/16 20:46:12  sam
   Fixes the baseline to work with other jdk versions

   Revision 1.2  2004/12/16 11:26:13  sam
   Adds a popup menu to editing.  Fixes it on windows or without the Mac menubar.

   Revision 1.1  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.nib.tool;

import com.sampullara.awt.platform.PlatformStandards;
import com.sampullara.nib.NIBConstraint;
import com.sampullara.nib.NIBLayoutManager;
import com.sampullara.relative.RelativeLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * User: sam
 * Date: Dec 15, 2004
 * Time: 2:33:39 PM
 */
public class Editor extends BaseEditor {

    public static final int FUDGE = 2;

    private Container parent;
    private int tx;
    private int ty;
    private NIBLayoutManager lm;
    private Guidelines guidelines;
    private JFrame frame;
    private Component chosen;
    private Resizer resizer;
    private NIBConstraint nc;
    private Point lastOrigin;
    private Color background;
    private boolean vg;
    private boolean hg;
    private int snapx;
    private int snapy;
    private int guidex;
    private int guidey;
    private PlatformStandards ps;

    private java.util.List selection = new ArrayList();

    private final static Color BASELINE_COLOR = new Color(255, 0, 0, 100);
    private final static Color EDGE_COLOR = new Color(255, 0, 0, 100);
    private final static Color DISTANCE_COLOR = new Color(255, 0, 0, 100);
    private final static Color LINEUP_COLOR = new Color(255, 0, 0, 100);

    public Editor(JFrame frame) {
        setFrame(frame);
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
        parent = frame.getContentPane();
        ps = PlatformStandards.getPlatformStandards(parent);
        lm = (NIBLayoutManager) parent.getLayout();
        guidelines = new Guidelines(this);
        guidelines.addMouseListener(this);
        guidelines.addKeyListener(this);
        guidelines.addMouseMotionListener(this);
        frame.setGlassPane(guidelines);
    }

    private boolean editing;

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
        guidelines.setVisible(editing);
        setupEventTranslation();
    }

    private void setupEventTranslation() {
        Rectangle bounds = parent.getBounds();
        tx = -bounds.x;
        ty = -bounds.y;
    }


    private void toggleSelect(Component c) {
        if (selection.contains(c)) {
            selection.remove(c);
            guidelines.removeResizer(c);
        } else {
            selection.add(c);
            guidelines.add(new Resizer(lm, c));
        }
    }

    private void select(Component c) {
        clearSelection();
        selection.add(c);
        guidelines.add(new Resizer(lm, c));
    }

    private void clearSelection() {
        selection.clear();
        guidelines.clearResizers();
    }

    private boolean checkExMask(MouseEvent e, int mask) {
        return (e.getModifiersEx() & mask) == mask;
    }

    private boolean checkButton(MouseEvent e, int mask) {
        return (e.getButton() & mask) == mask;
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e) {
        e.translatePoint(tx, ty);
        if (checkButton(e, MouseEvent.BUTTON1)) {
            Component c = findClickedComponent(e);
            if (c != null && c != parent) {
                if (checkExMask(e, MouseEvent.SHIFT_DOWN_MASK)) {
                    toggleSelect(c);
                } else {
                    select(c);
                }
            }
            guidelines.repaint();
        }
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
            resizer = guidelines.findResizer(e.getX(), e.getY());
            if (resizer == null) {
                chosen = findClickedComponent(e);
                if (chosen != null) {
                    nc = lm.getConstraint(chosen);
                    if (chosen != parent) {
                        chosenToFront(parent);
                        setOrigin(e.getX(), e.getY());
                        updateBackground();
                        setupGuides(chosen.getBounds());
                    } else {
                        clearSelection();
                        chosen = null;
                    }
                }
            } else {
                chosen = resizer.getC();
                setupGuides(chosen.getBounds());
                chosen = null;
            }
        }
    }


    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p/>
     * Due to platform-dependent Drag&Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&Drop operation.
     */
    public void mouseDragged(MouseEvent e) {
        e.translatePoint(tx, ty);
        int x = e.getX();
        int y = e.getY();
        if (resizer != null) {
            resizer.resize(x, y);
            chosen = resizer.getC();
            setupGuides(chosen.getBounds());
            chosen = null;
            guidelines.repaint();
        } else if (chosen != null && nc != null) {
            moveComponent(x, y);
            Rectangle chosenBounds = chosen.getBounds();
            setupGuides(chosenBounds);
            guidelines.repaint();
        }
        parent.repaint();
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e) {
        if (resizer != null) {
            resizer.snap(hg, vg, guidex, guidey);
            resizer.setSelected(false);
            guidelines.clearLines();
            resizer = null;
        } else if (chosen != null) {
            mouseDragged(e);
            snapToGuides();
            guidelines.clearLines();
            resetBackground();
            chosen = null;
        }
        parent.setLayout(lm);
        parent.validate();
        parent.repaint();
    }

    private void snapToGuides() {
        Rectangle bounds = chosen.getBounds();
        if (vg) {
            int dx = snapx - bounds.x;
            moveComponentX(dx);
            nc.setLocationX(null);
        } else {
            nc.setLocationX(bounds.x);
        }
        if (hg) {
            int dy = snapy - bounds.y;
            moveComponentY(dy);
            nc.setLocationY(null);
        } else {
            nc.setLocationY(bounds.y);
        }
    }

    private Component findClickedComponent(MouseEvent e) {
        return SwingUtilities.getDeepestComponentAt(parent, e.getX(), e.getY());
    }

    private void setOrigin(int x, int y) {
        lastOrigin = new Point(x, y);
    }

    private void updateBackground() {
        background = chosen.getBackground();
        chosen.setBackground(new Color(0, 0, 255, 100));
    }

    private void chosenToFront(Container panel) {
        panel.remove(chosen);
        panel.add(chosen, nc, 0);
        nc = lm.getConstraint(chosen);
    }

    private void resetBackground() {
        chosen.setBackground(background);
    }

    private void setupGuides(Rectangle chosenBounds) {
        resetGuides();
        Dimension parentSize = parent.getSize();
        setupEdgeGuides(ps, chosenBounds, parentSize);
        setupComponentGuides(ps, chosenBounds);
    }

    private void setupComponentGuides(PlatformStandards ps, Rectangle bounds) {
        // If we don't already have a guide, look for component guides
        if (!hg || !vg) {
            Set constraints = lm.getConstraints().entrySet();
            for (Iterator i = constraints.iterator(); i.hasNext();) {
                Map.Entry pair = (Map.Entry) i.next();
                Component c = (Component) pair.getKey();
                if (c != chosen) {
                    // Where is the other component?
                    Rectangle cb = c.getBounds();

                    // Alignment
                    setupBaselineGuide(c, bounds, cb);
                    setupTopGuide(c, bounds, cb);
                    setupBottomGuide(c, bounds, cb);
                    setupLeftGuide(c, bounds, cb);
                    setupRightGuide(c, bounds, cb);

                    // Distance
                    setupVerticalDistanceGuides(c, ps, bounds, cb);
                    setupHorizontalDistanceGuides(c, ps, bounds, cb);
                }
            }
        }
    }

    private void resetGuides() {
        vg = false;
        hg = false;
        guidelines.clearLines();
        NIBConstraint nc = lm.getConstraint(chosen);
        nc.resetPositionRelations();
    }

    private void setupEdgeGuides(PlatformStandards ps, Rectangle bounds, Dimension size) {
        setupVerticalEdgeGuides(ps, bounds, size);
        setupHorizontalEdgeGuides(ps, bounds, size);
    }

    private void setupHorizontalDistanceGuides(Component c, PlatformStandards ps, Rectangle bounds, Rectangle cb) {
        // Check the horizontal distance between them
        int hc = ps.getHorizontalComponentSpacing();
        // Right side
        if (checkXResizer(2) && !vg && Math.abs(bounds.x - (snapx = cb.x - hc - bounds.width)) <= FUDGE && (vg = true)) {
            guidex = cb.x - hc;
            if (bounds.y < cb.y) {
                guidelines.add(new Line(DISTANCE_COLOR, guidex, bounds.y, cb.x - hc, cb.y + cb.height));
            } else {
                guidelines.add(new Line(DISTANCE_COLOR, guidex, cb.y, guidex, bounds.y + bounds.height));
            }
        }
        // Left side
        if (checkXResizer(0) && !vg && Math.abs(bounds.x - (snapx = cb.x + cb.width + hc)) <= FUDGE && (vg = true)) {
            guidex = snapx;
            if (bounds.y < cb.y) {
                guidelines.add(new Line(DISTANCE_COLOR, snapx, bounds.y, snapx, cb.y + cb.height));
            } else {
                guidelines.add(new Line(DISTANCE_COLOR, snapx, cb.y, snapx, bounds.y + bounds.height));
            }
        }
    }

    private boolean checkYResizer(int sy) {
        if (resizer == null) return true;
        return resizer.getSy() == sy;
    }

    private boolean checkXResizer(int sx) {
        if (resizer == null) return true;
        return resizer.getSx() == sx;
    }

    private void setupVerticalDistanceGuides(Component c, PlatformStandards ps, Rectangle bounds, Rectangle cb) {
        // Check the vertical distance between them
        int vc = ps.getVerticalComponentSpacing();
        // Bottom
        if (checkYResizer(2) && !hg && Math.abs(bounds.y - (snapy = cb.y - vc - bounds.height)) <= FUDGE && (hg = true)) {
            guidey = cb.y - vc;
            if (bounds.x < cb.x) {
                guidelines.add(new Line(DISTANCE_COLOR, bounds.x, guidey, cb.x + cb.width, guidey));
            } else {
                guidelines.add(new Line(DISTANCE_COLOR, cb.x, guidey, bounds.x + bounds.width, guidey));
            }
        }
        // Top
        if (checkYResizer(0) && !hg && Math.abs(bounds.y - (snapy = cb.y + cb.height + vc)) <= FUDGE && (hg = true)) {
            guidey = snapy;
            if (bounds.x < cb.x) {
                guidelines.add(new Line(DISTANCE_COLOR, bounds.x, snapy, cb.x + cb.width, snapy));
            } else {
                guidelines.add(new Line(DISTANCE_COLOR, cb.x, snapy, bounds.x + bounds.width, snapy));
            }
        }
    }

    private void setupRightGuide(Component c, Rectangle bounds, Rectangle cb) {
        // Check the right side of the components
        if (checkXResizer(2) && !vg && Math.abs(bounds.x - (snapx = cb.x + cb.width - bounds.width)) <= FUDGE && (vg = true)) {
            guidex = cb.x + cb.width;
            if (bounds.y < cb.y) {
                guidelines.add(new Line(LINEUP_COLOR, guidex, bounds.y, guidex, cb.y + cb.height));
            } else {
                guidelines.add(new Line(LINEUP_COLOR, guidex, cb.y, guidex, bounds.y + bounds.height));
            }
        }
    }

    private void setupLeftGuide(Component c, Rectangle bounds, Rectangle cb) {
        // Check the left side of the components
        if (checkXResizer(0) && !vg && Math.abs(bounds.x - (snapx = cb.x)) <= FUDGE && (vg = true)) {
            guidex = cb.x;
            if (bounds.y < cb.y) {
                guidelines.add(new Line(LINEUP_COLOR, guidex, bounds.y, guidex, cb.y + cb.height));
            } else {
                guidelines.add(new Line(LINEUP_COLOR, guidex, cb.y, guidex, bounds.y + bounds.height));
            }
        }
    }

    private void setupBottomGuide(Component c, Rectangle bounds, Rectangle cb) {
        if (checkYResizer(2) && !hg && Math.abs(bounds.y - (snapy = cb.y + cb.height - bounds.height)) <= FUDGE && (hg = true)) {
            guidey = cb.y + cb.height;
            if (bounds.x < cb.x) {
                guidelines.add(new Line(LINEUP_COLOR, bounds.x, guidey, cb.x + cb.width, guidey));
            } else {
                guidelines.add(new Line(LINEUP_COLOR, cb.x, guidey, bounds.x + bounds.width, guidey));
            }
        }
    }

    private void setupTopGuide(Component c, Rectangle bounds, Rectangle cb) {
        if (checkYResizer(0) && !hg && Math.abs(bounds.y - (snapy = cb.y)) <= FUDGE && (hg = true)) {
            guidey = cb.y;
            if (bounds.x < cb.x) {
                guidelines.add(new Line(LINEUP_COLOR, bounds.x, guidey, cb.x + cb.width, guidey));
            } else {
                guidelines.add(new Line(LINEUP_COLOR, cb.x, guidey, bounds.x + bounds.width, guidey));
            }
        }
    }

    private void setupBaselineGuide(Component c, Rectangle bounds, Rectangle cb) {
        if (resizer == null && !hg) {
            int thisTB = RelativeLayoutManager.getTextRectangle(chosen);
            int thatTB = RelativeLayoutManager.getTextRectangle(c);
            if (thisTB != 0 && thatTB != 0) {
                if (Math.abs(bounds.y + thisTB - cb.y - thatTB) <= FUDGE && (hg = true)) {
                    guidey = cb.y + thatTB;
                    snapy = guidey - thisTB;
                    if (bounds.x < cb.x) {
                        guidelines.add(new Line(BASELINE_COLOR, bounds.x, guidey, cb.x + cb.width, guidey));
                    } else {
                        guidelines.add(new Line(BASELINE_COLOR, cb.x, guidey, bounds.x + bounds.width, guidey));
                    }
                }
            }
        }
    }

    private void setupHorizontalEdgeGuides(PlatformStandards ps, Rectangle bounds, Dimension size) {
        int ve = ps.getVerticalEdgeSpacing();
        if (checkYResizer(0) && Math.abs(bounds.y - ve) <= FUDGE && (hg = true)) {
            snapy = ve;
            guidey = snapy;
            guidelines.add(new Line(EDGE_COLOR, 0, guidey, size.width, guidey));
            if (resizer == null) {
                nc.setT(true);
            }
        }
        if (checkYResizer(2) && !hg && Math.abs(bounds.y + bounds.height - size.height - ve) <= FUDGE && (hg = true)) {
            guidey = size.height - ve;
            snapy = guidey - bounds.height;
            guidelines.add(new Line(EDGE_COLOR, 0, guidey, size.width, guidey));
            if (resizer == null) {
                nc.setB(true);
            }
        }
    }

    private void setupVerticalEdgeGuides(PlatformStandards ps, Rectangle bounds, Dimension size) {
        int he = ps.getHorizontalEdgeSpacing();
        if (checkXResizer(0) && Math.abs(bounds.x - he) <= FUDGE && (vg = true)) {
            guidex = snapx = he;
            guidelines.add(new Line(EDGE_COLOR, guidex, 0, guidex, size.height));
            if (resizer == null) {
                nc.setL(true);
            }
        }
        if (checkXResizer(2) && !vg && Math.abs(bounds.x + bounds.width - size.width - he) <= FUDGE && (vg = true)) {
            guidex = size.width - he;
            snapx = guidex - bounds.width;
            guidelines.add(new Line(EDGE_COLOR, guidex, 0, guidex, size.height));
            if (resizer == null) {
                nc.setR(true);
            }
        }
    }

    private void moveComponent(int x, int y) {
        int dx = x - lastOrigin.x;
        moveComponentX(dx);
        int dy = y - lastOrigin.y;
        moveComponentY(dy);
        setOrigin(x, y);
    }

    private void moveComponentX(int dx) {
        Rectangle bounds = chosen.getBounds();
        bounds.x += dx;
        chosen.setBounds(bounds);
    }

    private void moveComponentY(int dy) {
        Rectangle bounds = chosen.getBounds();
        bounds.y += dy;
        chosen.setBounds(bounds);
        nc.frame = bounds;
    }

    public Container getParent() {
        return parent;
    }

    public NIBLayoutManager getLm() {
        return lm;
    }

    public JFrame getFrame() {
        return frame;
    }
}
