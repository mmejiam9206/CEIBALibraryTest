package dominio.servicios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import dominio.Libro;
import dominio.Prestamo;
import dominio.servicios.ServicioPrestamos;
import testdatabuilder.*;

public class ServicioPrestamosTest {

	private static final String ISBN_PALINDROMO = "DS1331SD";
	
	private static final String ISBN_DIGITOS_30 = "A874B69Q";
	
	private static final String FECHA_INICIAL_PRESTAMO = "2018-03-12";
	private static final String FECHA_FINAL_PRESTAMO = "2018-03-27";
	private static final String NOMBRE_USUARIO = "mmejiam9206";
	
	@Test
	public void testISBNEsPalindromo() {
		ServicioPrestamos sp = new ServicioPrestamos();
		
		assertTrue(sp.esPalindromo(ISBN_PALINDROMO));
	}
	
	@Test
	public void testISBNNoEsPalindromo() {
		ServicioPrestamos sp = new ServicioPrestamos();
		
		assertFalse(sp.esPalindromo(ISBN_DIGITOS_30));		
	}
	
	@Test
	public void testEsPrestamoActivoEsTrue() {
		Libro libro = new LibroTestDataBuilder().build();
		Date fechaInicial = new Date();
		
		Calendar fechaMaxima = Calendar.getInstance();
		fechaMaxima.setTime(new Date());
		
		fechaMaxima.add(Calendar.DATE, 15);
		
		if (fechaMaxima.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			fechaMaxima.add(Calendar.DATE, 1);
		}
		
		Date fechaFinal = fechaMaxima.getTime();
		
		Prestamo prestamo = new Prestamo(fechaInicial, libro, fechaFinal, NOMBRE_USUARIO);
		ServicioPrestamos sp = new ServicioPrestamos();
		
		assertTrue(sp.esPrestamoActivo(prestamo));
	}
	
	@Test
	public void testObtenerFechaMaximaPrestamoEsNullISBNNoSuma30() {
		ServicioPrestamos sp = new ServicioPrestamos();
		
		Date d = sp.obtenerFechaMaximaPrestamo(ISBN_PALINDROMO);
		
		assertNull(d);
	}
	
	@Test
	public void testObtenerFechaMaximaPrestamoEsFechaValida() {
		Calendar fechaMaxima = Calendar.getInstance();
		fechaMaxima.setTime(new Date());
		
		fechaMaxima.add(Calendar.DATE, 15);
		
		if (fechaMaxima.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			fechaMaxima.add(Calendar.DATE, 1);
		}
		
		Date d = fechaMaxima.getTime();
		ServicioPrestamos sp = new ServicioPrestamos();
		
		assertEquals(d, sp.obtenerFechaMaximaPrestamo(ISBN_DIGITOS_30));
	}
}
