package dominio;

import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import dominio.excepcion.*;
import dominio.servicios.*;
import java.util.*;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;
	private ServicioPrestamos servicioPrestamos;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo, ServicioPrestamos servicioPrestamos) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;
		this.servicioPrestamos = servicioPrestamos;

	}

	public void prestar(String isbn, String nombreUsuario) {

		// Si ya esta prestado el libro, arrojar una excepcion
		if (esPrestado(isbn)) {
			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}
		
		// Si es palindromo, arrojar una excepcion
		if (esPalindromo(isbn)) {
			throw new PrestamoException("Los libros palíndromos solo se pueden utilizar en la biblioteca");
		}

		// Obtenerel libro por medio de su ISBN
		Libro l = repositorioLibro.obtenerPorIsbn(isbn);
		
		// Crear el prestamo a ser efectuado
		Prestamo p = new Prestamo(new Date(), l, obtenerFechaMaximaPrestamo(isbn), nombreUsuario);
		
		// Agregar el prestamo
		repositorioPrestamo.agregar(p);
	}

	// Determina si el libro ya ha sido prestado a la fecha de hoy
	public boolean esPrestado(String isbn) {
		Prestamo p = repositorioPrestamo.obtener(isbn);
		
		if (p == null) {
			return false;
		}
		
		return servicioPrestamos.esPrestamoActivo(p);
	}
	
	private boolean esPalindromo(String isbn) {
		
		return servicioPrestamos.esPalindromo(isbn);
	}
	
	private Date obtenerFechaMaximaPrestamo(String isbn) {
		
		return servicioPrestamos.obtenerFechaMaximaPrestamo(isbn);
	}

}
