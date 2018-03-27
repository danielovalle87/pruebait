package com.co.pruebait.core;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.co.pruebait.constantes.EstadosEmpleado;
import com.co.pruebait.enums.TipoEmpleado;
import com.co.pruebait.interfaces.IAtencionLlamadas;

/**
 * Clase AtencionLlamadas, encargada de buscar los empleados que se enceuntran disponibles para recibir la llamada
 * @author Daniel
 *
 */
public class AtencionLlamadas implements IAtencionLlamadas {

	/**
	 * @see IAtencionLlamadas
	 */
	@Override
	public Empleado buscarEmpleadoDisponible(Collection<Empleado> listaEmpleados) {
		if(listaEmpleados != null) {
			List<Empleado> empleadosDisponibles = listaEmpleados.stream().filter(
					empleado -> empleado.getEstadoEmpleado().equals(EstadosEmpleado.ESTADO_DISPONIBLE)).collect(Collectors.toList());
			Optional<Empleado> empleado = empleadosDisponibles.stream().filter(
					emp -> emp.getTipoEmpleado().equals(TipoEmpleado.OPERADOR)).findAny();
			if(!empleado.isPresent()) {
				empleado = empleadosDisponibles.stream().filter(
						emp -> emp.getTipoEmpleado().equals(TipoEmpleado.SUPERVISOR)).findAny();
				if(!empleado.isPresent()) {
					empleado = empleadosDisponibles.stream().filter(
							emp -> emp.getTipoEmpleado().equals(TipoEmpleado.DIRECTOR)).findAny();
					if(!empleado.isPresent()) {
						return null;
					}
				}
			}
			return empleado.get();
		}
		else {
			return null;
		}
	}

}
