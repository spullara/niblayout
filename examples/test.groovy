import javax.swing.*;
import groovy.swing.*;
import com.sampullara.nib.*;
import java.awt.*;

swing = new SwingBuilder();
main = swing.panel(layout:new NIBLayoutManager());

frame = swing.frame(
    title:"NIB Layout Manager",
    size:[640, 480],
    defaultCloseOperation:javax.swing.WindowConstants.EXIT_ON_CLOSE) {
        widget(main) {
	        button(id:"ok",text:"OK", constraints:new NC("nwbr"))
	        button(id:"cancel",text:"Cancel", constraints:new NC("nw").awblo(ok).lo(ok))
	        button(id:"send",text:"Send", constraints:new NC("nwr").a(ok))
	        label(text:"Status.", constraints:new NC("nel").eet(cancel).awblo(cancel))
	        textField(constraints:new NC("lnx").awvco(send).eet(send))
	        textArea(constraints:new NC("xytlf").eet(main).est(send))
        }
    }

frame.visible = true
