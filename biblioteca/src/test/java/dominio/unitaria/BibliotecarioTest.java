package dominio.unitaria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.Prestamo;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import dominio.servicios.ServicioPrestamos;
import testdatabuilder.LibroTestDataBuilder;

public class BibliotecarioTest {

	private static final String NOMBRE_USUARIO = "mmejiam9206";
	
	@Test
	public void esPrestadoTest() {
		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		
		Libro libro = libroTestDataBuilder.build(); 
		
		Prestamo prestamo = new Prestamo(new Date(), libro, null, NOMBRE_USUARIO);
		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		ServicioPrestamos servicioPrestamos = mock(ServicioPrestamos.class);
		
		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);
		when(repositorioPrestamo.obtener(libro.getIsbn())).thenReturn(prestamo);
		
		when(servicioPrestamos.esPrestamoActivo(prestamo)).thenReturn(true);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo, servicioPrestamos);
		
		// act 
		boolean esPrestado =  bibliotecario.esPrestado(libro.getIsbn());
		
		//assert
		assertTrue(esPrestado);
	}
	
	@Test
	public void libroNoPrestadoTest() {
		
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		
		Libro libro = libroTestDataBuilder.build(); 
		
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
		ServicioPrestamos servicioPrestamos = mock(ServicioPrestamos.class);
		
		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);
		when(repositorioPrestamo.obtener(libro.getIsbn())).thenReturn(null);
		when(servicioPrestamos.esPrestamoActivo(null)).thenReturn(false);
		
		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo, servicioPrestamos);
		
		// act 
		boolean esPrestado =  bibliotecario.esPrestado(libro.getIsbn());
		
		//assert
		assertFalse(esPrestado);
	}
}
