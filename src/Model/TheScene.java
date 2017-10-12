
package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;


class TheScene extends BranchGroup {
  
  public TheScene (Camara camaraLuna, Camara camaraNave) { 
    Nave nave = new Nave("E-TIE-I/E-TIE-I.obj",10000,
        new Point3f[]{                  //Puntos por los que pasa la nave
            new Point3f(10f,10f,-10f), new Point3f(10f,15f,-5f),
            new Point3f(10f,20f,0f), new Point3f(10f,15f,5f),
            new Point3f(10f,10f,10f), new Point3f(-10f,10f,10f),
            new Point3f(-10f,10f,-10f), new Point3f(10f,10f,-10f)
        },
        new AxisAngle4f[] {             //Angulos
            new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(0)),
            new AxisAngle4f(1.0f, 0.0f, 0.0f, (float) Math.toRadians(315)),
            new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(0)),
            new AxisAngle4f(1.0f, 0.0f, 0.0f, (float) Math.toRadians(45)),
            new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(270)),
            new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(180)),
            new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(90)),
            new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(360))
        },
        new float[] {                   // knots
            0f, 0.14f, 0.28f, 0.42f, 0.56f, 0.7f, 0.84f, 1f
        }
    );
    
    Material materialAstros = new Material (
        new Color3f (0.6f, 0.6f, 0.6f), // componente ambiental
        new Color3f (0.6f, 0.6f, 0.6f), // componente difusa
        new Color3f (0.6f, 0.6f, 0.6f), // componente emisiva
        new Color3f (0.2f, 0.2f, 0.2f), // componente especular
        10f                             // brillo
    );
    Material materialAnillos = new Material (
        new Color3f (0.8f, 0.8f, 0.8f), // componente ambiental
        new Color3f (0.7f, 0.7f, 0.7f), // componente difusa
        new Color3f (0.6f, 0.6f, 0.6f), // componente emisiva 
        new Color3f (0.2f, 0.2f, 0.2f), // componente especualar
        10f                             // brillo
    );
    Material materialSol = new Material(
        new Color3f (1f, 1f, 1f), 
        new Color3f (1f, 1f, 1f), 
        new Color3f (1f, 1f, 1f), 
        new Color3f (1f, 1f, 1f), 
        100f
    );
    
    //Creamos el sol
    Sol sol = new Sol(10f, 5000l, "imgs/sol.jpg", materialSol);
    
    //Creamos los planetas
    Planeta mercurio = new Planeta (0.49f, 18000l, 10000l, 7f, "imgs/mercurio.jpg", materialAstros);
    Planeta venus = new Planeta (1.21f, 25000, -100000l, 9.5f, "imgs/venus.jpg", materialAstros);
    Planeta tierra = new Planeta (1.27f, 60000l, 3000l, 12.5f, "imgs/tierra.jpg", materialAstros);
    Planeta marte = new Planeta (0.68f, 75000l, 5000l, 15f, "imgs/marte.jpg", materialAstros);
    Planeta jupiter = new Planeta (5f, 90000l, 2900l, 23f, "imgs/jupiter.jpg", materialAstros);
    Planeta saturno = new Planeta (4f, 120000l, 2900l, 35f, "imgs/saturno.jpg", materialAstros);
    Planeta urano = new Planeta (2.6f, 150000l, 2900l, 45f, "imgs/urano.jpg", materialAstros);
    Planeta neptuno = new Planeta (2.5f, 200000l, 2900l, 51f, "imgs/neptuno.jpg", materialAstros);
    
    
    //Creamos los satelites
    Satelite luna = new Satelite(0.34f, 9000l, 0l, 1f, "imgs/luna.jpg", materialAstros);
    Satelite fobos = new Satelite(0.11f, 3100l, 3100l, 0.5f, "imgs/fobos.jpg", materialAstros);
    Satelite deimos = new Satelite(0.1f, 3200l, 3200l, 0.7f, "imgs/deimos.jpg", materialAstros);
    Satelite io = new Satelite(0.36f, 3300l, 3300l, 2.8f, "imgs/io.jpg", materialAstros);
    Satelite europa = new Satelite(0.31f, 3500l, 3500l, 3.3f, "imgs/europa.jpg", materialAstros);
    Satelite ganimedes = new Satelite(0.53f, 3750l, 3750l, 3.9f, "imgs/ganimedes.jpg", materialAstros);
    Satelite calisto = new Satelite(0.48f, 6000l, 6000l, 4.6f, "imgs/calisto.jpg", materialAstros);
    Satelite miranda = new Satelite(0.12f, 3250l, 3250l, 1.4f, "imgs/miranda.jpg", materialAstros);
    Satelite ariel = new Satelite(0.13f, 3400l, 3400l, 1.6f, "imgs/ariel.jpg", materialAstros);
    Satelite umbriel = new Satelite(0.14f, 3550l, 3550l, 2f, "imgs/umbriel.jpg", materialAstros);
    Satelite titania = new Satelite(0.16f, 4000l, 4000l, 2.3f, "imgs/titania.jpg", materialAstros);
    Satelite oberon = new Satelite(0.15f, 5000l, 5000l, 2.76f, "imgs/oberon.jpg", materialAstros);
    Satelite triton = new Satelite(0.27f, -36000l, 36000l, 1.6f, "imgs/triton.jpg", materialAstros);
    
    
    //Creamos los anillos 
    Anillo a = new Anillo(3.76f, 0.24f, 50000l, "imgs/anilloa.jpg", materialAnillos);
    Anillo b = new Anillo(3.11f, 0.39f, 50000l, "imgs/anillob.jpg", materialAnillos);
    Anillo c = new Anillo(2.375f, 0.325f, 50000l, "imgs/anilloc.jpg", materialAnillos);
  
  
    //Estructura de astros y anillos
    tierra.addSatelite(luna);
    marte.addSatelite(fobos);
    marte.addSatelite(deimos);
    jupiter.addSatelite(io);
    jupiter.addSatelite(europa);
    jupiter.addSatelite(ganimedes);
    jupiter.addSatelite(calisto);
    urano.addSatelite(miranda);
    urano.addSatelite(umbriel);
    urano.addSatelite(titania);
    urano.addSatelite(ariel);
    urano.addSatelite(oberon);
    neptuno.addSatelite(triton);
    saturno.addAnillo(a);
    saturno.addAnillo(b);
    saturno.addAnillo(c);
    
    sol.addPlaneta(mercurio);
    sol.addPlaneta(venus);
    sol.addPlaneta(tierra);
    sol.addPlaneta(marte);
    sol.addPlaneta(jupiter);
    sol.addPlaneta(saturno);
    sol.addPlaneta(urano);
    sol.addPlaneta(neptuno);
    
    //Colgamos de la escena la nave y el
    this.addChild(sol);
    this.addChild(nave);
    
    //Añadimos la camara a la nave y a la luna
    luna.addCamara(camaraLuna);
    nave.addCamara(camaraNave);
  } 
  
}

 