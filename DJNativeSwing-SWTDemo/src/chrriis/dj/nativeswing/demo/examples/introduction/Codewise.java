/*
 * Christopher Deckers (chrriis@nextencia.net)
 * http://www.nextencia.net
 * 
 * See the file "readme.txt" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */
package chrriis.dj.nativeswing.demo.examples.introduction;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Christopher Deckers
 */
public class Codewise extends JPanel {

  protected static final String LS = System.getProperty("line.separator");

  public Codewise() {
    super(new BorderLayout(0, 0));
    JEditorPane editorPane = new JEditorPane("text/html", 
        "<html>" + LS +
        "  <body>" + LS +
        "    <h1>Using the library is straightforward</h1>" + LS +
        "    <p>There are only two mandatory calls to add to the main method, and the rest is just using the API exposed by the components:<br/>" + LS +
        "    <pre>" + LS +
        "      public static void main(String[] args) {" + LS +
        "        NativeInterfaceHandler.init();" + LS +
        "        // The rest of the initialization of the program" + LS +
        "        NativeInterfaceHandler.runEventPump();" + LS +
        "      }</pre>" + LS +
        "    </p>" + LS +
        "    <p>Note that the last call is blocking, and it assumes that the Swing application exits explicitely with a System.exit(int) call, which is generally the rule.</p>" + LS +
        "    <h1>Native components are Swing-like</h1>" + LS +
        "    <p>Try the next examples and look at the source code: they could not be easier to manipulate.</p>" + LS +
        "  </body>" + LS +
        "</html");
    editorPane.setEditable(false);
    add(new JScrollPane(editorPane));
  }
  
}
