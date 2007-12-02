/*
 * Christopher Deckers (chrriis@nextencia.net)
 * http://www.nextencia.net
 * 
 * See the file "readme.txt" for information on usage and redistribution of
 * this file, and for a DISCLAIMER OF ALL WARRANTIES.
 */
package chrriis.dj.nativeswing.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import chrriis.dj.nativeswing.ui.event.InitializationEvent;
import chrriis.dj.nativeswing.ui.event.InitializationListener;
import chrriis.dj.nativeswing.ui.event.WebBrowserAdapter;
import chrriis.dj.nativeswing.ui.event.WebBrowserEvent;
import chrriis.dj.nativeswing.ui.event.WebBrowserListener;
import chrriis.dj.nativeswing.ui.event.WebBrowserNavigationEvent;

/**
 * A web browser.
 * Methods execute when this component is initialized. If the component is not initialized, methods will be executed as soon as it gets initialized.
 * If the initialization fail, the methods will not have any effect. The results from methods have relevant values only when the component is valid. 
 * @author Christopher Deckers
 */
public class JWebBrowser extends JPanel {

  protected final ResourceBundle RESOURCES = ResourceBundle.getBundle(JWebBrowser.class.getPackage().getName().replace('.', '/') + "/resource/WebBrowser");
  
  protected NativeWebBrowser nativeComponent;
  protected JMenuBar menuBar;
  protected JMenu fileMenu;
  protected JMenu viewMenu;
  protected JPanel buttonBarPanel;
  protected JCheckBoxMenuItem buttonBarCheckBoxMenuItem;
  protected JPanel addressBarPanel;
  protected JCheckBoxMenuItem addressBarCheckBoxMenuItem;
  protected JPanel statusBarPanel;
  protected JCheckBoxMenuItem statusBarCheckBoxMenuItem;
  protected JPanel webBrowserPanel;
  
  protected JButton backButton;
  protected JMenuItem backMenuItem;
  protected JButton forwardButton;
  protected JMenuItem forwardMenuItem;
  protected JButton refreshButton;
  protected JMenuItem refreshMenuItem;
  protected JButton stopButton;
  protected JMenuItem stopMenuItem;
  
