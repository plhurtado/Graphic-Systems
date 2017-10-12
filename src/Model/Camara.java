package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Camara extends BranchGroup {
    
    private boolean activa;
    private TransformGroup tg;
    private ViewPlatform vp;
    private View view;
    private Canvas3D canvas;
    private Point3d posicion;   // Posición de la cámara
    private Point3d interes;    // Dónde mira
    private Vector3d vUp;       // View Up
    private double anguloEscala;        //Angulo de vision que tiene la camara
    private double planoDelantero;
    private double planoTrasero;
    
    public Camara(boolean angulo, boolean movimiento, Canvas3D canvas, Point3d posicion, Point3d interes, Vector3d vUp, double anguloEscala, double planoDelantero, double planoTrasero) {
        this.setPickable(false);      
        
        this.activa = false;            //Por defecto se crean desactivadas
        this.posicion = posicion;
        this.interes = interes;
        this.vUp = vUp;
        this.anguloEscala = anguloEscala;
        this.planoDelantero = planoDelantero;
        this.planoTrasero = planoTrasero;
        this.canvas = canvas;
        
        Transform3D ubicacion = new Transform3D();                  //Transformacion con la posicion y enfoque de la camara
        ubicacion.lookAt(this.posicion, this.interes, this.vUp);    //Se le da los valores pasados como parametros
        ubicacion.invert();
        
        tg = new TransformGroup(ubicacion);         // Se aplica la transformación al nodo de transformación
        vp = new ViewPlatform();                    // Se crea el ViewPlatform
        
        if(movimiento) { //Si se permite mover la cámara se crean los comportamientos del ratón
            tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            
            MouseRotate rotacionCam = new MouseRotate(MouseBehavior.INVERT_INPUT);
            rotacionCam.setFactor(0.005);
            rotacionCam.setTransformGroup(tg);
            rotacionCam.setSchedulingBounds(new BoundingSphere(new Point3d(), 200.0));

            MouseTranslate traslacionCam = new MouseTranslate(MouseBehavior.INVERT_INPUT);
            traslacionCam.setFactor(0.1);
            traslacionCam.setTransformGroup(tg);
            traslacionCam.setSchedulingBounds(new BoundingSphere(new Point3d(), 200.0));

            MouseWheelZoom zoom = new MouseWheelZoom(MouseBehavior.INVERT_INPUT);        //Con MouseZoom podremos hacer zoom con la rueda del raton
            zoom.setFactor(2.0);
            zoom.setTransformGroup(tg);
            zoom.setSchedulingBounds(new BoundingSphere(new Point3d(), 200.0));
            
            //Añadimos todos los movimientos del raton a la trasformacion
            tg.addChild(rotacionCam);
            tg.addChild(traslacionCam);
            tg.addChild(zoom);
        }
       
        tg.addChild(vp); //Añadimos el ViewPlatform al nodo de transformaciones
        
        view = new View(); // Se crea el view
        view.setPhysicalBody(new PhysicalBody());
        view.setPhysicalEnvironment(new PhysicalEnvironment());
        
        if(angulo) {
            view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);  //Se habilita la vista en perspectiva para las camaras de perspectiva, luna y nave
            view.setFieldOfView(Math.toRadians(this.anguloEscala));
            view.setFrontClipDistance(this.planoDelantero);
            view.setBackClipDistance(this.planoTrasero);
        } else {
            view.setProjectionPolicy(View.PARALLEL_PROJECTION);
            view.setScreenScalePolicy(View.SCALE_EXPLICIT);
            view.setScreenScale(this.anguloEscala);
            view.setFrontClipDistance(this.planoDelantero);
            view.setBackClipDistance(this.planoTrasero);
        }
        
        view.attachViewPlatform(vp); //Añadimos el ViewPlatform al View
        this.addChild(tg); 
    }
    
    public void activar() {
        if(!activa) {
            view.addCanvas3D(this.canvas);
            activa = true;
        }
    }
    
    public void desactivar() {
        if(activa) { 
            view.removeCanvas3D(this.canvas);
            activa = false;
        }
    }
    
}
