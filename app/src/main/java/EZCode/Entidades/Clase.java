package main.java.EZCode.Entidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Clase extends Evento{
    private String profesor;
    private String salon;

    public Clase(Calendar horaInicial, Calendar horaFinal, String nombre, String profesor, String salon) {
        super(horaInicial, horaFinal, nombre);
        this.profesor = profesor;
        this.salon = salon;
    }
    public Clase(Calendar horaInicial, Calendar horaFinal, String nombre, String profesor, String salon, int ID) {
        super(horaInicial, horaFinal, nombre, ID);
        this.profesor = profesor;
        this.salon = salon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clase)) return false;
        if (!super.equals(o)) return false;
        Clase clase = (Clase) o;
        return Objects.equals(getProfesor(), clase.getProfesor()) &&
                Objects.equals(getSalon(), clase.getSalon());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProfesor(), getSalon());
    }

    @Override
    public String toString() {
        return "Clase{" +
                super.toString() +
                "profesor='" + profesor + '\'' +
                ", salon='" + salon + '\'' +
                '}';
    }


    public Clase() {
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

}
