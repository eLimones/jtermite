/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.termite.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author manuel
 */
public class RandomSerialPortTest {
    @Test
    public void testGetPorts() {
        try {
            String[] ports = RandomSerialPort.getPorts();
            RandomSerialPort port = new RandomSerialPort(ports[0]);
            port.listen();
            for(int i = 0; i < 100; i++){
                for (int j = 0; j < 10; j++) {
                    port.write("element " + i + ":" + j +" ");
                }
                port.write("line end \n");
            }
            Thread.sleep(1000);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RandomSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("en exception");
        } catch (IOException ex) {
            Logger.getLogger(RandomSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("io ex");
        } catch (InterruptedException ex) {
            Logger.getLogger(RandomSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
