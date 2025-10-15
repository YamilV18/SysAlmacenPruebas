package pe.edu.upeu.sysalmacen.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.Categoria;
import pe.edu.upeu.sysalmacen.modelo.Marca;
import pe.edu.upeu.sysalmacen.repositorio.ICategoriaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ICategoriaRepositoryTest {
    @Autowired
    private ICategoriaRepository categoriaRepository;
    private static Long categoriaId;

    @BeforeEach
    public void setUp() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Adolescente");
        Categoria guardada = categoriaRepository.save(categoria);
        categoriaId = guardada.getIdCategoria(); // Guardamos el ID para pruebas posteriores
    }

    @Test
    @Order(1)
    public void testGuardarCategoria() {
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre("Infantil");
        Categoria guardada = categoriaRepository.save(nuevaCategoria);
        assertNotNull(guardada.getIdCategoria());
        assertEquals("Infantil", guardada.getNombre());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
        assertTrue(categoria.isPresent());
        assertEquals("Adolescente", categoria.get().getNombre());
    }
    @Test
    @Order(3)
    public void testActualizarCategoria() {
        Categoria categoria = categoriaRepository.findById(categoriaId).orElseThrow();
        categoria.setNombre("Niños");
        Categoria actualizada = categoriaRepository.save(categoria);
        assertEquals("Niños", actualizada.getNombre());
    }
    @Test
    @Order(4)
    public void testListarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        assertFalse(categorias.isEmpty());
        System.out.println("Total categorias registradas: " + categorias.size());
        for (Categoria m: categorias){
            System.out.println(m.getNombre()+"\t"+m.getIdCategoria());
        }
    }
    @Test
    @Order(5)
    public void testEliminarCategoria() {
        categoriaRepository.deleteById(categoriaId);
        Optional<Categoria> eliminada = categoriaRepository.findById(categoriaId);
        assertFalse(eliminada.isPresent(), "La categoria debería haber sido eliminada");
    }
}
