
package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Light;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;


class TheLights extends BranchGroup {
  
  TheLights () {
    Light aLight;
    
    // Se crea la luz ambiente
    aLight = new AmbientLight (new Color3f (0.2f, 0.2f, 0.2f));
    aLight.setInfluencingBounds (new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 200.0));
    aLight.setEnable(true);
    this.addChild(aLight); 
  }
  
}
