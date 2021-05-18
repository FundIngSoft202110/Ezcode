package EZCode.Controladores;

import EZCode.Entidades.*;

public class ControlMetas {

    public void modificarMeta(Meta meta, int i){
        Estudiante.getInstance().getMetas().remove(i);
        Estudiante.getInstance().getMetas().add(i,meta);
    }

    public void eliminarMeta(int i){
        Estudiante.getInstance().getMetas().remove(i);
    }

    public void agregarMeta(Meta meta){
        Estudiante.getInstance().getMetas().add(meta);
    }

    public ControlMetas() { }

}
