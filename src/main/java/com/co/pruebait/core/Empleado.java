package com.co.pruebait.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import com.co.pruebait.constantes.EstadosEmpleado;
import com.co.pruebait.enums.TipoEmpleado;

/**
 * Clase EmpleadoDTO, encargada de definir el tipo y el estado del empleado.
 * También es encargada de asignar las llamadas y los estados a los empleados
 * @author Daniel
 *
 */
public class Empleado implements Runnable {
	
	private TipoEmpleado tipoEmpleado;
	
	private String estadoEmpleado;

	private ConcurrentLinkedDeque<Llamada> llamadasEntrantes;

    private ConcurrentLinkedDeque<Llamada> llamadasAtendidas;

    /**
     * Constructor de la clase Empleado, recibe como parámetro el tipo de empleado
     * @param tipoEmpleado
     */
    public Empleado(TipoEmpleado tipoEmpleado) {
    	if(tipoEmpleado != null) {
    		this.tipoEmpleado = tipoEmpleado;
            this.estadoEmpleado = EstadosEmpleado.ESTADO_DISPONIBLE;
            this.llamadasEntrantes = new ConcurrentLinkedDeque<Llamada>();
            this.llamadasAtendidas = new ConcurrentLinkedDeque<Llamada>();	
    	}
    }

    /**
     * 
     * @return
     */
	public TipoEmpleado getTipoEmpleado() {
		return tipoEmpleado;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEstadoEmpleado() {
		return estadoEmpleado;
	}
	
	/**
	 * 
	 * @param estadoEmpleado
	 */
	public void setEstadoEmpleado(String estadoEmpleado) {
		this.estadoEmpleado = estadoEmpleado;
	}
	
	/**
	 * Método atenderLlamada, obtiene una llamada y la agrega a las llamadas entrantes 
	 * para ser atentida por un empleado
	 * @param llamada
	 */
	public synchronized void atenderLlamada(Llamada llamada) {
		this.llamadasEntrantes.add(llamada);
	}
	
	
	/**
	 * Método utilizado para obtener las llamadas atendidas por el empleado
	 * @return
	 */
	public synchronized List<Llamada> getLlamadasAtendidas() {
        return new ArrayList<>(llamadasAtendidas);
    }

	/**
	 * Método encargado de validar que hayan llamadas entrantes, en caso que haya una llamada entrante
	 * se asigna la llamada a un empleado y se cambia su estado a 'No Disponible'.
	 * Una vez termine la llamada se cambia el estado del empleado a 'Disponible' 
	 * y se agrega la llamada a la lista de llamadas atendidas
	 */
	@Override
	public void run() {
		while(true) {
			if(this.llamadasEntrantes != null && !this.llamadasEntrantes.isEmpty()) {
				Llamada llamada = llamadasEntrantes.poll();
				this.setEstadoEmpleado(EstadosEmpleado.ESTADO_NO_DISPONIBLE);
				try {
					TimeUnit.SECONDS.sleep(llamada.getDuracion());
				} catch(InterruptedException ie) {
					ie.printStackTrace();
				} finally {
					this.setEstadoEmpleado(EstadosEmpleado.ESTADO_DISPONIBLE);
				}
				this.llamadasAtendidas.add(llamada);
			}
		}
	}
	
    
}
