
package Model;

/**
 *
 * @author plhurtado y CCarcedo
 */

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedGeometryArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3f;

public class Torus extends Shape3D {
    
    public Torus(float rad1, float rad2, int res1, int res2, Appearance ap) {
        if(res1<3) res1 = 3;    // el valor mínimo de resolución es 3
        if(res2<2) res2 = 2;    // el valor mínimo de resolución es 2
        setGeometry(crearGeometria(rad1, rad2, res1, res2));    // Crea geometría con normales y coordenadas de textura
        setAppearance(ap);  // Copia la apariencia pasada como parámetro
    }

    private IndexedGeometryArray crearGeometria(float rad1, float rad2, int res1, int res2) {
        // Cálculo de vértices e índices
        Point3f[] vertices = calcularVertices(rad1, rad2, res1, res2);  // Array con coordenads de los vértices
        Vector3f[] normales = calcularNormales(res1, res2);             // Array con las normales de los vértices
        TexCoord2f[] texCoords = calcularTexCoords(res1, res2);         // Array con coordenadas de textura
        int[] indicesVertices = calcularIndicesVertices(res1, res2);    // Array con índices de vértices y normales
        int[] indicesTexCoords = calcularIndicesTexCoords(res1, res2);  // Array con índices de coordenadas de textura
        // Creación del GeometryArray
        IndexedGeometryArray geometria = new IndexedTriangleArray(
                texCoords.length, // Tamaño vértices: texCoords>(vertices=normales)
                GeometryArray.COORDINATES | GeometryArray.NORMALS |  GeometryArray.TEXTURE_COORDINATE_2, // Flags
                indicesVertices.length); // Tamaño de índices: indicesVertices=indicesTexCoords
        // Asignación de valores
        geometria.setCoordinates(0, vertices);                          // Se asignan vértices
        geometria.setCoordinateIndices(0, indicesVertices);             // Se asignan índices de vértices
        geometria.setNormals(0, normales);                              // Se asignan normales
        geometria.setNormalIndices(0, indicesVertices);                 // Se asignan índices de normales
        geometria.setTextureCoordinates(0, 0, texCoords);               // Se asignan coordenadas de textura
        geometria.setTextureCoordinateIndices(0, 0, indicesTexCoords);  // Se asignan índices de coordenadas de textura
        
        return geometria;
    }

    private Point3f[] calcularVertices(float rad1, float rad2, int res1, int res2) {
        Point3f p;
        Transform3D t1, t2, t3;
        float alfa, beta;
        Point3f[] vertices = new Point3f[res1*res2];
        for(int i=0; i<res1; i++) { // Círculo exterior
            alfa = (float)(i*2*Math.PI/res1);
            for(int j=0; j<res2; j++) { // Círculo interior
                beta = (float)(j*2*Math.PI/res2);
                p = new Point3f(rad2, 0f, 0f);  // Primer punto en rad2,0,0. El resto se calcula con transformaciones
                t1 = new Transform3D();
                t1.rotZ(beta);  // Rotación círculo interior
                t2 = new Transform3D();
                t2.setTranslation(new Vector3f(rad1, 0f, 0f));  // Traslación radio entre origen y centro de círculo interior
                t3 = new Transform3D();
                t3.rotY(alfa);  // Rotación círculo exterior
                t1.transform(p);
                t2.transform(p);
                t3.transform(p);
                vertices[i*res2+j] = p; // Añadimos vértice
            }
        }
        
        return vertices;
    }

    private Vector3f[] calcularNormales(int res1, int res2) {
        Vector3f n;
        Transform3D t1, t3;
        float alfa, beta;
        Vector3f[] normales = new Vector3f[res1*res2];
        for(int i=0; i<res1; i++) { // Círculo exterior
            alfa = (float)(i*2*Math.PI/res1);
                for(int j=0; j<res2; j++) { // Círuclo interior
                    beta = (float)(j*2*Math.PI/res2);
                    n = new Vector3f(1f, 0f, 0f); // Primera normal 1,0,0. El resto se calcula con transformaciones
                    t1 = new Transform3D();
                    t1.rotZ(beta);  // Rotación cículo interior
                    t3 = new Transform3D();
                    t3.rotY(alfa);  // Rotación círculo exterior
                    t1.transform(n);
                    t3.transform(n);
                    n.normalize();  // Normalizar vector
                    normales[i*res2+j] = n; // Añadimos normal
            }
        }

        return normales;
    }
    
    private TexCoord2f[] calcularTexCoords(int res1, int res2) {
        // Para los extremos que se acaban juntando un mismo puntos
        // en dos triángulos distintos tiene un valor distinto
        // por lo que el tamaño será mayor
        TexCoord2f[] texCoords = new TexCoord2f[(res1+1)*(res2+1)];
        TexCoord2f texCoord;
        float s, t;
        for(int i=0; i<res1+1; i++) {
            s = (float)i/res1;  // s constante para todas las filas de una misma columna
            for(int j=0; j<res2+1; j++) {
                t = (float)j/res2;
                texCoord = new TexCoord2f(s,t);
                texCoords[i*(res2+1)+j] = texCoord; // Añadimos coordenada de textura
            }
        }
        
        return texCoords;
    }
    
    private int[] calcularIndicesVertices(int res1, int res2) {
        int[] indicesVertices = new int[res1*2*res2*3];
        int cnt = 0, supIzda, supDcha, infIzda, infDcha;
        for(int i=0; i<res1; i++) {
            for(int j=0; j<res2; j++) {
                supIzda = res2*i+j;
                supDcha = supIzda+res2;
                infIzda = supIzda+1;
                infDcha = infIzda+res2;
                if(i==res1-1) { // En la última columna se repiten vértices
                    supDcha = j;
                    infDcha = j+1;
                }
                if(j==res2-1) { // En la última fila se repiten vértices
                    infIzda -= res2;
                    infDcha -= res2;
                }
                indicesVertices[cnt] = (supIzda)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (infDcha)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (infIzda)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (supIzda)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (supDcha)%(res1*res2);
                cnt++;
                indicesVertices[cnt] = (infDcha)%(res1*res2);
                cnt++;
            }
        }
        
        return indicesVertices;
    }
    
    private int[] calcularIndicesTexCoords(int res1, int res2) {
        int[] indicesTexCoords = new int[res1*2*res2*3];
        int cnt = 0, supIzda, supDcha, infIzda, infDcha;
        for(int i=0; i<res1; i++) {
            for(int j=0; j<res2; j++) {
                supIzda = i*(res2+1)+j;
                supDcha = supIzda+(res2+1);
                infIzda = supIzda+1;
                infDcha = infIzda+(res2+1);
                indicesTexCoords[cnt] = supIzda;
                cnt++;
                indicesTexCoords[cnt] = infDcha;
                cnt++;
                indicesTexCoords[cnt] = infIzda;
                cnt++;
                indicesTexCoords[cnt] = supIzda;
                cnt++;
                indicesTexCoords[cnt] = supDcha;
                cnt++;
                indicesTexCoords[cnt] = infDcha;
                cnt++;
            }
        }
        
        return indicesTexCoords;
    }
    
}