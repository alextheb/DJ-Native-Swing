/*
 * Christopher Deckers (chrriis@nextencia.net)
 * http://www.nextencia.net
 * 
 * See the file "readme.txt" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */
package chrriis.dj.nativeswing.demo.examples.webbrowser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import chrriis.dj.nativeswing.ui.JWebBrowser;

/**
 * @author Christopher Deckers
 */
public class SettingContent extends JPanel {

  protected static final String LS = System.getProperty("line.separator");

  public SettingContent() {
    super(new BorderLayout(0, 0));
    JPanel webBrowserPanel = new JPanel(new BorderLayout(0, 0));
    webBrowserPanel.setBorder(BorderFactory.createTitledBorder("Native Web Browser component"));
    final JWebBrowser webBrowser = new JWebBrowser();
    webBrowser.setBarsVisible(false);
    webBrowser.setStatusBarVisible(true);
    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
    add(webBrowserPanel, BorderLayout.CENTER);
    JPanel configurationPanel = new JPanel(new BorderLayout(0, 0));
    configurationPanel.setBorder(BorderFactory.createTitledBorder("Configuration"));
    final JTextArea configurationTextArea = new JTextArea(
        "<html>" + LS +
        "  <body>" + LS +
        "    <h1>Some header</h1>" + LS +
        "    <p>A paragraph with a <a href=\"http://www.google.com\">link</a>.</p>" + LS +
        "  </body>" + LS +
        "</html>");
    JScrollPane scrollPane = new JScrollPane(configurationTextArea);
    Dimension preferredSize = scrollPane.getPreferredSize();
    preferredSize.height += 20;
    scrollPane.setPreferredSize(preferredSize);
    configurationPanel.add(scrollPane, BorderLayout.CENTER);
    JPanel configurationButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    JButton setContentButton = new JButton("Set Content");
    setContentButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        webBrowser.setText(configurationTextArea.getText());
      }
    });
    configurationButtonPanel.add(setContentButton);
    configurationPanel.add(configurationButtonPanel, BorderLayout.SOUTH);
    add(configurationPanel, BorderLayout.NORTH);
  }
  
}
