/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.grafica.splash_screen;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;
import javax.swing.JWindow;

//************************************************************************************//
/**
 * Clase que representa un Splash Screen.
 * 
 * @author S@L-c
 */
public class SplashScreen extends JWindow {
	
	// CONSTANTES
	private static final long serialVersionUID = 1L;
	public static final String RUTA_RECURSOS = "/recursos/interfaz/splash_screen/";
	
	// ATRIBUTOS
	private Image _imagen;
	private int _X, _Y, _ancho, _alto;

//	************************************************************************************//
	/**
	 * Constructor de la clase SplashScreen.
	 * 
	 * @param ruta Ruta donde se encuentra la imagen a dibujar.
	 */
	public SplashScreen() {
		
		super(new Frame());

		try {
		
			Toolkit toolkit = Toolkit.getDefaultToolkit();

			// Obtenemos la imagen
			_imagen = toolkit.getImage(getClass().getResource(RUTA_RECURSOS + "eGorillaSplash.jpg"));

			MediaTracker mediaTracker = new MediaTracker(this);
			mediaTracker.addImage(_imagen, 0);
			mediaTracker.waitForID(0);

			_ancho = _imagen.getWidth(this);
			_alto = _imagen.getHeight(this);

			Dimension screenSize = toolkit.getScreenSize();

			_X = (screenSize.width - _ancho) / 2;
			_Y = (screenSize.height - _alto) / 2;
		} 
		catch (Exception exception) {
		
			exception.printStackTrace();
			_imagen = null;
		}
	}

//	************************************************************************************//
	/**
	 * Constructor de la clase SplashScreen.
	 * 
	 * @param imagen Imagen a dibujar.
	 */
	public SplashScreen(Image imagen) {
		
		super(new Frame());

		try {

			Toolkit toolkit = Toolkit.getDefaultToolkit();

			_imagen = imagen;

			MediaTracker mediaTracker = new MediaTracker(this);
			mediaTracker.addImage(_imagen, 0);
			mediaTracker.waitForID(0);

			_ancho = _imagen.getWidth(this);
			_alto = _imagen.getHeight(this);

			Dimension screenSize = toolkit.getScreenSize();

			_X = (screenSize.width - _ancho) / 2;
			_Y = (screenSize.height - _alto) / 2;
		} 
		catch (Exception exception) {
		
			exception.printStackTrace();
			_imagen = null;
		}
	}

//	************************************************************************************//
	/**
	 * Abre el splash screen y lo mantiene abierto la duración especificada o hasta
	 * que se cierre explícitamente.
	 * 
	 * @param milisegundos Milisegundos que está visible el Splash Screen.
	 */
	public void open(int milisegundos) {
		
		if (_imagen == null)
			return;

		Timer timer = new Timer(Integer.MAX_VALUE, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				((Timer) event.getSource()).stop();
				close();
			};
		});

		timer.setInitialDelay(milisegundos);
		timer.start();

		setBounds(_X, _Y, _ancho, _alto);
		setVisible(true);
	}

//	************************************************************************************//
	/**
	 * Cierra el splash screen.
	 */
	public void close() {
		
		setVisible(false);
		dispose();
	}

//	************************************************************************************//
	/**
	 * Dibuja el splash screen.
	 * 
	 * @param graphics La instancia graphics.
	 */
	public void paint(Graphics graphics) {

		if (_imagen == null)
			return;
		
		graphics.drawImage(_imagen, 0, 0, _ancho, _alto, this);
	}
}