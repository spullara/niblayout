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
   $Header: /home/cvs/root/NIBLayout/examples/Test.java,v 1.3 2005/03/28 02:21:12 sam Exp $
   $Id: Test.java,v 1.3 2005/03/28 02:21:12 sam Exp $

   Revisions:
   $Log: Test.java,v $
   Revision 1.3  2005/03/28 02:21:12  sam
   Separate the NIB like editable layout system from the programmatic one

   Revision 1.2  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */

import com.sampullara.relative.RC;
import com.sampullara.relative.RelativeLayoutManager;

import javax.swing.*;


public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Relative Layout Manager");
        frame.setSize(640, 480);

        JPanel panel = new JPanel(new RelativeLayoutManager());
        frame.setContentPane(panel);

        // Put an OK button in the lower right hand corner and keep it there by allocating resize
        // space to the distance between the component and the north and west edge
        JButton ok = new JButton("OK");
        panel.add(ok, new RC("nwbr"));

        // Put a Cancel button along the bottom edge to the left of the OK button and apply the
        // same NW springs to keep it there.
        JButton cancel = new JButton("Cancel");
        panel.add(cancel, new RC("nw").lo(ok).awblo(ok));

        // Put a button against the right edge above the ok button
        JButton send = new JButton("Send");
        panel.add(send, new RC("nwr").a(ok));

        // The status label is put in the lower left hand corner and its width extends to the
        // cancel button.
        JLabel status = new JLabel("Status.");
        panel.add(status, new RC("nel").eet(cancel).awblo(cancel));

        // Put a textfield right above the cancel button on the left edge and extend it to the
        // right edge of the panel
        JTextField input = new JTextField();
        panel.add(input, new RC("nxl").awvco(send).eet(send));

        // Put a textarea in the upper left hand corner and extend it to the right edge of the
        // panel and to the top of the textfield
        JTextArea output = new JTextArea();
        panel.add(output, new RC("xytl").est(input).eet(panel));

        frame.setVisible(true);
    }
}