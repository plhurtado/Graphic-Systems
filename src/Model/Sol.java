package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;


public class Sol extends Astro{
    private PointLight luz;
    
    public Sol (float diametro, long velRotacion, String texturePath, Material material){
        super (diametro, velRotacion, texturePath, material);
        
        rotacion = rotar();
        
        //Creamos la figura del sol y creamos el arbol
        BranchGroup figura = new BranchGroup();
        figura.addChild(new Sphere (diametro/2, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 64, apariencia)); 
        rotacion.addChild(figura);
        this.addChild(rotacion);
        
        //Creamos y colgamos la luz solar
        luz = new PointLight (
            new Color3f (1.0f, 1.0f, 1.0f), //Color
            new Point3f (0.0f, 0.0f, 0.0f), //Posicion
            new Point3f (1.0f, 0.0f, 0.0f)  //Atenuacion
        );
        luz.setInfluencingBounds (new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 200.0)); //rango de accion
        luz.setEnable (true);
        
        this.addChild(luz);
    }
    
    public void addPlaneta(Planeta planeta) {
        this.addChild(planeta);
    }
}
