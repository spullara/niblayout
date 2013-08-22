/*
   Copyright 2005 Sam Pullara

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
   $Date: 2005/03/28 02:21:13 $
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/relative/RelativeLayoutManager.java,v 1.1 2005/03/28 02:21:13 sam Exp $
   $Id: RelativeLayoutManager.java,v 1.1 2005/03/28 02:21:13 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.relative;

import com.sampullara.awt.BaseLayoutManager;
import com.sampullara.awt.platform.PlatformStandards;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: sam
 * Date: Mar 27, 2005
 * Time: 5:59:26 PM
 */
public class RelativeLayoutManager extends BaseLayoutManager {

    // Map of components to constraints
    private Map constraints = new LinkedHashMap();
    private Rectangle lastBounds;

    public RCConstraint getConstraint(Component c) {
        return (RCConstraint) constraints.get(c);
    }

    public void setConstraints(Map constraints) {
        this.constraints = constraints;
    }

    public Map getConstraints() {
        return constraints;
    }

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     *
     * @param comp       the component to be added
     * @param constraint where/how the component is added to the layout.
     */
    public void addLayoutComponent(Component comp, Object constraint) {
        // This will throw an exception if they pass in the wrong thing
        RCConstraint nc = (RCConstraint) constraint;

        // We clone it so it can be reused by the caller, maybe we should make them clone it.
        // Since constructing the initial view shouldn't be too much trouble, lets leave it
        // like this.
        nc = (RCConstraint) nc.clone();

        // Put the constraint in the map
        constraints.put(comp, nc);
    }

