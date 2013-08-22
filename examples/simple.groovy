import javax.swing.*;
import groovy.swing.*;
import com.sampullara.nib.*;
import java.awt.*;

swing = new SwingBuilder();
main = swing.panel(layout:new NIBLayoutManager());

frame = swing.frame(
    title:"NIB Layout Manager",
    size:[320, 150],
    defaultCloseOperation:javax.swing.WindowConstants.EXIT_ON_CLOSE) {
        widget(main) {
	        button(id:"ok",text:"OK", constraints:new NC("nwbr"))
	        button(text:"Cancel", constraints:new NC("nw").awblo(ok).lo(ok))
	        label(id:"ul", text:"Username:", constraints:new NC("setl"))
	        textField(id:"uf", constraints:new NC("sxt").awvco(ul).ro(ul).eet(main))
	        label(id:"pl", text:"Password:", constraints:new NC("se").awro(ul).b(uf).swa(ul))
	        textField(constraints:new NC("sxt").b(uf).awvco(pl).ro(pl).eet(main))
        }
    }

frame.visible = true
