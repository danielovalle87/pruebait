package com.co.pruebait.interfaces;

import java.util.Collection;

import com.co.pruebait.core.Empleado;

/**
 * Interfaz IAtencionLlamadas
 * @author Daniel
 *
 */
public interface IAtencionLlamadas {

	/**
	 * Método encargado de buscar los empleados disponibles para asignarles la llamada,
	 * inicialmente valida que haya un operador disponible, en caso que no haya, 
	 * valida que haya un supervisor disponible, en caso que no haya un supervisor disponible,
	 * valida que haya un director disponible. 
	 * Una vez valide los diferentes tipos de empleados, si no se encuentra uno disponible,
	 * retorna nulo.
	 * @param listaEmpleados
	 * @return
	 */
	Empleado buscarEmpleadoDisponible(Collection<Empleado> listaEmpleados);
	
}
