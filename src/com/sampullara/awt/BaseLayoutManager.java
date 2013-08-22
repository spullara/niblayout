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
   $Date: 2004/12/17 01:06:41 $
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/awt/BaseLayoutManager.java,v 1.6 2004/12/17 01:06:41 sam Exp $
   $Id: BaseLayoutManager.java,v 1.6 2004/12/17 01:06:41 sam Exp $

   Revisions:
   $Log: BaseLayoutManager.java,v $
   Revision 1.6  2004/12/17 01:06:41  sam
   Large scale refactoring of the editor.  Still need to comment a ton of things.

   Revision 1.5  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.awt;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: sam
 * Date: Dec 10, 2004
 * Time: 12:40:34 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseLayoutManager implements LayoutManager, LayoutManager2 {
    public abstract void layoutContainer(Container parent);

    public abstract void removeLayoutComponent(Component comp);

    public abstract void addLayoutComponent(Component comp, Object constraint);

    /**
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains.
     *
     * @see java.awt.Component#getMaximumSize
     * @see java.awt.LayoutManager
     */
    public Dimension maximumLayoutSize(Container target) {
        return target.getMaximumSize();
    }

    /**
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    /**
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     */
    public void invalidateLayout(Container target) {
    }

    /**
     * Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the container to be laid out
     * @see #minimumLayoutSize
     */
    public Dimension preferredLayoutSize(Container parent) {
        // TODO: implement this correctly
        return parent.getPreferredSize();
    }

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the component to be laid out
     * @see #preferredLayoutSize
     */
    public Dimension minimumLayoutSize(Container parent) {
        return parent.getMinimumSize();
    }
}
