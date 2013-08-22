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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/relative/RCConstraint.java,v 1.1 2005/03/28 02:21:13 sam Exp $
   $Id: RCConstraint.java,v 1.1 2005/03/28 02:21:13 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.relative;

import java.awt.*;
import java.util.Arrays;

/**
 * User: sam
 * Date: Mar 27, 2005
 * Time: 5:58:29 PM
 */
public class RCConstraint implements Cloneable {
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

    // Nudges
    private int nudgeX;
    private int nudgeY;
    private int nudgeWidth;
    private int nudgeHeight;

    // Relative positioning
    private Component leftOf;
    private Component rightOf;
    private Component above;
    private Component below;
    private Component alignedWithLeftOf;
    private Component alignedWithRightOf;
    private Component alignedWithTopOf;
    private Component alignedWithBottomOf;
    private Component alignedWithVerticalCenterOf;
    private Component alignedWithHorizontalCenterOf;
    private Component alignedWithBaselineOf;

    // Relative sizing
    private Component extendEastTo;
    private Component extendSouthTo;
    private Component extendNorthTo;
    private Component extendWestTo;
    private Component sameWidthAs;
    private Component sameHeightAs;
    private Component[] maxWidthOf;
    private Component[] maxHeightOf;

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
        leftOf = null;
        rightOf = null;
        above = null;
        below = null;
        alignedWithLeftOf = null;
        alignedWithRightOf = null;
        alignedWithTopOf = null;
        alignedWithBottomOf = null;
        alignedWithVerticalCenterOf = null;
        alignedWithHorizontalCenterOf = null;
        alignedWithBaselineOf = null;
    }

    public void resetSize() {
        width = null;
        height = null;
    }

    public void resetLocation() {
        locationX = null;
        locationY = null;
    }

    public void resetSizeRelations() {
        extendEastTo = null;
        extendSouthTo = null;
        extendNorthTo = null;
        extendWestTo = null;
        sameWidthAs = null;
        sameHeightAs = null;
        maxWidthOf = null;
        maxHeightOf = null;
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
            RCConstraint clone = (RCConstraint) super.clone();
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

    public int getNudgeX() {
        return nudgeX;
    }

    public void setNudgeX(int nudgeX) {
        this.nudgeX = nudgeX;
    }

    public int getNudgeY() {
        return nudgeY;
    }

    public void setNudgeY(int nudgeY) {
        this.nudgeY = nudgeY;
    }

    public int getNudgeWidth() {
        return nudgeWidth;
    }

    public void setNudgeWidth(int nudgeWidth) {
        this.nudgeWidth = nudgeWidth;
    }

    public int getNudgeHeight() {
        return nudgeHeight;
    }

    public void setNudgeHeight(int nudgeHeight) {
        this.nudgeHeight = nudgeHeight;
    }

    public Component getLeftOf() {
        return leftOf;
    }

    public void setLeftOf(Component leftOf) {
        this.leftOf = leftOf;
    }

    public Component getRightOf() {
        return rightOf;
    }

    public void setRightOf(Component rightOf) {
        this.rightOf = rightOf;
    }

    public Component getAbove() {
        return above;
    }

    public void setAbove(Component above) {
        this.above = above;
    }

    public Component getBelow() {
        return below;
    }

    public void setBelow(Component below) {
        this.below = below;
    }

    public Component getAlignedWithLeftOf() {
        return alignedWithLeftOf;
    }

    public void setAlignedWithLeftOf(Component alignedWithLeftOf) {
        this.alignedWithLeftOf = alignedWithLeftOf;
    }

    public Component getAlignedWithRightOf() {
        return alignedWithRightOf;
    }

    public void setAlignedWithRightOf(Component alignedWithRightOf) {
        this.alignedWithRightOf = alignedWithRightOf;
    }

    public Component getAlignedWithTopOf() {
        return alignedWithTopOf;
    }

    public void setAlignedWithTopOf(Component alignedWithTopOf) {
        this.alignedWithTopOf = alignedWithTopOf;
    }

    public Component getAlignedWithBottomOf() {
        return alignedWithBottomOf;
    }

    public void setAlignedWithBottomOf(Component alignedWithBottomOf) {
        this.alignedWithBottomOf = alignedWithBottomOf;
    }

    public Component getAlignedWithVerticalCenterOf() {
        return alignedWithVerticalCenterOf;
    }

    public void setAlignedWithVerticalCenterOf(Component alignedWithVerticalCenterOf) {
        this.alignedWithVerticalCenterOf = alignedWithVerticalCenterOf;
    }

    public Component getAlignedWithHorizontalCenterOf() {
        return alignedWithHorizontalCenterOf;
    }

    public void setAlignedWithHorizontalCenterOf(Component alignedWithHorizontalCenterOf) {
        this.alignedWithHorizontalCenterOf = alignedWithHorizontalCenterOf;
    }

    public Component getAlignedWithBaselineOf() {
        return alignedWithBaselineOf;
    }

    public void setAlignedWithBaselineOf(Component alignedWithBaselineOf) {
        this.alignedWithBaselineOf = alignedWithBaselineOf;
    }

    public Component getExtendEastTo() {
        return extendEastTo;
    }

    public void setExtendEastTo(Component extendEastTo) {
        this.extendEastTo = extendEastTo;
    }

    public Component getExtendSouthTo() {
        return extendSouthTo;
    }

    public void setExtendSouthTo(Component extendSouthTo) {
        this.extendSouthTo = extendSouthTo;
    }

    public Component getExtendNorthTo() {
        return extendNorthTo;
    }

    public void setExtendNorthTo(Component extendNorthTo) {
        this.extendNorthTo = extendNorthTo;
    }

    public Component getExtendWestTo() {
        return extendWestTo;
    }

    public void setExtendWestTo(Component extendWestTo) {
        this.extendWestTo = extendWestTo;
    }

    public Component getSameWidthAs() {
        return sameWidthAs;
    }

    public void setSameWidthAs(Component sameWidthAs) {
        this.sameWidthAs = sameWidthAs;
    }

    public Component getSameHeightAs() {
        return sameHeightAs;
    }

    public void setSameHeightAs(Component sameHeightAs) {
        this.sameHeightAs = sameHeightAs;
    }

    public Component[] getMaxWidthOf() {
        return maxWidthOf;
    }

    public void setMaxWidthOf(Component[] maxWidthOf) {
        this.maxWidthOf = maxWidthOf;
    }

    public Component[] getMaxHeightOf() {
        return maxHeightOf;
    }

    public void setMaxHeightOf(Component[] maxHeightOf) {
        this.maxHeightOf = maxHeightOf;
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
                ", nudgeX=" + nudgeX +
                ", nudgeY=" + nudgeY +
                ", nudgeWidth=" + nudgeWidth +
                ", nudgeHeight=" + nudgeHeight +
                ", leftOf=" + leftOf +
                ", rightOf=" + rightOf +
                ", above=" + above +
                ", below=" + below +
                ", alignedWithLeftOf=" + alignedWithLeftOf +
                ", alignedWithRightOf=" + alignedWithRightOf +
                ", alignedWithTopOf=" + alignedWithTopOf +
                ", alignedWithBottomOf=" + alignedWithBottomOf +
                ", alignedWithVerticalCenterOf=" + alignedWithVerticalCenterOf +
                ", alignedWithHorizontalCenterOf=" + alignedWithHorizontalCenterOf +
                ", alignedWithBaselineOf=" + alignedWithBaselineOf +
                ", extendEastTo=" + extendEastTo +
                ", extendSouthTo=" + extendSouthTo +
                ", extendNorthTo=" + extendNorthTo +
                ", extendWestTo=" + extendWestTo +
                ", sameWidthAs=" + sameWidthAs +
                ", sameHeightAs=" + sameHeightAs +
                ", maxWidthOf=" + (maxWidthOf == null ? null : Arrays.asList(maxWidthOf)) +
                ", maxHeightOf=" + (maxHeightOf == null ? null : Arrays.asList(maxHeightOf)) +
                ", dn=" + dn +
                ", ds=" + ds +
                ", dw=" + dw +
                ", de=" + de +
                ", state=" + state +
                "}";
    }
}