    /**
     * If the layout manager uses a per-component string,
     * adds the component <code>comp</code> to the layout,
     * associating it
     * with the string specified by <code>name</code>.
     *
     * @param name the string to be associated with the component
     * @param comp the component to be added
     */
    public void addLayoutComponent(String name, Component comp) {
        RCConstraint nc = new RCConstraint();
        nc.setSprings(name);
        addLayoutComponent(comp, nc);
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    public void removeLayoutComponent(Component comp) {
        constraints.remove(comp);
    }

    /**
     * Lays out the specified container.
     *
     * @param container the container to be laid out
     */
    public void layoutContainer(Container container) {
        // Setup the platform standards
        if (ps == null) {
            ps = PlatformStandards.getPlatformStandards(container);
        }

        // Loop through the constraints and adjust for the container.
        Set entrySet = constraints.entrySet();
        for (Iterator i = entrySet.iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Component c = (Component) entry.getKey();
            RCConstraint nc = (RCConstraint) entry.getValue();
            processAbsolute(container, c, nc);
        }

        final Rectangle currentBounds = container.getBounds();
        if (lastBounds == null) lastBounds = currentBounds;

        for (Iterator i = entrySet.iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Component c = (Component) entry.getKey();
            RC nc = (RC) entry.getValue();

            // Process the constraints
            processConstraint(container, c, nc);
        }

        lastBounds = currentBounds;
    }

    private static void processAbsolute(Component parent, Component c, RCConstraint nc) {
        // If the contraint has no frame, make it the same size as the component
        Rectangle thisBounds = nc.frame;
        if (thisBounds == null) {
            thisBounds = c.getBounds();
        }

        // If we are initilizing, get the absolute size and position
        if (nc.state == RCConstraint.STATE_IDLE) {
            Integer locationX = nc.getLocationX();
            if (locationX != null) thisBounds.x = locationX.intValue();
            Integer locationY = nc.getLocationY();
            if (locationY != null) thisBounds.y = locationY.intValue();
            Integer width = nc.getWidth();
            if (width != null) thisBounds.width = width.intValue();
            Integer height = nc.getHeight();
            if (height != null) thisBounds.height = height.intValue();
        }

        // Make sure if the component has no size, we set it
        Dimension prefSize = c.getPreferredSize();
        if (thisBounds.width == 0) {
            thisBounds.width = prefSize.width;
        }
        if (thisBounds.height == 0) {
            thisBounds.height = prefSize.height;
        }

        if (nc.isF()) {
            Dimension parentSize = parent.getSize();
            thisBounds = new Rectangle(0, 0, parentSize.width, parentSize.height);
        }

        nc.frame = thisBounds;
        c.setBounds(thisBounds);
    }

    // These are the standard separations for this platform
    private PlatformStandards ps;

    private void processConstraint(Component container, Component thisComponent, RCConstraint nc) {
        Rectangle thisBounds = nc.frame;

        // Recursive relationships are not currently supported
        if (nc.state == RCConstraint.STATE_PROCESSING) {
            throw new IllegalArgumentException("Recursive relationships are not supported: " + thisComponent);
        }

        // Only use these the first time through. Use the springs for resizing.
        if (nc.state == RCConstraint.STATE_IDLE) {
            nc.state = RCConstraint.STATE_PROCESSING;

            // The other component
            Component thatComponent;

            // Set the sizing
            if ((thatComponent = nc.getSameWidthAs()) != null) {
                Rectangle thatBounds = thatComponent.getBounds();
                thisBounds.width = thatBounds.width;
            }
            if ((thatComponent = nc.getSameHeightAs()) != null) {
                Rectangle thatBounds = thatComponent.getBounds();
                thisBounds.height = thatBounds.height;
            }

            Component[] thoseComponents;
            if ((thoseComponents = nc.getMaxWidthOf()) != null) {
                int maxWidth = 0;
                for (int i = 0; i < thoseComponents.length; i++) {
                    Rectangle thatBounds = thoseComponents[i].getBounds();
                    if (thatBounds.width > maxWidth) {
                        maxWidth = thatBounds.width;
                    }
                }
                thisBounds.width = maxWidth;
            }
            if ((thoseComponents = nc.getMaxHeightOf()) != null) {
                int maxHeight = 0;
                for (int i = 0; i < thoseComponents.length; i++) {
                    Rectangle thatBounds = thoseComponents[i].getBounds();
                    if (thatBounds.height > maxHeight) {
                        maxHeight = thatBounds.height;
                    }
                }
                thisBounds.height = maxHeight;
            }

            // Process container relative constraints
            Dimension parentSize = container.getSize();
            if (nc.isT()) {
                thisBounds.y = ps.getVerticalEdgeSpacing();
            }
            if (nc.isB()) {
                thisBounds.y = parentSize.height - thisBounds.height - ps.getVerticalEdgeSpacing();
            }
            if (nc.isL()) {
                thisBounds.x = ps.getHorizontalEdgeSpacing();
            }
            if (nc.isR()) {
                thisBounds.x = parentSize.width - thisBounds.width - ps.getHorizontalEdgeSpacing();
            }

            // Now do it relative to other components
            if (nc.getLocationX() == null) {
                if ((thatComponent = nc.getLeftOf()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    thisBounds.x = thatBounds.x - thisBounds.width - ps.getHorizontalComponentSpacing();
                }
                if ((thatComponent = nc.getRightOf()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    thisBounds.x = thatBounds.x + thatBounds.width + ps.getHorizontalComponentSpacing();
                }
                if ((thatComponent = nc.getAlignedWithLeftOf()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    thisBounds.x = thatBounds.x;
                }
                if ((thatComponent = nc.getAlignedWithRightOf()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    thisBounds.x = thatBounds.x + thatBounds.width - thisBounds.width;
                }
                if ((thatComponent = nc.getExtendWestTo()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    if (thatComponent == container) {
                        thisBounds.width += thisBounds.x - ps.getHorizontalEdgeSpacing();
                        thisBounds.x = ps.getHorizontalEdgeSpacing();
                    } else {
                        thisBounds.width = thisBounds.x - thatBounds.x - thatBounds.width - ps.getHorizontalEdgeSpacing();
                        thisBounds.x = thatBounds.x + ps.getHorizontalComponentSpacing();
                    }
                }
                if ((thatComponent = nc.getAlignedWithHorizontalCenterOf()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    if (thatComponent == container) {
                        thisBounds.x = thatBounds.width / 2 - thisBounds.width / 2;
                    } else {
                        thisBounds.x = thatBounds.x + thatBounds.width / 2 - thisBounds.width / 2;
                    }
                }

            }
            if (nc.getLocationY() == null) {
                if ((thatComponent = nc.getAbove()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    thisBounds.y = thatBounds.y - thisBounds.height - ps.getVerticalComponentSpacing();
                }
                if ((thatComponent = nc.getBelow()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    thisBounds.y = thatBounds.y + thatBounds.height + ps.getVerticalComponentSpacing();
                }
                if ((thatComponent = nc.getAlignedWithTopOf()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    thisBounds.y = thatBounds.y;
                }
                if ((thatComponent = nc.getAlignedWithBottomOf()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    thisBounds.y = thatBounds.y + thatBounds.height - thisBounds.height;
                }

                if ((thatComponent = nc.getExtendNorthTo()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    if (thatComponent == container) {
                        thisBounds.height = thisBounds.y - ps.getVerticalEdgeSpacing();
                        thisBounds.y = ps.getVerticalEdgeSpacing();
                    } else {
                        thisBounds.height += thisBounds.y - thatBounds.y - thatBounds.width - ps.getVerticalComponentSpacing();
                        thisBounds.y = thatBounds.y + thatBounds.width + ps.getVerticalComponentSpacing();
                    }
                }
                if ((thatComponent = nc.getAlignedWithVerticalCenterOf()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    Rectangle thatBounds = thatComponent.getBounds();
                    if (thatComponent == container) {
                        thisBounds.y = thatBounds.height / 2 - thisBounds.height / 2;
                    } else {
                        thisBounds.y = thatBounds.y + thatBounds.height / 2 - thisBounds.height / 2;
                    }
                }

                if ((thatComponent = nc.getAlignedWithBaselineOf()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                    int thisBaseline = getTextRectangle(thisComponent);
                    int thatBaseline = getTextRectangle(thatComponent);
                    Rectangle thatBounds = thatComponent.getBounds();
                    if (thisBaseline != 0 && thatBaseline != 0) {
                        thisBounds.y = thatBounds.y + thatBaseline - thisBaseline;
                    } else {
                        nc.setAlignedWithHorizontalCenterOf(nc.getAlignedWithBaselineOf());
                    }
                }

            }

            if ((thatComponent = nc.getExtendSouthTo()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                Rectangle thatBounds = thatComponent.getBounds();
                if (thatComponent == container) {
                    thisBounds.height = thatBounds.height - ps.getVerticalEdgeSpacing() - thisBounds.y;
                } else {
                    thisBounds.height = thatBounds.y - thisBounds.y - ps.getVerticalComponentSpacing();
                }
            }
            if ((thatComponent = nc.getExtendEastTo()) != null && ensureInitialized(container, thisComponent, thatComponent)) {
                Rectangle thatBounds = thatComponent.getBounds();
                if (thatComponent == container) {
                    thisBounds.width = thatBounds.width - ps.getHorizontalEdgeSpacing() - thisBounds.x;
                } else {
                    thisBounds.width = thatBounds.x - ps.getHorizontalComponentSpacing() - thisBounds.x;
                }
            }

            thisBounds.x += nc.getNudgeX();
            thisBounds.y += nc.getNudgeY();
            thisBounds.width += nc.getNudgeWidth();
            thisBounds.height += nc.getNudgeHeight();

            nc.state = RCConstraint.STATE_IDLE;
        }

        // Set the location and size
        nc.frame = thisBounds;
        // Set the bounds of the component
        thisComponent.setBounds(nc.frame);
    }

    private boolean ensureInitialized(Component container, Component thisComponent, Component thatComponent) {
        RCConstraint nc = getConstraint(thatComponent);
        if (nc == null) return true;
        if (nc.state == RCConstraint.STATE_PROCESSING) {
            throw new IllegalArgumentException("Recursive relationship between: " + thisComponent + " and " + thatComponent);
        }
        if (nc.state == RCConstraint.STATE_IDLE) {
            processConstraint(container, thatComponent, nc);
        }
        return true;
    }

    private static int getTextRectangle(JLabel label) {

        String text = label.getText();
        Icon icon = label.isEnabled() ? label.getIcon() : label.getDisabledIcon();

        if (icon == null && text == null) {
            return 0;
        }

        Rectangle paintViewR = new Rectangle();
        Insets paintViewInsets = new Insets(0, 0, 0, 0);

        paintViewInsets = label.getInsets(paintViewInsets);
        paintViewR.x = paintViewInsets.left;
        paintViewR.y = paintViewInsets.top;
        paintViewR.width = label.getWidth() - (paintViewInsets.left + paintViewInsets.right);
        paintViewR.height = label.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

        Graphics g = label.getGraphics();
        if (g == null) {
            return 0;
        }
        return getBaseline(label,
                g.getFontMetrics(),
                text,
                label.getVerticalAlignment(),
                label.getVerticalTextPosition(),
                paintViewR);
    }

    private static int getTextRectangle(AbstractButton button) {

        String text = button.getText();
        Icon icon = button.isEnabled() ? button.getIcon() : button.getDisabledIcon();

        if (icon == null && text == null) {
            return 0;
        }

        Rectangle paintViewR = new Rectangle();
        Insets paintViewInsets = new Insets(0, 0, 0, 0);

        paintViewInsets = button.getInsets(paintViewInsets);
        paintViewR.x = paintViewInsets.left;
        paintViewR.y = paintViewInsets.top;
        paintViewR.width = button.getWidth() - (paintViewInsets.left + paintViewInsets.right);
        paintViewR.height = button.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

        Graphics g = button.getGraphics();
        if (g == null) {
            return 0;
        }
        return getBaseline(button,
                g.getFontMetrics(),
                text,
                button.getVerticalAlignment(),
                button.getVerticalTextPosition(),
                paintViewR);
    }


    public static int getTextRectangle(Component c) {
        if (c instanceof JLabel) {
            return getTextRectangle((JLabel) c);
        }
        if (c instanceof JButton) {
            return getTextRectangle((JButton) c);
        }
        return 0;
    }

    /**
     * Find the text baseline of a component.
     */
    public static int getBaseline(JComponent c,
                                  FontMetrics fm,
                                  String text,
                                  int verticalAlignment,
                                  int verticalTextPosition,
                                  Rectangle viewR) {
        /* Initialize the text bounds rectangle textR.  If a null
         * or and empty String was specified we substitute "" here
         * and use 0,0,0,0 for textR.
         */

        Rectangle textR = new Rectangle();
        boolean textIsEmpty = (text == null) || text.equals("");

        View v = null;
        if (textIsEmpty) {
            textR.height = 0;
            text = "";
        } else {
            v = (c != null) ? (View) c.getClientProperty("html") : null;
            if (v != null) {
                textR.height = (int) v.getPreferredSpan(View.Y_AXIS);
            } else {
                textR.height = fm.getHeight();
            }
        }


        /* Compute textR.x,y given the verticalTextPosition and
        * horizontalTextPosition properties
        */

        if (verticalTextPosition == SwingConstants.TOP) {
            textR.y = -textR.height;
        } else if (verticalTextPosition == SwingConstants.CENTER) {
            textR.y = -(textR.height / 2);
        } else { // (verticalTextPosition == BOTTOM)
            textR.y = 0;
        }

        /* labelR is the rectangle that contains iconR and textR.
        * Move it to its proper position given the labelAlignment
        * properties.
        *
        * To avoid actually allocating a Rectangle, Rectangle.union
        * has been inlined below.
        */
        int labelR_y = 0;
        int labelR_height = textR.y + textR.height - labelR_y;

        int dy;

        if (verticalAlignment == SwingConstants.TOP) {
            dy = viewR.y - labelR_y;
        } else if (verticalAlignment == SwingConstants.CENTER) {
            // Fix the problem for buttons.
            int tmp = ((2 * viewR.y + viewR.height) - (2 * labelR_y + labelR_height));
            if (tmp / 2 * 2 != tmp) tmp++;
            dy = tmp / 2;
        } else { // (verticalAlignment == BOTTOM)
            dy = (viewR.y + viewR.height) - (labelR_y + labelR_height);
        }

        /* Translate textR and glypyR by dx,dy.
        */

        textR.y += dy;

        return textR.y + textR.height;
    }
}