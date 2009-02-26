/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;
import javax.swing.JWindow;

/**
 * Class representing an application splash screen.
 * 
 */
public class SplashScreen extends JWindow
{
  private Image  image_;
  private int    x_, y_, width_, height_;



  /**
   * 
   */
  public SplashScreen (String imageFileName)
  {
    super (new Frame());

    try {
      Toolkit toolkit = Toolkit.getDefaultToolkit();

      image_ = toolkit.getImage (imageFileName);

      MediaTracker mediaTracker = new MediaTracker (this);
      mediaTracker.addImage (image_, 0);
      mediaTracker.waitForID (0);

      width_  = image_.getWidth (this);
      height_ = image_.getHeight (this);

      Dimension screenSize = toolkit.getScreenSize();

      x_ = (screenSize.width  - width_)  / 2;
      y_ = (screenSize.height - height_) / 2;
    }
    catch (Exception exception) {
      exception.printStackTrace();
      image_ = null;
    }
  }

    SplashScreen(Image image) {
        super (new Frame());

    try {
      Toolkit toolkit = Toolkit.getDefaultToolkit();

      image_ = image;

      MediaTracker mediaTracker = new MediaTracker (this);
      mediaTracker.addImage (image_, 0);
      mediaTracker.waitForID (0);

      width_  = image_.getWidth (this);
      height_ = image_.getHeight (this);

      Dimension screenSize = toolkit.getScreenSize();

      x_ = (screenSize.width  - width_)  / 2;
      y_ = (screenSize.height - height_) / 2;
    }
    catch (Exception exception) {
      exception.printStackTrace();
      image_ = null;
    }
    }

  /**
   * Open the splash screen and keep it open for the specified duration
   * or until close() is called explicitly.
   */
  public void open (int nMilliseconds)
  {
    if (image_ == null) return;

    Timer timer = new Timer (Integer.MAX_VALUE, new ActionListener() {
        public void actionPerformed (ActionEvent event) {
          ((Timer) event.getSource()).stop();
          close();
        };
      });

    timer.setInitialDelay (nMilliseconds);
    timer.start();

    setBounds (x_, y_, width_, height_);
    setVisible (true);
  }

  /**
   * Close the splash screen.
   */
  public void close()
  {
    setVisible (false);
    dispose();
  }

  /**
   * Paint the splash screen window.
   *
   * @param graphics  The graphics instance.
   */
  public void paint (Graphics graphics)
  {
    System.out.println ("paint");
    if (image_ == null) return;
    graphics.drawImage (image_, 0, 0, width_, height_, this);
  }
}