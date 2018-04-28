package dominio.servicios;

import dominio.*;

import java.text.SimpleDateFormat;
import java.util.*;
public class ServicioPrestamos {

	
	public Date obtenerFechaMaximaPrestamo(String isbn) {
		Date d = null;

		String digitos = isbn.replaceAll("\\D+", "");
		
		int sumaDigitos = 0;
		
		for (int i = 0; i < isbn.length(); i++) {
			sumaDigitos += Character.getNumericValue(isbn.charAt(i));
		}
		
		if (sumaDigitos > 30) {
			Calendar fechaMaxima = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			fechaMaxima.setTime(new Date());
			
			String fechaMaxString = sdf.format(fechaMaxima.getTime());
			
			d = new Date(fechaMaxString);
		}
		
		return d;
	}
	
	public boolean esPrestamoActivo(Prestamo prestamo) {
		Date hoy = new Date();
		
		return (hoy.equals(prestamo.getFechaSolicitud()) || hoy.after(prestamo.getFechaSolicitud())) && 
				(hoy.equals(prestamo.getFechaEntregaMaxima()) || hoy.before(prestamo.getFechaEntregaMaxima()));
	}
	
	public boolean esPalindromo(String isbn) {
		
		return isbn.equals(new StringBuilder(isbn).reverse().toString());
	}
}