  public JWebBrowser() {
    setLayout(new BorderLayout(0, 0));
    nativeComponent = new NativeWebBrowser(this);
    nativeComponent.addInitializationListener(new InitializationListener() {
      public void componentInitialized(InitializationEvent e) {
        Object[] listeners = listenerList.getListenerList();
        e = null;
        for(int i=listeners.length-2; i>=0; i-=2) {
          if(listeners[i] == InitializationEvent.class) {
            if(e == null) {
              e = new InitializationEvent(JWebBrowser.this);
            }
            ((InitializationListener)listeners[i + 1]).componentInitialized(e);
          }
        }
      }
    });
    JPanel menuToolAndAddressBarPanel = new JPanel(new BorderLayout(0, 0));
    menuBar = new JMenuBar();
    menuToolAndAddressBarPanel.add(menuBar, BorderLayout.NORTH);
    buttonBarPanel = new JPanel(new BorderLayout(0, 0));
    JToolBar buttonToolBar = new JToolBar();
    buttonToolBar.add(Box.createHorizontalStrut(2));
    buttonToolBar.setFloatable(false);
    backButton = new JButton(createIcon("BackIcon"));
    backButton.setEnabled(false);
    backButton.setToolTipText(RESOURCES.getString("BackText"));
    ActionListener backActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nativeComponent.back();
        nativeComponent.requestFocus();
      }
    };
    backButton.addActionListener(backActionListener);
    buttonToolBar.add(backButton);
    forwardButton = new JButton(createIcon("ForwardIcon"));
    forwardButton.setToolTipText(RESOURCES.getString("ForwardText"));
    forwardButton.setEnabled(false);
    ActionListener forwardActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nativeComponent.forward();
        nativeComponent.requestFocus();
      }
    };
    forwardButton.addActionListener(forwardActionListener);
    buttonToolBar.add(forwardButton);
    refreshButton = new JButton(createIcon("RefreshIcon"));
    refreshButton.setToolTipText(RESOURCES.getString("RefreshText"));
    ActionListener refreshActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nativeComponent.refresh();
        nativeComponent.requestFocus();
      }
    };
    refreshButton.addActionListener(refreshActionListener);
    buttonToolBar.add(refreshButton);
    stopButton = new JButton(createIcon("StopIcon"));
    stopButton.setToolTipText(RESOURCES.getString("StopText"));
    stopButton.setEnabled(false);
    ActionListener stopActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nativeComponent.stop();
      }
    };
    stopButton.addActionListener(stopActionListener);
    buttonToolBar.add(stopButton);
    buttonBarPanel.add(buttonToolBar, BorderLayout.CENTER);
    menuToolAndAddressBarPanel.add(buttonBarPanel, BorderLayout.WEST);
    addressBarPanel = new JPanel(new BorderLayout(0, 0));
    JToolBar addressToolBar = new JToolBar();
    JPanel addressToolBarInnerPanel = new JPanel(new BorderLayout(0, 0));
    addressToolBarInnerPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    addressToolBarInnerPanel.setOpaque(false);
    addressToolBar.setFloatable(false);
    final JTextField addressField = new JTextField();
    ActionListener goActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nativeComponent.setURL(addressField.getText());
        nativeComponent.requestFocus();
      }
    };
    addressField.addActionListener(goActionListener);
    addressToolBarInnerPanel.add(addressField, BorderLayout.CENTER);
    JButton goButton = new JButton(createIcon("GoIcon"));
    goButton.setToolTipText(RESOURCES.getString("GoText"));
    goButton.addActionListener(goActionListener);
    addressToolBar.add(addressToolBarInnerPanel);
    addressToolBar.add(goButton);
    addressBarPanel.add(addressToolBar, BorderLayout.CENTER);
    menuToolAndAddressBarPanel.add(addressBarPanel, BorderLayout.CENTER);
    add(menuToolAndAddressBarPanel, BorderLayout.NORTH);
    webBrowserPanel = new JPanel(new BorderLayout(0, 0));
    webBrowserPanel.add(nativeComponent, BorderLayout.CENTER);
    add(webBrowserPanel, BorderLayout.CENTER);
    statusBarPanel = new JPanel(new BorderLayout(0, 0));
    statusBarPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, statusBarPanel.getBackground().darker()), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
    final JLabel statusLabel = new JLabel(" ");
    statusBarPanel.add(statusLabel, BorderLayout.CENTER);
    add(statusBarPanel, BorderLayout.SOUTH);
    nativeComponent.addWebBrowserListener(new WebBrowserAdapter() {
      @Override
      public void urlChanged(WebBrowserNavigationEvent e) {
        stopButton.setEnabled(false);
        stopMenuItem.setEnabled(false);
        addressField.setText(nativeComponent.getURL());
        boolean isBackEnabled = nativeComponent.isBackEnabled();
        backButton.setEnabled(isBackEnabled);
        backMenuItem.setEnabled(isBackEnabled);
        boolean isForwardEnabled = nativeComponent.isForwardEnabled();
        forwardButton.setEnabled(isForwardEnabled);
        forwardMenuItem.setEnabled(isForwardEnabled);
      }
      @Override
      public void urlChanging(WebBrowserNavigationEvent e) {
        addressField.setText(e.getNewURL());
        stopButton.setEnabled(true);
        stopMenuItem.setEnabled(true);
      }
      @Override
      public void urlChangeCanceled(WebBrowserNavigationEvent e) {
        stopButton.setEnabled(false);
        stopMenuItem.setEnabled(false);
        addressField.setText(nativeComponent.getURL());
        boolean isBackEnabled = nativeComponent.isBackEnabled();
        backButton.setEnabled(isBackEnabled);
        backMenuItem.setEnabled(isBackEnabled);
        boolean isForwardEnabled = nativeComponent.isForwardEnabled();
        forwardButton.setEnabled(isForwardEnabled);
        forwardMenuItem.setEnabled(isForwardEnabled);
      }
      @Override
      public void statusChanged(WebBrowserEvent e) {
        String status = nativeComponent.getStatus();
        statusLabel.setText(status.length() == 0? " ": status);
      }
    });
    adjustBorder();
    fileMenu = new JMenu(RESOURCES.getString("FileMenu"));
    JMenuItem fileNewWindowMenuItem = new JMenuItem(RESOURCES.getString("FileNewWindowMenu"));
    fileNewWindowMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JWebBrowserWindow webBrowserWindow = new JWebBrowserWindow();
        webBrowserWindow.getWebBrowser().setURL(getURL());
        webBrowserWindow.setVisible(true);
      }
    });
    fileMenu.add(fileNewWindowMenuItem);
    final JMenuItem fileOpenLocationMenuItem = new JMenuItem(RESOURCES.getString("FileOpenLocationMenu"));
    fileOpenLocationMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String path = JOptionPane.showInputDialog(JWebBrowser.this, RESOURCES.getString("FileOpenLocationDialogMessage"), RESOURCES.getString("FileOpenLocationDialogTitle"), JOptionPane.QUESTION_MESSAGE);
        if(path != null) {
          setURL(path);
        }
      }
    });
    fileMenu.add(fileOpenLocationMenuItem);
    final JMenuItem fileOpenFileMenuItem = new JMenuItem(RESOURCES.getString("FileOpenFileMenu"));
    fileOpenFileMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if(fileChooser.showOpenDialog(JWebBrowser.this) == JFileChooser.APPROVE_OPTION) {
          try {
            setURL(fileChooser.getSelectedFile().getAbsolutePath());
          } catch(Exception ex) {
            ex.printStackTrace();
          }
        }
      }
    });
    fileMenu.add(fileOpenFileMenuItem);
    menuBar.add(fileMenu);
    viewMenu = new JMenu(RESOURCES.getString("ViewMenu"));
    JMenu viewToolbarsMenu = new JMenu(RESOURCES.getString("ViewToolbarsMenu"));
    buttonBarCheckBoxMenuItem = new JCheckBoxMenuItem(RESOURCES.getString("ViewToolbarsButtonBarMenu"));
    buttonBarCheckBoxMenuItem.setSelected(isButtonBarVisible());
    buttonBarCheckBoxMenuItem.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        setButtonBarVisible(e.getStateChange() == ItemEvent.SELECTED);
      }
    });
    viewToolbarsMenu.add(buttonBarCheckBoxMenuItem);
    addressBarCheckBoxMenuItem = new JCheckBoxMenuItem(RESOURCES.getString("ViewToolbarsAddressBarMenu"));
    addressBarCheckBoxMenuItem.setSelected(isAddressBarVisible());
    addressBarCheckBoxMenuItem.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        setAddressBarVisible(e.getStateChange() == ItemEvent.SELECTED);
      }
    });
    viewToolbarsMenu.add(addressBarCheckBoxMenuItem);
    viewMenu.add(viewToolbarsMenu);
    statusBarCheckBoxMenuItem = new JCheckBoxMenuItem(RESOURCES.getString("ViewStatusBarMenu"));
    statusBarCheckBoxMenuItem.setSelected(isStatusBarVisible());
    statusBarCheckBoxMenuItem.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        setStatusBarVisible(e.getStateChange() == ItemEvent.SELECTED);
      }
    });
    viewMenu.add(statusBarCheckBoxMenuItem);
    viewMenu.addSeparator();
    backMenuItem = new JMenuItem(RESOURCES.getString("ViewBackMenu"), createIcon("BackMenuIcon"));
    backMenuItem.addActionListener(backActionListener);
    backMenuItem.setEnabled(backButton.isEnabled());
    viewMenu.add(backMenuItem);
    forwardMenuItem = new JMenuItem(RESOURCES.getString("ViewForwardMenu"), createIcon("ForwardMenuIcon"));
    forwardMenuItem.addActionListener(forwardActionListener);
    forwardMenuItem.setEnabled(forwardButton.isEnabled());
    viewMenu.add(forwardMenuItem);
    refreshMenuItem = new JMenuItem(RESOURCES.getString("ViewRefreshMenu"), createIcon("RefreshMenuIcon"));
    refreshMenuItem.addActionListener(refreshActionListener);
    refreshMenuItem.setEnabled(refreshButton.isEnabled());
    viewMenu.add(refreshMenuItem);
    stopMenuItem = new JMenuItem(RESOURCES.getString("ViewStopMenu"), createIcon("StopMenuIcon"));
    stopMenuItem.addActionListener(stopActionListener);
    stopMenuItem.setEnabled(stopButton.isEnabled());
    viewMenu.add(stopMenuItem);
    menuBar.add(viewMenu);
  }
  
  protected Icon createIcon(String resourceKey) {
    String value = RESOURCES.getString(resourceKey);
    return value.length() == 0? null: new ImageIcon(JWebBrowser.class.getResource(value));
  }
  
  @SuppressWarnings("deprecation")
  @Override
  public void show() {
    super.show();
    nativeComponent.requestFocus();
  }
  
  public void setStatusBarVisible(boolean isStatusBarVisible) {
    statusBarPanel.setVisible(isStatusBarVisible);
    statusBarCheckBoxMenuItem.setSelected(isStatusBarVisible);
    adjustBorder();
  }
  
  public boolean isStatusBarVisible() {
    return statusBarPanel.isVisible();
  }
  
  public void setMenuBarVisible(boolean isMenuBarVisible) {
    menuBar.setVisible(isMenuBarVisible);
    adjustBorder();
  }
  
  public boolean isMenuBarVisible() {
    return menuBar.isVisible();
  }
  
  public void setButtonBarVisible(boolean isButtonBarVisible) {
    buttonBarPanel.setVisible(isButtonBarVisible);
    buttonBarCheckBoxMenuItem.setSelected(isButtonBarVisible);
    adjustBorder();
  }
  
  public boolean isButtonBarVisible() {
    return buttonBarPanel.isVisible();
  }
  
  public void setAddressBarVisible(boolean isAddressBarVisible) {
    addressBarPanel.setVisible(isAddressBarVisible);
    addressBarCheckBoxMenuItem.setSelected(isAddressBarVisible);
    adjustBorder();
  }
  
  public boolean isAddressBarVisible() {
    return addressBarPanel.isVisible();
  }
  
  public String getTitle() {
    return nativeComponent.getTitle();
  }
  
  public String getStatus() {
    return nativeComponent.getStatus();
  }

  public String getURL() {
    return nativeComponent.getURL();
  }
  
  public boolean setText(String html) {
    return nativeComponent.setText(html);
  }
  
  public boolean setURL(String url) {
    return nativeComponent.setURL(url);
  }
  
  public boolean isBackEnabled() {
    return nativeComponent.isBackEnabled();
  }
  
  public boolean back() {
    return nativeComponent.back();
  }
  
  public boolean isForwardEnabled() {
    return nativeComponent.isForwardEnabled();
  }
  
  public boolean forward() {
    return nativeComponent.forward();
  }
  
  public void refresh() {
    nativeComponent.refresh();
  }
  
  public void stop() {
    nativeComponent.stop();
  }
  
  public boolean execute(String script) {
    return nativeComponent.execute(script);
  }
  
  public int getPageLoadingProgressValue() {
    return nativeComponent.getPageLoadingProgressValue();
  }
  
  public void addWebBrowserListener(WebBrowserListener listener) {
    listenerList.add(WebBrowserListener.class, listener);
    nativeComponent.addWebBrowserListener(listener);
  }
  
  public void removeWebBrowserListener(WebBrowserListener listener) {
    listenerList.remove(WebBrowserListener.class, listener);
    nativeComponent.removeWebBrowserListener(listener);
  }
  
  public WebBrowserListener[] getListSelectionListeners() {
    return listenerList.getListeners(WebBrowserListener.class);
  }
  
  public void setBarsVisible(boolean areBarsVisible) {
    setMenuBarVisible(areBarsVisible);
    setButtonBarVisible(areBarsVisible);
    setAddressBarVisible(areBarsVisible);
    setStatusBarVisible(areBarsVisible);
  }
  
  protected void adjustBorder() {
    if(isMenuBarVisible() || isButtonBarVisible() || isAddressBarVisible() || isStatusBarVisible()) {
      webBrowserPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    } else {
      webBrowserPanel.setBorder(null);
    }
  }
  
  /**
   * The display component is the component that actually shows the web browser content.
   * This access is useful to attach listeners (key, mouse) to trap events happening in that area.
   */
  public Component getDisplayComponent() {
    return nativeComponent;
  }
  
  public void addInitializationListener(InitializationListener listener) {
    listenerList.add(InitializationListener.class, listener);
  }
  
  public void removeWebBrowserListener(InitializationListener listener) {
    listenerList.remove(InitializationListener.class, listener);
  }
  
  public InitializationListener[] getInitializationListeners() {
    return listenerList.getListeners(InitializationListener.class);
  }

  /**
   * @return true if the control was initialized. If the initialization failed, this would return true but isValidControl would return false.
   */
  public boolean isInitialized() {
    return nativeComponent.isInitialized();
  }
  
  /**
   * @return true if the component is initialized and is properly created.
   */
  public boolean isValidControl() {
    return nativeComponent.isValidControl();
  }
  
  public JMenuBar getMenuBar() {
    return menuBar;
  }
  
  public JMenu getFileMenu() {
    return fileMenu;
  }

}
