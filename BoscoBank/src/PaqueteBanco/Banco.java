package PaqueteBanco;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import PaqueteUtil.Utilidades;

/**
 *
 * @author Ismael
 */
public class Banco implements Serializable {
    private static final String SE_HA_PRODUCIDO_UN_ERROR_AL_ABIR_EL_ARCHIVO = "Se ha producido un error al abir el archivo:";
	private static final String SE_HA_PRODUCIDO_UN_ERROR_AL_LEER_EL_ARCHIVO = "Se ha producido un error al leer el archivo:";
	private static final long serialVersionUID = 1L; 
    private String nombre;
    private final Cuenta[] cuentas;
    private int numeroCuentas;
    private static final int MAX_CUENTAS = 100;

    public Banco(String nombre) {
        this.nombre = nombre;
        cuentas = new Cuenta[MAX_CUENTAS];
        this.numeroCuentas = 0;
    }

    // Método para serializar el curso
    public void guardarEstado(String nombreArchivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(this);
            //System.out.println("Curso serializado correctamente.");
        }
         catch (IOException e) {
            throw new IOException(SE_HA_PRODUCIDO_UN_ERROR_AL_LEER_EL_ARCHIVO + nombreArchivo);
        }

    }
    //comentario
    // Método para deserializar el curso
    public static Banco cargarEstado(String nombreArchivo) throws IOException, ClassNotFoundException {
        Banco banco;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            banco = (Banco) in.readObject();
            //System.out.println("Curso deserializado correctamente.");
        } catch (IOException e) {
            throw new IOException(SE_HA_PRODUCIDO_UN_ERROR_AL_LEER_EL_ARCHIVO + nombreArchivo);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(SE_HA_PRODUCIDO_UN_ERROR_AL_ABIR_EL_ARCHIVO + nombreArchivo);

        }
        return banco;
    }

    
    public boolean agregarCuenta(String codigo, String titular, String dni) {
        if (this.numeroCuentas >= MAX_CUENTAS) // no caben más cuentas, la tabla está llena
        {
            return false;
        } else {
            this.cuentas[numeroCuentas] = new Cuenta(codigo, titular, dni);
            numeroCuentas++;
            return true;
        }
    }

    public boolean eliminarCuenta(String codigo) {
        int pos = this.buscarCuenta(codigo);
        if (pos >= 0) {
            for (int i = pos; i < numeroCuentas - 1; i++) {
                cuentas[i] = cuentas[i + 1];
            }
            cuentas[numeroCuentas - 1] = null; 
            this.numeroCuentas--;
            return true;
        } else {
            return false;
        }
    }

    private int buscarCuenta(String codigo) {
        // se busca secuencialmente la cuenta con un código y se devuelve su posición en la tabla    
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIban().equals(codigo)) {
                return i;
            }
        }
        return -1;
    }

    public boolean ingresar(String codigo, double importe) {
        Cuenta c = localizarCuenta(codigo);
        if (c == null) // no se encuentra una cuenta con ese codigo
        {
            return false;
        } else {
            c.ingresar(importe);
            return true;
        }
    }

    public boolean retirar(String codigo, double importe) {
        Cuenta c = localizarCuenta(codigo);
        if (c == null) // no se encuentra una cuenta con ese codigo
        {
            return false;
        } else {
            c.retirar(importe);
            return true;
        }
    }

    public boolean existeCuenta(String codigo) {
        return (localizarCuenta(codigo) != null);
    }

    public String consultarCuenta(String codigo) {
        Cuenta c = localizarCuenta(codigo);
        if (c != null) {
            return c.toString();
        } else {
            return "";
        }
    }

    public double informaSaldo(String codigo) {
        Cuenta c = localizarCuenta(codigo);
        if (c != null) {
            return c.getSaldo();
        } else {
            return -100000000;
        }
    }

    private Cuenta localizarCuenta(String codigo) {
        // se busca secuencialmente la cuenta con un código       
         {for (int i=0;i<this.numeroCuentas;i++){
            if (cuentas[i].getIban().equals(codigo)) 
                return cuentas[i];
            }
        }
        return null;
    }

    public StringBuilder mostrarCuentas() {
        StringBuilder salida = new StringBuilder("");

        for (int i=0;i<this.numeroCuentas;i++){
            
            salida.append(cuentas[i].toString());
            salida.append("\n");
        }
        return salida;
    }

    public void mostrarDatos() {
        System.out.println(this.nombre);
        System.out.println("Cuentas actuales " + this.numeroCuentas);
        for (int i=0;i<this.numeroCuentas;i++){
            System.out.print(cuentas[i].toString());
            System.out.println("");
        }

    }

    public StringBuilder informeCuentas() {
        float totalSaldo = 0;
        int numCuentas = 0;
        StringBuilder salida = new StringBuilder("");
        salida.append("   Código de cuenta               Titular                  DNI       Saldo     \n");
        salida.append("======================== ============================== ========= =============\n");
        for (int i=0;i<this.numeroCuentas;i++){
            salida.append(cuentas[i].toString());
            salida.append("\n");
            totalSaldo += cuentas[i].getSaldo();
            numCuentas++;
        }
        
        salida.append(String.format("Número total de cuentas %3d                                      %8.2f \n",
                numCuentas, totalSaldo));
        return salida;

    }

    public void cargarDatos() {
        String cuenta, titular, dni;
        for (int i = 0; i < MAX_CUENTAS; i++) {
            cuenta = String.format("Cuenta%3d", i);
            titular = String.format("Titular %3d", i);
            char letra=Utilidades.calcularLetraNIF(i);
            dni = String.format("%08d%c", i,letra);
            this.agregarCuenta(cuenta, titular, dni);
        }

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroCuentas() {
        return numeroCuentas;
    }

    public void setNumCuentas(int numCuentas) {
        this.numeroCuentas = numCuentas;
    }

}
