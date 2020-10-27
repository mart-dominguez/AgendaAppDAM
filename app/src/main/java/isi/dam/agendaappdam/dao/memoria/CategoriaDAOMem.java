package isi.dam.agendaappdam.dao.memoria;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import isi.dam.agendaappdam.modelo.Categoria;

public class CategoriaDAOMem  {

    private static Set<Categoria> lista = new LinkedHashSet<>();
    private static Long _ID = 10L;

    public List<Categoria> lista() {
        if(lista.isEmpty()){
            lista.add(new Categoria(1L,"Cat1"));
            lista.add(new Categoria(2L,"Cat2"));
        }
        return new ArrayList<>(lista);

    }

    public long insertar(Categoria c) {
        c.setId(_ID++);
        lista.add(c);
        return _ID;
    }
}
