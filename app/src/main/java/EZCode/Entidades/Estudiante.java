package EZCode.Entidades;

import java.io.Serializable;
import java.util.ArrayList;

public class Estudiante implements Serializable {
    private String Nombre;
    private String Correo;
    private String Password;
    private String ID;
    private int EZpuntos;
    private ArrayList<Meta> Metas;
    private ArrayList<Evento> horario;
    private static Estudiante instance;


    public ArrayList<Evento> getHorario() {
        return horario;
    }

    public void setHorario(ArrayList<Evento> horario) {
        this.horario = horario;
    }

    public static synchronized Estudiante getInstance(){
        if(instance == null){
            instance = new Estudiante();
        }
        return instance;
    }
    public static synchronized void setInstance(Estudiante estudiante){
        instance = estudiante;
    }
    public Estudiante() {
        this.Metas = new ArrayList<>();
        this.horario = new ArrayList<>();
    }


    public Estudiante(String nombre, String correo, String password, int EZpuntos) {

        Nombre = nombre;
        Correo = correo;
        Password = password;
        this.ID = ID;
        this.EZpuntos = EZpuntos;
        this.Metas = new ArrayList<>();
        this.horario = new ArrayList<>();
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getEZpuntos() {
        return EZpuntos;
    }

    public void setEZpuntos(int EZpuntos) {
        this.EZpuntos = EZpuntos;
    }

    public ArrayList<Meta> getMetas() {
        return Metas;
    }

    public void setMetas(ArrayList<Meta> metas) {
        Metas = metas;
    }
}
