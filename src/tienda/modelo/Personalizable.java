package tienda.modelo;

import java.util.ArrayList;

public interface Personalizable {
	ArrayList<String> getOpcionesPersonalizacion();
	void aplicarPersonalizacion(String opcion);
	double getCostePersonalizacion();
}
