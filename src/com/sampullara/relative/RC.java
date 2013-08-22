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
   $Header: /home/cvs/root/NIBLayout/src/com/sampullara/relative/RC.java,v 1.1 2005/03/28 02:21:13 sam Exp $
   $Id: RC.java,v 1.1 2005/03/28 02:21:13 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.relative;

import java.awt.*;

/**
 * User: sam
 * Date: Mar 27, 2005
 * Time: 5:57:30 PM
 */
public class RC extends RCConstraint implements Cloneable {

    public RC f(Rectangle frame) {
        this.frame = frame;
        return this;
    }

    public RC f() {
        setF(true);
        return this;
    }

    public RC x() {
        setX(true);
        return this;
    }

    public RC y() {
        setY(true);
        return this;
    }

    public RC n() {
        setN(true);
        return this;
    }

    public RC s() {
        setS(true);
        return this;
    }

    public RC w() {
        setW(true);
        return this;
    }

    public RC e() {
        setE(true);
        return this;
    }

    public RC t() {
        setT(true);
        return this;
    }

    public RC b() {
        setB(true);
        return this;
    }

    public RC r() {
        setR(true);
        return this;
    }

    public RC l() {
        setL(true);
        return this;
    }

    public RC lo(Component leftOf) {
        this.setLeftOf(leftOf);
        return this;
    }

    public RC ro(Component rightOf) {
        this.setRightOf(rightOf);
        return this;
    }

    public RC a(Component above) {
        this.setAbove(above);
        return this;
    }

    public RC b(Component below) {
        this.setBelow(below);
        return this;
    }

    public RC awlo(Component alignedWithLeftOf) {
        this.setAlignedWithLeftOf(alignedWithLeftOf);
        return this;
    }

    public RC awro(Component alignedWithRightOf) {
        this.setAlignedWithRightOf(alignedWithRightOf);
        return this;
    }

    public RC awto(Component alignedWithTopOf) {
        this.setAlignedWithTopOf(alignedWithTopOf);
        return this;
    }

    public RC awbo(Component alignedWithBottomOf) {
        this.setAlignedWithBottomOf(alignedWithBottomOf);
        return this;
    }

    public RC awvco(Component alignedWithVerticalCenterOf) {
        this.setAlignedWithVerticalCenterOf(alignedWithVerticalCenterOf);
        return this;
    }

    public RC awhco(Component alignedWithHorizontalCenterOf) {
        this.setAlignedWithHorizontalCenterOf(alignedWithHorizontalCenterOf);
        return this;
    }

    public RC awblo(Component alignedWithBaselineOf) {
        this.setAlignedWithBaselineOf(alignedWithBaselineOf);
        return this;
    }

    public RC eet(Component extendEastTo) {
        this.setExtendEastTo(extendEastTo);
        return this;
    }

    public RC est(Component extendSouthTo) {
        this.setExtendSouthTo(extendSouthTo);
        return this;
    }

    public RC ent(Component extendNorthTo) {
        this.setExtendNorthTo(extendNorthTo);
        return this;
    }

    public RC ewt(Component extendWestTo) {
        this.setExtendWestTo(extendWestTo);
        return this;
    }

    public RC swa(Component sameWidthAs) {
        this.setSameWidthAs(sameWidthAs);
        return this;
    }

    public RC sha(Component sameHeightAs) {
        this.setSameHeightAs(sameHeightAs);
        return this;
    }

    public RC nx(int nudgeX) {
        this.setNudgeX(nudgeX);
        return this;
    }

    public RC ny(int nudgeY) {
        this.setNudgeY(nudgeY);
        return this;
    }

    public RC nw(int nudgeWidth) {
        this.setNudgeWidth(nudgeWidth);
        return this;
    }

    public RC nh(int nudgeHeight) {
        this.setNudgeHeight(nudgeHeight);
        return this;
    }

    public RC mwo(Component[] maxWidthOf) {
        this.setMaxWidthOf(maxWidthOf);
        return this;
    }

    public RC mho(Component[] maxHeightOf) {
        this.setMaxHeightOf(maxHeightOf);
        return this;
    }

    // Constructors
    public RC() {
    }

    public RC(int[] values, String springs) {
        frame = new Rectangle(values[0], values[1], values[2], values[3]);
        setSprings(springs);
    }

    public RC(String springs) {
        setSprings(springs);
    }

    public RC(Component c, int x, int y, String springs) {
        Dimension size = c.getPreferredSize();
        frame = new Rectangle(x, y, size.width, size.height);
        setSprings(springs);
    }

    public RC(Component c, String springs) {
        frame = c.getBounds();
        setSprings(springs);
    }

    public RC(int x, int y, int width, int height, String springs) {
        frame = new Rectangle(x, y, width, height);
        setSprings(springs);
    }

    public Object clone() {
        return super.clone();
    }

}
