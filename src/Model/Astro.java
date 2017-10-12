
package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Astro extends BranchGroup {
    
    //Algunos atributos tendran visibilidad de paquete para que se pueda acceder a ellos desde las subclases
    private boolean movimiento;
    private float diametro;
    private long velTraslacion;
    private long velRotacion;
    private float distancia; //Con respecto al sol en caso de planeta o con respecto a su planeta en caso de satelite
    Appearance apariencia;
    private RotationInterpolator rotador;
    private RotationInterpolator rotadorSobre;
    private TransformGroup rotacionSobre;   //Rotar con respecto al sol o a su planeta (en caso de satelite)
    TransformGroup rotacion;                //Rotacion sobre si mismo
    TransformGroup traslacion;              //Movimiento
 
    //Constructor general
    public Astro (float diametro, long velTraslacion, long velRotacion, float distancia, String texturePath, Material material){
        this.setPickable(true);                         //Los astros se podran clicar para que dejen de rotar (incluido el sol)
        this.setCapability(Node.ENABLE_PICK_REPORTING); //Hacemos que dicha modificacion pueda ser en tiempo de ejecucion
        
        this.movimiento = true;                         //Por defecto todos estarán en movimiento
        this.diametro = diametro;
        this.velTraslacion = velTraslacion;
        this.velRotacion = velRotacion;
        this.distancia = distancia;
        
        //Creación de la apariencia
        Texture texture = new TextureLoader (texturePath, null).getTexture();
        TextureAttributes textureAttributes = new TextureAttributes(); 
        textureAttributes.setTextureMode(TextureAttributes.MODULATE);
        apariencia = new Appearance();
        apariencia.setTexture(texture);
        apariencia.setTextureAttributes(textureAttributes);
        apariencia.setMaterial(material);
        
        //Creamos las transformaciones del planeta
        rotacion = rotar();
        traslacion = trasladar(); 
        rotacionSobre = rotarAlrededor(); 
        
        //Creamos la figura del planeta
        BranchGroup figure = new BranchGroup(); 
        figure.addChild(new Sphere (this.diametro/2, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 64, apariencia)); 

        //Creamos el arbol con la figura y todas las transformaciones del planeta
        rotacion.addChild(figure); 
        traslacion.addChild(rotacion); 
        rotacionSobre.addChild(traslacion); 
        this.addChild(rotacionSobre);
    }
    
    //Constructor para el sol
    public Astro(float diametro, long velRotacion, String texturePath, Material material) {
        this.setPickable(true);
        this.setCapability(Node.ENABLE_PICK_REPORTING);
        
        this.diametro = diametro;
        this.velTraslacion = 0l;
        this.velRotacion = velRotacion;
        this.distancia = 0.0f;
        
        //Creación de la apariencia
        Texture texture = new TextureLoader (texturePath, null).getTexture();
        TextureAttributes textureAttributes = new TextureAttributes(); 
        textureAttributes.setTextureMode(TextureAttributes.MODULATE);
        apariencia = new Appearance();
        apariencia.setTexture(texture);
        apariencia.setTextureAttributes(textureAttributes);
        apariencia.setMaterial(material);
    }
    
    public void setRotationOnOff() { //Activamos o desactivamos el giro del planeta con el raton
        movimiento = !movimiento; 
        rotador.setEnable(movimiento);
    }
    
    protected TransformGroup rotar() {
        TransformGroup t = new TransformGroup ();
        t.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); //Damos la posibilidad de que cambie en tiempo de ejecucion
        Transform3D t3d = new Transform3D (); //Matriz de rotación
        Alpha value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, velRotacion, 0, 0, 0, 0, 0); // Valor numérico que se ira modificando en tiempo de ejecución
        rotador = new RotationInterpolator (value, t, t3d, 0.0f, (float) Math.PI*2.0f); // Se crea el interpolador de rotación, las figuras iran rotando
        rotador.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 200.0));
        rotador.setEnable(true);
        t.addChild(rotador);
        
        return t;
    }
    
    private TransformGroup rotarAlrededor() {
        TransformGroup t = new TransformGroup ();
        t.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); 
        Transform3D t3d = new Transform3D (); 
        Alpha value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, velTraslacion, 0, 0, 0, 0, 0);
        rotadorSobre = new RotationInterpolator (value, t, t3d, 0.0f, (float) Math.PI*2.0f);
        rotadorSobre.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 200.0));
        rotadorSobre.setEnable(true);
        t.addChild(rotadorSobre);
        
        return t;
    }

    private TransformGroup trasladar() {
        TransformGroup t = new TransformGroup (); 
        Transform3D t3d = new Transform3D ();               //Matriz de traslación
        t3d.setTranslation(new Vector3f(distancia,0,0) );   //Definimos la traslación
        t.setTransform(t3d); 
        
        return t;
    }
}
