package dominio.servicios;

import dominio.*;

import java.util.*;
public class ServicioPrestamos {

	
	public Date obtenerFechaMaximaPrestamo(String isbn) {
		Date d = null;

		String digitos = isbn.replaceAll("\\D+", "");
		
		int sumaDigitos = 0;
		
		for (int i = 0; i < digitos.length(); i++) {
			sumaDigitos += Character.getNumericValue(digitos.charAt(i));
		}
		
		if (sumaDigitos > 30) {
			Calendar fechaMaxima = Calendar.getInstance();
			
			fechaMaxima.setTime(new Date());
			
			fechaMaxima.add(Calendar.DATE, 15);
			
			if (fechaMaxima.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				fechaMaxima.add(Calendar.DATE, 1);
			}
			
			d = fechaMaxima.getTime();
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
