package main.java.EZCode.Entidades;


public class DTOEvento {
    private String horaInicial;
    private String horaFinal;
    private String nombre;
    private int ID;
    private String tipo;
    private String profesor;
    private String salon;
    private String descripcion;

    @Override
    public String toString() {
        return "DTOEvento{" +
                "horaInicial='" + horaInicial + '\'' +
                ", horaFinal='" + horaFinal + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ID=" + ID +
                ", tipo='" + tipo + '\'' +
                ", profesor='" + profesor + '\'' +
                ", salon='" + salon + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public DTOEvento(String horaInicial, String horaFinal, String nombre, int ID) {
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.nombre = nombre;
        this.ID = ID;
    }

    public DTOEvento(String horaInicial, String horaFinal, String nombre, int ID, String tipo, String profesor, String salon) {
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.nombre = nombre;
        this.ID = ID;
        this.tipo = tipo;
        this.profesor = profesor;
        this.salon = salon;
    }

    public DTOEvento(String horaInicial, String horaFinal, String nombre, int ID, String tipo, String descripcion) {
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.nombre = nombre;
        this.ID = ID;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DTOEvento() {
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
