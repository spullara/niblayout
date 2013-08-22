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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/NC.java,v 1.17 2005/03/28 02:21:12 sam Exp $
   $Id: NC.java,v 1.17 2005/03/28 02:21:12 sam Exp $

   Revisions:
   $Log: NC.java,v $
   Revision 1.17  2005/03/28 02:21:12  sam
   Separate the NIB like editable layout system from the programmatic one

   Revision 1.16  2004/12/19 07:56:25  sam
   Preparing for a new style

   Revision 1.15  2004/12/17 23:00:37  sam
   More light refactoring to clean things up.

   Revision 1.14  2004/12/17 06:24:54  sam
   More light refactoring to clean things up.

   Revision 1.13  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.nib;

import java.awt.*;

/**
 * This is the constraint object for the NIBLayoutManager.  Every component in a NIBLayout
 * has one of these associated with it. NC is short for NIBConstraint so we don't have to
 * type a lot to build interfaces with them.  Similarly for all the other shortcut versions
 * of setters.  It's a little non-Java like, but it makes for very terse descriptions of UIs.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: sam
 * Date: Dec 9, 2004
 * Time: 9:40:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class NC extends NIBConstraint implements Cloneable {

    public NC f(Rectangle frame) {
        this.frame = frame;
        return this;
    }

    public NC f() {
        setF(true);
        return this;
    }

    public NC x() {
        setX(true);
        return this;
    }

    public NC y() {
        setY(true);
        return this;
    }

    public NC n() {
        setN(true);
        return this;
    }

    public NC s() {
        setS(true);
        return this;
    }

    public NC w() {
        setW(true);
        return this;
    }

    public NC e() {
        setE(true);
        return this;
    }

    public NC t() {
        setT(true);
        return this;
    }

    public NC b() {
        setB(true);
        return this;
    }

    public NC r() {
        setR(true);
        return this;
    }

    public NC l() {
        setL(true);
        return this;
    }

    // Constructors
    public NC() {
    }

    public NC(int[] values, String springs) {
        frame = new Rectangle(values[0], values[1], values[2], values[3]);
        setSprings(springs);
    }

    public NC(String springs) {
        setSprings(springs);
    }

    public NC(Component c, int x, int y, String springs) {
        Dimension size = c.getPreferredSize();
        frame = new Rectangle(x, y, size.width, size.height);
        setSprings(springs);
    }

    public NC(Component c, String springs) {
        frame = c.getBounds();
        setSprings(springs);
    }

    public NC(int x, int y, int width, int height, String springs) {
        frame = new Rectangle(x, y, width, height);
        setSprings(springs);
    }

    public Object clone() {
        return super.clone();
    }

}
