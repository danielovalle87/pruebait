package com.co.pruebait.dispatcher;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.co.pruebait.core.AtencionLlamadas;
import com.co.pruebait.core.Empleado;
import com.co.pruebait.core.Llamada;
import com.co.pruebait.interfaces.IAtencionLlamadas;

/**
 * Clase Dispatcher, encargada de manejar las llamadas y asignarlas a los empleados disponibles
 * @author Daniel
 *
 */
public class Dispatcher implements Runnable {
	
	public static final Integer MAXIMO_DE_HILOS = 10;
	
	private Boolean hiloActivo;
	
	private ExecutorService executorService;
	
	private ConcurrentLinkedDeque<Empleado> empleados;
	
	private ConcurrentLinkedDeque<Llamada> llamadasEntrantes;
	
	private IAtencionLlamadas procesoAtencionLlamadas;
	
	
	/**
	 * Constructor de la clase Dispatcher
	 * @param empleados
	 */
	public Dispatcher(List<Empleado> empleados) {
		this(empleados, new AtencionLlamadas());
	}
	
	/**
	 * Constructor de la clase Dispatcher
	 * @param empleados
	 * @param procesoAtencionLlamadas
	 */
	public Dispatcher(List<Empleado> empleados, IAtencionLlamadas procesoAtencionLlamadas) {
		
		if(empleados != null && procesoAtencionLlamadas != null) {
			this.empleados = new ConcurrentLinkedDeque<Empleado>(empleados);
			this.procesoAtencionLlamadas = procesoAtencionLlamadas;
			this.llamadasEntrantes = new ConcurrentLinkedDeque<Llamada>();
			this.executorService = Executors.newFixedThreadPool(MAXIMO_DE_HILOS);
		}
		
	}
	
	/**
	 * Método encargado de agregar las llamadas entrantes
	 * @param llamada
	 */
	public synchronized void dispatchCall(Llamada llamada) {
		this.llamadasEntrantes.add(llamada);
	}
	
	/**
	 * Método encargado de ejecutar los hilos por cada empleado
	 */
	public synchronized void iniciarHilo() {
		this.hiloActivo = true;
		for(Empleado empleado : this.empleados) {
			this.executorService.execute(empleado);
		}
	}
	
	/**
	 * Método encargado de detener los hilos
	 */
	public synchronized void detenerHilo() {
		this.hiloActivo = false;
		this.executorService.shutdown();
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized Boolean getHiloActivo() {
        return hiloActivo;
    }

	/**
	 * Método encargado de validar que hayan llamadas entrantes, en caso que hayan llamadas entrantes
	 * se buscan los empleados que estén disponibles y se les asigna la llamada. 
	 * Si los empleados no se encuentran disponibles, se encola la llamada hasta que haya uno disponible
	 */
	@Override
	public void run() {
		while(getHiloActivo()) {
			if(this.llamadasEntrantes.isEmpty()) {
				continue;
			}
			else {
				Empleado empleado = this.procesoAtencionLlamadas.buscarEmpleadoDisponible(this.empleados);
				if(empleado == null) {
					continue;
				}
				else {
					Llamada llamada = this.llamadasEntrantes.poll();
					try {
						empleado.atenderLlamada(llamada);
					} catch(Exception e) {
						this.llamadasEntrantes.addFirst(llamada);
					}
				}
			}
		}
	}

}
