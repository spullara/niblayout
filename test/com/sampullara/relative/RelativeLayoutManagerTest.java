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
   $Header: /home/cvs/root/NIBLayout/test/com/sampullara/relative/RelativeLayoutManagerTest.java,v 1.1 2005/03/28 02:21:13 sam Exp $
   $Id: RelativeLayoutManagerTest.java,v 1.1 2005/03/28 02:21:13 sam Exp $

   Revisions:


   Version: $Name:  $
 */
package com.sampullara.relative;

import junit.framework.TestCase;

import javax.swing.*;
import java.awt.*;

/**
 * User: sam
 * Date: Mar 27, 2005
 * Time: 6:03:58 PM
 */
public class RelativeLayoutManagerTest extends TestCase {
    LayoutManager test;

    public void testCloneConstraints() {
        RC nc = new RC();
        nc.clone();
    }

    public void testFrame() {
        test = new RelativeLayoutManager();
        JFrame frame = new JFrame("NIBLayoutManager");
        JPanel panel = new JPanel();
        panel.setLayout(test);
        RC nc = new RC();
        nc.setLocation(new Point(20, 20));
        nc.setSize(new Dimension(50, 20));
        nc.setX(true);
        panel.add(new JButton("Test"), nc);
        frame.setContentPane(panel);
        frame.setSize(640, 480);
        frame.setVisible(true);
        int result = JOptionPane.showConfirmDialog(frame, "Does this look right to you?");
        if (result != JOptionPane.OK_OPTION) {
            fail("Component did not look right to the user.");
        }
    }
}
