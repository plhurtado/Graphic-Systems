
package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import javax.media.j3d.Material;

public class Planeta extends Astro{
    
    public Planeta (float diametro, long velTraslacion, long velRotacion, float distancia, String texturePath, Material material){
        super (diametro, velTraslacion, velRotacion, distancia, texturePath, material);     //Creamos el planeta con el constructor de la clase que heredamos
    }
    
    //Los planetas podrán añadir satelites o anillos
    
    public void addSatelite(Satelite satelite){
        traslacion.addChild(satelite);
    }
    
    public void addAnillo(Anillo anillo){
        traslacion.addChild(anillo);
    }
}
