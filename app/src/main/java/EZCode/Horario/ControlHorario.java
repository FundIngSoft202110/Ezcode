package EZCode.Horario;

import EZCode.Entidades.Estudiante;

public class ControlHorario {
    private Estudiante estudiante;

    public ControlHorario(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
