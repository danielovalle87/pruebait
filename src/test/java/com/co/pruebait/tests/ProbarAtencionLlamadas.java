package com.co.pruebait.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.co.pruebait.constantes.EstadosEmpleado;
import com.co.pruebait.core.AtencionLlamadas;
import com.co.pruebait.core.Empleado;
import com.co.pruebait.enums.TipoEmpleado;

public class ProbarAtencionLlamadas {
	
	Empleado operator;
	Empleado supervisor;
	Empleado director;
	AtencionLlamadas atencion;
	
	public ProbarAtencionLlamadas() {
		this.operator = new Empleado(TipoEmpleado.OPERADOR);
		this.supervisor = new Empleado(TipoEmpleado.SUPERVISOR);
		this.director = new Empleado(TipoEmpleado.SUPERVISOR);
		this.atencion = new AtencionLlamadas();
	}

	@Test
    public void testAsignarLlamadaAOperador() {
        List<Empleado> listaEmpleados = Arrays.asList(this.operator, this.supervisor, this.director);

        Empleado empleado = this.atencion.buscarEmpleadoDisponible(listaEmpleados);

        assertNotNull(empleado);
        assertEquals(TipoEmpleado.OPERADOR, empleado.getTipoEmpleado());
    }

    @Test
    public void testAsignarLlamadaASupervisor() {
    	this.operator.setEstadoEmpleado(EstadosEmpleado.ESTADO_NO_DISPONIBLE);
        List<Empleado> listaEmpleados = Arrays.asList(this.operator, this.supervisor, this.director);

        Empleado empleado = this.atencion.buscarEmpleadoDisponible(listaEmpleados);

        assertNotNull(empleado);
        assertEquals(TipoEmpleado.SUPERVISOR, empleado.getTipoEmpleado());
    }

    @Test
    public void testASignarLlamadaADirector() {
    	this.operator.setEstadoEmpleado(EstadosEmpleado.ESTADO_NO_DISPONIBLE);
    	this.supervisor.setEstadoEmpleado(EstadosEmpleado.ESTADO_NO_DISPONIBLE);
        List<Empleado> listaEmpleados = Arrays.asList(this.operator, this.supervisor, this.director);

        Empleado empleado = this.atencion.buscarEmpleadoDisponible(listaEmpleados);

        assertNotNull(empleado);
        assertEquals(TipoEmpleado.DIRECTOR, empleado.getTipoEmpleado());
    }
    
    @Test
    public void testNoAsignarLlamada() {
    	this.operator.setEstadoEmpleado(EstadosEmpleado.ESTADO_NO_DISPONIBLE);
    	this.supervisor.setEstadoEmpleado(EstadosEmpleado.ESTADO_NO_DISPONIBLE);
    	this.director.setEstadoEmpleado(EstadosEmpleado.ESTADO_NO_DISPONIBLE);
        List<Empleado> listaEmpleados = Arrays.asList(this.operator, this.supervisor, this.director);

        Empleado empleado = this.atencion.buscarEmpleadoDisponible(listaEmpleados);

        assertNull(empleado);
    }
	
	
}
