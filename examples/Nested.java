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
   $Header: /home/cvs/root/NIBLayout/examples/Nested.java,v 1.3 2005/03/28 02:21:12 sam Exp $
   $Id: Nested.java,v 1.3 2005/03/28 02:21:12 sam Exp $

   Revisions:
   $Log: Nested.java,v $
   Revision 1.3  2005/03/28 02:21:12  sam
   Separate the NIB like editable layout system from the programmatic one

   Revision 1.2  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */

import com.sampullara.relative.RC;
import com.sampullara.relative.RelativeLayoutManager;

import javax.swing.*;
import java.awt.*;

/**
 * User: sam
 * Date: Dec 13, 2004
 * Time: 7:48:52 PM
 */
public class Nested {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nested");
        frame.setSize(640, 400);
        JPanel panel1 = new JPanel(new RelativeLayoutManager());
        panel1.setBackground(Color.RED);
        JPanel panel2 = new JPanel(new RelativeLayoutManager());
        panel2.setBackground(Color.BLUE);
        panel1.add(panel2, new RC().f(new Rectangle(0, 0, 0, 200)).eet(panel1));
        frame.setContentPane(panel1);
        frame.setVisible(true);
    }
}
