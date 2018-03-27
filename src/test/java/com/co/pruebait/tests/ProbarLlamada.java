package com.co.pruebait.tests;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.co.pruebait.core.Llamada;

public class ProbarLlamada {
	private Integer min = 5;
	private Integer max = 10;

	@Test
    public void testLlamar() {
        Llamada llamada = Llamada.llamar(min, max);

        assertNotNull(llamada);
    }
	
	@Test
	public void testGenerarListaLlamadas() {
		List<Llamada> listaLlamadas = Llamada.generarListaLlamadas(10, min, max);

		assertNotNull(listaLlamadas);
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testLlamarConDuracionErronea() {
        Llamada.llamar(-1, 1);
    }
}
