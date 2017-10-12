
package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import javax.media.j3d.Material;

public class Satelite extends Astro{
    
    public Satelite (float diametro, long velTraslacion, long velRotacion, float distancia, String texturePath, Material material){
        super (diametro, velTraslacion, velRotacion, distancia, texturePath, material);
    } 
    
    //Tendremos un metodo para añadir camara ya que la luna dispondra de una
     public void addCamara(Camara camara){
        rotacion.addChild(camara);
    }
}
