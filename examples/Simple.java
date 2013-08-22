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
   $Header: /home/cvs/root/NIBLayout/examples/Simple.java,v 1.3 2005/03/28 02:21:12 sam Exp $
   $Id: Simple.java,v 1.3 2005/03/28 02:21:12 sam Exp $

   Revisions:
   $Log: Simple.java,v $
   Revision 1.3  2005/03/28 02:21:12  sam
   Separate the NIB like editable layout system from the programmatic one

   Revision 1.2  2004/12/16 08:01:31  sam
   Initial work on Swing Interface Builder


   Version: $Name:  $
 */

import com.sampullara.nib.NC;
import com.sampullara.relative.RC;
import com.sampullara.relative.RelativeLayoutManager;

import javax.swing.*;

public class Simple {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Dialog");
        frame.setSize(320, 150);
        JPanel panel = new JPanel(new RelativeLayoutManager());
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordField = new JPasswordField();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        panel.add(ok, new NC("nwbr"));
        panel.add(cancel, new RC("nwb").lo(ok));
        panel.add(usernameLabel, new RC("setl"));
        panel.add(usernameField, new RC("sxt").awvco(usernameLabel).ro(usernameLabel).eet(panel));
        panel.add(passwordLabel, new RC("se").awro(usernameLabel).b(usernameField).swa(usernameLabel));
        panel.add(passwordField, new RC("sxt").b(usernameField).awvco(passwordLabel).ro(passwordLabel).eet(panel));

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}