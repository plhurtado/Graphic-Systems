
package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Locale;
import javax.media.j3d.VirtualUniverse;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class TheUniverse {
  // Atributos de relación
  private TheBackground background;
  private TheScene scene;
  private VirtualUniverse universo;
  private Locale local;
  private Camara perspectiva;
  private Camara planta;
  private Camara luna;
  private Camara nave;
  private Pick pick;
  private TheLights luzAmbiente;
  
  public TheUniverse (Canvas3D canvas, Canvas3D canvasGrande) {
    // Se crea el universo. La parte de la vista
    universo = new VirtualUniverse();
    local = new Locale(universo);
    
    // Se crean las cuatro cámaras con sus enfoques y posicion
    planta = new Camara(false, false, canvas, new Point3d (0,200,0), new Point3d (0,0,0), new Vector3d (0,0,-1), 0.0050f, 0.3f, 100.0f);
    perspectiva = new Camara(true, true, canvasGrande, new Point3d (80,80,80), new Point3d (0,0,0), new Vector3d (0,1,0), 45.0f, 0.3f, 100.0f);
    nave = new Camara(true, false, canvasGrande, new Point3d (0,0.5,-0.25), new Point3d (0,0,1), new Vector3d (0,1,0), 90.0f, 0.1f, 30.0f);
    luna = new Camara(true, false, canvasGrande, new Point3d (0,0.5,0), new Point3d (-1,-0.25,0), new Vector3d (1,1,0), 100.0f, 0.05f, 15.0f);

    //Compilamos la dos camaras que cuelgan del local y las colgamos de este
    perspectiva.compile();
    planta.compile();
    local.addBranchGraph(perspectiva);
    local.addBranchGraph(planta);
    
    //Las dos vistas creadas serán las activas por defecto
    perspectiva.activar();
    planta.activar();
    
    // Se crea, se compila y se añade el fondo
    background = new TheBackground ();
    scene = new TheScene (luna, nave); 
    
    // Se crea el pick y se cuelga de la escena
    pick = new Pick(canvasGrande);          //Solo se podrá pinchar en la pantalla grande que es la interactiva
    pick.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 200.0));     //Radio de accion
    scene.addChild(pick);
    
    //Compilamos el fondo y la escena
    background.compile();
    scene.compile();
   
    // Se crea, se compila y se añade la luz ambiente 
    luzAmbiente = new TheLights();
    luzAmbiente.compile();
    
    //Lo añadimos todo al local
    local.addBranchGraph(luzAmbiente);
    local.addBranchGraph(background);
    local.addBranchGraph(scene);
    
    pick.iniciar(scene);    //Se inicia el pick para que se pueda pulsar en tiempo de ejecucion
  }
  
  //Funciones para activar y desactivar las diferentes camaras del canvas interactivo (el grande)
  
  public void activarCamaraPerspectiva(){
      luna.desactivar();
      nave.desactivar();
      perspectiva.activar();
  }
  
  public void activarCamaraNave(){
      luna.desactivar();
      perspectiva.desactivar();
      nave.activar();
  }
  
  public void activarCamaraLuna(){
      nave.desactivar();
      perspectiva.desactivar();
      luna.activar();
  }
  
  //Funcion para salir de la aplicacion
  public void salir (int code){
      System.exit(code);
  }
}
