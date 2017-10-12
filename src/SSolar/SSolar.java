
package SSolar;

import Control.GUI.ControlWindow;
import Model.TheUniverse;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;

/**
 *
 * @author plhurtado
 */
public class SSolar {

  public static void main(String[] args) {

    // Se crean las dos pantallas (Canvas) que tendrá la aplicacion con la configuracion del sistema
    Canvas3D canvasPlanta = new Canvas3D (SimpleUniverse.getPreferredConfiguration());
    Canvas3D canvasGrande = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    
    // Tamaño de las pantallas
    canvasPlanta.setSize(400, 400);
    canvasGrande.setSize(1000, 700);

    // Se crea el Universo
    TheUniverse universe = new TheUniverse(canvasPlanta, canvasGrande);
    
    // Se crea la GUI (control) a partir del Canvas3D y del Universo
    ControlWindow controlWindow = new ControlWindow(canvasPlanta, canvasGrande, universe);
    
    // Se muestra la ventana principal de la aplicación
    controlWindow.showWindow ();
  }
  
}
