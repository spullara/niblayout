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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/NIBLayoutManager.java,v 1.24 2005/03/28 02:21:12 sam Exp $
   $Id: NIBLayoutManager.java,v 1.24 2005/03/28 02:21:12 sam Exp $

   Revisions:
   $Log: NIBLayoutManager.java,v $
   Revision 1.24  2005/03/28 02:21:12  sam
   Separate the NIB like editable layout system from the programmatic one

   Revision 1.23  2004/12/19 07:56:25  sam
   Preparing for a new style

   Revision 1.22  2004/12/19 01:49:26  sam
   Allow reinititalization on load

   Revision 1.21  2004/12/18 20:20:32  sam
   Resizing now working.  Now we need to snap to grid.

   Revision 1.20  2004/12/18 02:12:50  sam
   Start on resizing of components.  Experimented with saving / restoring.

   Revision 1.19  2004/12/17 23:00:37  sam
   More light refactoring to clean things up.

   Revision 1.18  2004/12/17 06:24:54  sam
   More light refactoring to clean things up.

   Revision 1.17  2004/12/17 01:06:41  sam
   Large scale refactoring of the editor.  Still need to comment a ton of things.

   Revision 1.16  2004/12/16 20:46:12  sam
   Fixes the baseline to work with other jdk versions

   Revision 1.15  2004/12/16 11:26:13  sam
   Adds a popup menu to editing.  Fixes it on windows or without the Mac menubar.

   Revision 1.14  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.nib;

import com.sampullara.awt.BaseLayoutManager;
import com.sampullara.awt.platform.PlatformStandards;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * This program is dedicated to Anna Elisabeth Pullara, Born December 8th, 2004.
 * <p/>
 * User: sam
 * Date: Dec 9, 2004
 * Time: 9:15:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class NIBLayoutManager extends BaseLayoutManager {

    // Map of components to constraints
    private Map constraints = new LinkedHashMap();
    private Rectangle lastBounds;
    private boolean layout;

    public NIBConstraint getConstraint(Component c) {
        return (NIBConstraint) constraints.get(c);
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
        NIBConstraint nc = (NIBConstraint) constraint;

        // We clone it so it can be reused by the caller, maybe we should make them clone it.
        // Since constructing the initial view shouldn't be too much trouble, lets leave it
        // like this.
        nc = (NIBConstraint) nc.clone();

        // Put the constraint in the map
        constraints.put(comp, nc);

        // Layout needed
        layout = true;
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
        NIBConstraint nc = new NIBConstraint();
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
            NIBConstraint nc = (NIBConstraint) entry.getValue();
            processAbsolute(container, c, nc);
        }

        final Rectangle currentBounds = container.getBounds();
        if (lastBounds == null) lastBounds = currentBounds;

        for (Iterator i = entrySet.iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Component c = (Component) entry.getKey();
            NC nc = (NC) entry.getValue();

            // Process the constraints
            processConstraint(container, c, nc);
        }

        lastBounds = currentBounds;
    }

    private static void processAbsolute(Component parent, Component c, NIBConstraint nc) {
        // If the contraint has no frame, make it the same size as the component
        Rectangle thisBounds = nc.frame;
        if (thisBounds == null) {
            thisBounds = c.getBounds();
        }

        // If we are initilizing, get the absolute size and position
        if (nc.state == NIBConstraint.STATE_IDLE) {
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

    private void processConstraint(Component container, Component thisComponent, NIBConstraint nc) {
        Rectangle thisBounds = nc.frame;

        // Recursive relationships are not currently supported
        if (nc.state == NIBConstraint.STATE_PROCESSING) {
            throw new IllegalArgumentException("Recursive relationships are not supported: " + thisComponent);
        }

        // Only use these the first time through. Use the springs for resizing.
        if (nc.state == NIBConstraint.STATE_IDLE) {
            nc.state = NIBConstraint.STATE_PROCESSING;

            // The other component
            Component thatComponent;

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

            nc.state = NIBConstraint.STATE_IDLE;
        }

        // Set the location and size
        nc.frame = thisBounds;
        // Set the bounds of the component
        thisComponent.setBounds(nc.frame);
    }

}
