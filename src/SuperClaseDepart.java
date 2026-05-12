import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JFrame;
import javax.swing.JTextField;

public abstract class SuperClaseDepart extends JFrame implements InterfaceVentanaDepart {

    // Añadimos los campos que necesita el método visualiza
    protected JTextField nombre = new JTextField();
    protected JTextField loc = new JTextField();

	public SuperClaseDepart() throws HeadlessException {
		super();
	}

	public SuperClaseDepart(JFrame f) {
		super();
	}

	protected void visualiza(int dep) {
        String nom="",loca=""; 
        long pos; int depa;
        File fichero = new File("AleatorioDep.dat");
        try {
            RandomAccessFile file = new RandomAccessFile(fichero, "r");
            pos=44 * (dep-1);
            file.seek(pos); 
            depa=file.readInt();   
            System.out.println("Depart leido:" + depa);    
            char nom1[]= new char[10], aux,  loc1[]= new char[10];
            for (int i=0;i<10;i++) 
            {   aux=file.readChar();
                nom1[i]=aux;
            }
            for (int i=0;i<10;i++) 
            {   aux=file.readChar();
                loc1[i]=aux;
            }
            nom=new String (nom1);
            loca=new String (loc1);
            System.out.println("DEP: " + dep + ", Nombre: "+  nom + ", Localidad: "+ loca);  
            
            // ESTO ES LO QUE NO DEBES BORRAR:
            nombre.setText(nom);
            loc.setText(loca);

            file.close(); 
        } catch (IOException e1) {
            System.out.println("ERRROR AL LEEERRRRRR AleatorioDep.dat");    
            e1.printStackTrace();
        }   
	}

	protected void grabar(int dep, String nom, String loc) {  
        long pos; StringBuffer buffer = null;
        File fichero = new File("AleatorioDep.dat");
        try {
            RandomAccessFile file = new RandomAccessFile(fichero, "rw");
            pos=44 * (dep-1);
            file.seek(pos); 
            file.writeInt(dep);        
            buffer = new StringBuffer( nom );      
            buffer.setLength(10); 
            file.writeChars(buffer.toString());
            buffer = new StringBuffer( loc );      
            buffer.setLength(10); 
            file.writeChars(buffer.toString());
            file.close(); 
            System.out.println(" GRABADOOO el "+dep);  
        } catch (IOException e1) {
           System.out.println("ERRROR AL grabarr AleatorioDep.dat");    
            e1.printStackTrace();
        }   
	}
}