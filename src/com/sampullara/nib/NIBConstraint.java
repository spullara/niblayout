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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/nib/NIBConstraint.java,v 1.12 2005/03/28 02:21:12 sam Exp $
   $Id: NIBConstraint.java,v 1.12 2005/03/28 02:21:12 sam Exp $

   Revisions:
   $Log: NIBConstraint.java,v $
   Revision 1.12  2005/03/28 02:21:12  sam
   Separate the NIB like editable layout system from the programmatic one

   Revision 1.11  2004/12/19 07:56:25  sam
   Preparing for a new style

   Revision 1.10  2004/12/19 01:49:26  sam
   Allow reinititalization on load

   Revision 1.9  2004/12/18 02:12:50  sam
   Start on resizing of components.  Experimented with saving / restoring.

   Revision 1.8  2004/12/17 23:00:37  sam
   More light refactoring to clean things up.

   Revision 1.7  2004/12/17 06:24:54  sam
   More light refactoring to clean things up.

   Revision 1.6  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */
package com.sampullara.nib;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: sam
 * Date: Dec 13, 2004
 * Time: 1:42:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class NIBConstraint implements Cloneable {
    // Absolute positioning
    public Rectangle frame;
    private Integer locationX;
    private Integer locationY;
    private Integer width;
    private Integer height;

    private boolean x; // width spring
    private boolean y; // height spring
    private boolean n; // north spring
    private boolean s; // south spring, still stretches if height doesn't
    private boolean w; // west spring
    private boolean e; // east spring, still stretches if width doesn't
    private boolean t; // top glue
    private boolean b; // bottom glue
    private boolean r; // right glue
    private boolean l; // left glue
    private boolean f; // fill container

    // Calculated values from initialization
    public int dn = Integer.MAX_VALUE; // normal distance from north border
    public int ds = Integer.MAX_VALUE; // normal distance from south border
    public int dw = Integer.MAX_VALUE; // normal distance from east border
    public int de = Integer.MAX_VALUE; // normal distance from west border

    // Current state of the components
    protected static final int STATE_IDLE = 0;
    protected static final int STATE_PROCESSING = 1;
    protected int state = STATE_IDLE;

    public void resetPositionRelations() {
        t = false;
        b = false;
        l = false;
        r = false;
    }

    public void resetSize() {
        width = null;
        height = null;
    }

    public void resetLocation() {
        locationX = null;
        locationY = null;
    }

    public void setSprings(String springs) {
        springs = springs.toLowerCase();
        this.setX(springs.indexOf("x") != -1);
        this.setY(springs.indexOf("y") != -1);
        this.setN(springs.indexOf("n") != -1);
        this.setW(springs.indexOf("w") != -1);
        this.setS(springs.indexOf("s") != -1);
        this.setE(springs.indexOf("e") != -1);
        this.setT(springs.indexOf("t") != -1);
        this.setB(springs.indexOf("b") != -1);
        this.setL(springs.indexOf("l") != -1);
        this.setR(springs.indexOf("r") != -1);
        this.setF(springs.indexOf("f") != -1);
    }

    public Object clone() {
        try {
            NIBConstraint clone = (NIBConstraint) super.clone();
            if (frame != null) {
                clone.frame = (Rectangle) frame.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone is supported: " + e);
        }
    }

    public boolean isX() {
        return x;
    }

    public void setX(boolean x) {
        this.x = x;
    }

    public boolean isY() {
        return y;
    }

    public void setY(boolean y) {
        this.y = y;
    }

    public boolean isN() {
        return n;
    }

    public void setN(boolean n) {
        this.n = n;
    }

    public boolean isS() {
        return s;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public boolean isW() {
        return w;
    }

    public void setW(boolean w) {
        this.w = w;
    }

    public boolean isE() {
        return e;
    }

    public void setE(boolean e) {
        this.e = e;
    }

    public boolean isT() {
        return t;
    }

    public void setT(boolean t) {
        this.t = t;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public boolean isR() {
        return r;
    }

    public void setR(boolean r) {
        this.r = r;
    }

    public boolean isL() {
        return l;
    }

    public void setL(boolean l) {
        this.l = l;
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public Integer getLocationX() {
        return locationX;
    }

    public void setLocationX(Integer locationX) {
        this.locationX = locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = new Integer(locationX);
    }

    public Integer getLocationY() {
        return locationY;
    }

    public void setLocationY(Integer locationY) {
        this.locationY = locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = new Integer(locationY);
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setWidth(int width) {
        this.width = new Integer(width);
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setHeight(int height) {
        this.height = new Integer(height);
    }

    public void setLocation(Point location) {
        this.locationX = new Integer(location.x);
        this.locationY = new Integer(location.y);
    }

    public void setSize(Dimension size) {
        this.width = new Integer(size.width);
        this.height = new Integer(size.height);
    }

    public void setBounds(Rectangle bounds) {
        this.locationX = new Integer(bounds.x);
        this.locationY = new Integer(bounds.y);
        this.width = new Integer(bounds.width);
        this.height = new Integer(bounds.height);
    }

    public String toString() {
        return "NIBConstraint{" +
                "frame=" + frame +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", width=" + width +
                ", height=" + height +
                ", x=" + x +
                ", y=" + y +
                ", n=" + n +
                ", s=" + s +
                ", w=" + w +
                ", e=" + e +
                ", t=" + t +
                ", b=" + b +
                ", r=" + r +
                ", l=" + l +
                ", f=" + f +
                ", dn=" + dn +
                ", ds=" + ds +
                ", dw=" + dw +
                ", de=" + de +
                ", state=" + state +
                "}";
    }
}
