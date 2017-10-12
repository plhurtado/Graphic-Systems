
package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;


public class Anillo extends BranchGroup{
    private float radioInterno;
    private float radioExterno;
    private long velRotacion;
    private Appearance apariencia;
    private RotationInterpolator rotator;
    private TransformGroup rotation;
    
    public Anillo (float radioInterno, float radioExterno, long velRotacion, String texturePath, Material material){
        this.radioExterno = radioExterno;
        this.radioInterno = radioInterno;
        this.velRotacion = velRotacion;
        
        //Apariencia del anillo
        apariencia = new Appearance();
        Texture textura = new TextureLoader(texturePath, null).getTexture();
        TextureAttributes atributosTextura = new TextureAttributes();
        atributosTextura.setTextureMode(atributosTextura.MODULATE);
        apariencia.setTexture(textura);
        apariencia.setTextureAttributes(atributosTextura);
        apariencia.setMaterial(material);
        
        rotation = rotar();
        BranchGroup figura = new BranchGroup();
        figura.addChild(new Torus(radioInterno, radioExterno, 64, 64, apariencia));
        rotation.addChild(figura);
        
        this.addChild(rotation);
    }
    
     private TransformGroup rotar() {
        TransformGroup t = new TransformGroup ();
        t.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); //Posibitamos que se pueda modificar en tiempo de ejecucion
        Transform3D t3d = new Transform3D ();
        Alpha value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, velRotacion, 0, 0, 0, 0, 0); //Valor numérico que se ira modificando en tiempo de ejecución
        rotator = new RotationInterpolator (value, t, t3d, 0.0f, (float) Math.PI*2.0f); // Se crea el interpolador de rotación, las figuras iran rotando
        rotator.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 200.0)); //Radio de uso
        rotator.setEnable(true);
        t.addChild(rotator); // Se cuelga del grupo de transformación
        
        return t;
    }
}


