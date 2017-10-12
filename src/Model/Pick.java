
package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import com.sun.j3d.utils.pickfast.PickCanvas;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PickInfo;
import javax.media.j3d.SceneGraphPath;
import javax.media.j3d.WakeupOnAWTEvent;


public class Pick extends Behavior{
    private WakeupOnAWTEvent condition;
    private PickCanvas pickCanvas;
    private Canvas3D canvas;
    
    public Pick(Canvas3D canvas){
        this.canvas =  canvas;
        this.condition = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);        //Nuestra condicion sera que cliquemos con el raton
    }
    
    public void iniciar(BranchGroup bg){
        pickCanvas = new PickCanvas(this.canvas, bg);
        pickCanvas.setTolerance(0.0f);                  //Habrá que pinchar justo en el objeto
        pickCanvas.setMode(PickInfo.PICK_GEOMETRY);     
        pickCanvas.setFlags(PickInfo.SCENEGRAPHPATH | PickInfo.CLOSEST_INTERSECTION_POINT);
    }
    
    @Override
    public void initialize() {
        wakeupOn(condition);
    }
    
    @Override
    public void processStimulus(Enumeration cond) {
        WakeupOnAWTEvent c = (WakeupOnAWTEvent) cond.nextElement();
        AWTEvent[] e = c.getAWTEvent();
        MouseEvent mouse = (MouseEvent) e[0];
        pickCanvas.setShapeLocation(mouse);
        PickInfo pi = pickCanvas.pickClosest();
        if(pi != null) { // Si hay algo seleccionado
            SceneGraphPath sgp = pi.getSceneGraphPath();
            Astro astro = (Astro)sgp.getNode(sgp.nodeCount()-2); // Se recoge el objeto astro seleccionado
            astro.setRotationOnOff(); // Se para o vuelve a iniciar la rotación
        }
        wakeupOn(condition);
    }
}
