package gui.grafica.splash_screen;

public class ThreadSplash extends Thread {

	public ThreadSplash() {

    }
    public void run() {

        Splash sp = new Splash();
        sp.setVisible(true);

        for(int i = 0;i<13;i++){

            try {
                 ThreadSplash.sleep(500);
            }
            catch(InterruptedException e) {
            }

            sp.avanza();
        }
        
        sp.setVisible(false);

    }
}
