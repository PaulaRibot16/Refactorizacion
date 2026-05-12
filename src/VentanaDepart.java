import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.*;

public class VentanaDepart extends SuperClaseDepart implements ActionListener, InterfaceVentanaDepart {
	
    private static final long serialVersionUID = 1L;
    JTextField num = new JTextField(10);
    // Nota: nombre y loc se heredan de SuperClaseDepart, no hace falta redeclararlos aquí
    
    JLabel mensaje = new JLabel(" ----------------------------- ");
    JLabel titulo = new JLabel("GESTIÓN DE DEPARTAMENTOS.");
    JLabel lnum = new JLabel("NUMERO DEPARTAMENTO:");
    JLabel lnom = new JLabel("NOMBRE:");
    JLabel lloc = new JLabel("LOCALIDAD:");

    JButton balta = new JButton("Insertar Depar.t");
    JButton consu = new JButton("Consultar Depart.");
    JButton borra = new JButton("Borrar Depart.");
    JButton breset = new JButton("Limpiar datos.");
    JButton modif = new JButton("Modificar Departamento.");
    JButton ver = new JButton("Ver por consola.");
    JButton fin = new JButton("CERRAR");

    public VentanaDepart(JFrame f) { 	
        super(f);
        setTitle("GESTIÓN DE DEPARTAMENTOS.");
        
        JPanel p0 = new JPanel();
        p0.add(titulo);
        p0.setBackground(Color.CYAN);
                
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(lnum);
        p1.add(num);
        p1.add(consu);
        
        JPanel p2 = new JPanel();
        p2.add(lnom);
        p2.add(nombre);
        
        JPanel p3 = new JPanel();
        p3.add(lloc);
        p3.add(loc);
        
        JPanel p4 = new JPanel();
        p4.setBackground(Color.YELLOW);
        p4.add(balta); p4.add(borra); p4.add(modif);
        
        JPanel p5 = new JPanel();
        p5.setBackground(Color.PINK);
        p5.add(breset); p5.add(ver); p5.add(fin);

        JPanel p7 = new JPanel();
        p7.add(mensaje);
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 
        add(p0); add(p1); add(p2); add(p3); add(p4); add(p5); add(p7);
        pack();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Listeners
        balta.addActionListener(this);
        breset.addActionListener(this);
        fin.addActionListener(this);
        consu.addActionListener(this);
        borra.addActionListener(this);
        modif.addActionListener(this);
        ver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        String departamentoExiste = "DEPARTAMENTO EXISTE.";
        String PRUEBA = "PRUEBA";

        if (e.getSource() == balta) altadepart(e, departamentoExiste, PRUEBA);
        if (e.getSource() == consu) consuldepart(e, departamentoExiste, PRUEBA);
        if (e.getSource() == borra) borrardepart(e, departamentoExiste, PRUEBA);
        if (e.getSource() == modif) modifdepart(e, departamentoExiste, PRUEBA);
        
        if (e.getSource() == fin) System.exit(0);
        
        if (e.getSource() == ver) {
            try {
                mensaje.setText("Visualizando por consola...");
                verporconsola();
            } catch (IOException e1) {
                mensaje.setText("ERROR AL LEER.");
            }
        }
        if (e.getSource() == breset) {
            num.setText(""); nombre.setText(""); loc.setText("");
            mensaje.setText("Datos limpios.");
        }
    }

    // --- MÉTODOS DE LA INTERFAZ ---

    @Override
    public void altadepart(ActionEvent e, String departamentoExiste, String PRUEBA) {
        try {
            int dep = Integer.parseInt(num.getText());
            if (dep > 0) {
                if (consultar(dep)) mensaje.setText(departamentoExiste);
                else {
                    grabar(dep, nombre.getText(), loc.getText());
                    mensaje.setText("GRABADO OK.");
                }
            }
        } catch (Exception ex) { mensaje.setText("ERROR EN ALTA."); }
    }

    @Override
    public void consuldepart(ActionEvent e, String departamentoExiste, String PRUEBA) {
        try {
            int dep = Integer.parseInt(num.getText());
            if (consultar(dep)) {
                mensaje.setText(departamentoExiste);
                visualiza(dep);
            } else mensaje.setText("NO EXISTE.");
        } catch (Exception ex) { mensaje.setText("ERROR CONSULTA."); }
    }

    @Override
    public void borrardepart(ActionEvent e, String departamentoExiste, String PRUEBA) {
        try {
            int dep = Integer.parseInt(num.getText());
            if (consultar(dep)) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Borrar?");
                if (confirm == 0) {
                    borrar(dep);
                    mensaje.setText("BORRADO.");
                }
            }
        } catch (Exception ex) { mensaje.setText("ERROR BORRADO."); }
    }

    @Override
    public void modifdepart(ActionEvent e, String departamentoExiste, String PRUEBA) {
        try {
            int dep = Integer.parseInt(num.getText());
            if (consultar(dep)) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Modificar?");
                if (confirm == 0) {
                    modificar(dep);
                    mensaje.setText("MODIFICADO.");
                }
            }
        } catch (Exception ex) { mensaje.setText("ERROR MODIF."); }
    }

    // --- MÉTODOS AUXILIARES Y CLASE ANIDADA ---

    public void verporconsola() throws IOException {
        claseAnidada cA = new claseAnidada();
        cA.entrada();
        // Lógica de lectura...
    }

    boolean consultar(int dep) throws IOException {
        File fichero = new File("AleatorioDep.dat");
        if (!fichero.exists()) return false;
        RandomAccessFile file = new RandomAccessFile(fichero, "r");
        try {
            file.seek(44 * (dep - 1));
            int depa = file.readInt();
            file.close();
            return depa > 0;
        } catch (Exception ex) { return false; }
    }

    void borrar(int dep) throws IOException {
        // Lógica de borrado (poner a 0)
    }

    void modificar(int dep) throws IOException {
        // Lógica de grabación
    }

    // Los métodos JButton que pide la interfaz (los que me pasaste antes)
    @Override public JButton altaDepart(String p) { return balta; }
    @Override public JButton borraDepart(String p) { return borra; }
    @Override public JButton consulDepart(String p) { return consu; }
    @Override public JButton modifDepart(String p) { return modif; }

    // Clase anidada requerida
    class claseAnidada {
        void entrada() { System.out.println("Entrada en clase anidada."); }
        int salida(int x) { return x; }
    }

} // Fin de la clase