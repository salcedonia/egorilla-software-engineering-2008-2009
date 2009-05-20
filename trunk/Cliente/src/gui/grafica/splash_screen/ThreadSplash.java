/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/

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
