package com.co.pruebait.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase Llamada, encargada de validar la duración de las llamadas y de realizar las llamadas.
 * @author Daniel
 *
 */
public class Llamada {

	private Integer duracion;

	/**
	 * Constructor de la clase Llamada, recibe como parámetro la duración de la llamada
	 * @param duracion
	 */
	public Llamada(Integer duracion) {
		if(duracion != null && duracion >= 0)
			this.duracion = duracion;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getDuracion() {
		return duracion;
	}

	/**
	 * 
	 * @param duracion
	 */
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	
	/**
	 * Método encargado de realizar una llamada. se establece la llamada entre la duración mínima
	 * y la duración máxima que puede tener.
	 * @param duracionMinima
	 * @param duracionMaxima
	 * @return
	 */
	public static Llamada llamar(Integer duracionMinima, Integer duracionMaxima) {
		if(duracionMinima != null && duracionMaxima != null) {
			if(duracionMaxima >= duracionMinima && duracionMinima >=0) {
				return new Llamada(ThreadLocalRandom.current().nextInt(duracionMinima, duracionMaxima +1));
			}	
		}
		return null;
	}
	
	/**
	 * Método encargado de generar una lista de llamadas. Se establece el numero de llamadas a realizar
	 * y se llama el método llamar con cada llamada obtenida.
	 * @param tamanioLista
	 * @param duracionMinima
	 * @param duracionMaxima
	 * @return
	 */
	public static List<Llamada> generarListaLlamadas(Integer tamanioLista, Integer duracionMinima, Integer duracionMaxima){
		List<Llamada> listaLlamadas = new ArrayList<>();
		if(tamanioLista != null && tamanioLista >= 0) {
			for(int i = 0; i < tamanioLista; i++) {
				listaLlamadas.add(llamar(duracionMinima, duracionMaxima));
			}
		}
		return listaLlamadas;
	}
	
	
}
