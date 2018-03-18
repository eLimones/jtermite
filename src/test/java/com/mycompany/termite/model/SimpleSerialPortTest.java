package com.mycompany.termite.model;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleSerialPortTest {

    @Test
    public void returnsNames(){
        String[] portNames = SimpleSerialPort.getPorts();
        assertEquals(1, portNames.length);
    }

    @Test
    public void stringIsEchoed() {
        try {
            final StringBuffer buffer = new StringBuffer(100);
            String[] ports = SimpleSerialPort.getPorts();
            SimpleSerialPort port = new SimpleSerialPort(ports[0]);
            String expectedString = "this is a super value\n";
            CompletableFuture<String> future = new CompletableFuture<>();

            port.subscribe((c) -> {
                buffer.append(c);
                if(c == '\n'){
                    future.complete(buffer.toString());
                }
            });
            
            port.write(expectedString);
            assertEquals(expectedString, future.get());
            port.close();
        } catch (IOException ex) {
            Logger.getLogger(SimpleSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(SimpleSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
