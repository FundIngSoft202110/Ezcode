package EZCode.Metas;

import EZCode.Entidades.*;

public class ControlMetas {
    private Estudiante estudiante;

    public ControlMetas(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
