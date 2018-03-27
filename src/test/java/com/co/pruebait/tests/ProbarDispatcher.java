package com.co.pruebait.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.co.pruebait.core.Empleado;
import com.co.pruebait.core.Llamada;
import com.co.pruebait.dispatcher.Dispatcher;
import com.co.pruebait.enums.TipoEmpleado;


public class ProbarDispatcher {


    @Test
    public void testDispatchCall() throws InterruptedException {
        List<Empleado> listaEmpleados = new ArrayList<>();
        listaEmpleados.add(new Empleado(TipoEmpleado.OPERADOR));
        listaEmpleados.add(new Empleado(TipoEmpleado.OPERADOR));
        listaEmpleados.add(new Empleado(TipoEmpleado.OPERADOR));
        listaEmpleados.add(new Empleado(TipoEmpleado.SUPERVISOR));
        listaEmpleados.add(new Empleado(TipoEmpleado.SUPERVISOR));
        listaEmpleados.add(new Empleado(TipoEmpleado.SUPERVISOR));
        listaEmpleados.add(new Empleado(TipoEmpleado.DIRECTOR));
        listaEmpleados.add(new Empleado(TipoEmpleado.DIRECTOR));
        listaEmpleados.add(new Empleado(TipoEmpleado.DIRECTOR));
        Dispatcher dispatcher = new Dispatcher(listaEmpleados);
        dispatcher.iniciarHilo();
        TimeUnit.SECONDS.sleep(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(dispatcher);
        TimeUnit.SECONDS.sleep(1);

        Llamada.generarListaLlamadas(10, 5, 10).stream().forEach(llamada -> {
        	dispatcher.dispatchCall(llamada);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                fail();
            }
        });

        executorService.awaitTermination(10 * 2, TimeUnit.SECONDS);
        assertEquals(10, listaEmpleados.stream().mapToInt(empleado -> empleado.getLlamadasAtendidas().size()).sum());
    }

    
}
