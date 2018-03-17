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
import org.junit.Ignore;

public class SimpleSerialPortTest {
    
    @Ignore
    @Test
    public void returnsNames(){
        String[] portNames = SimpleSerialPort.getPorts();
        assertEquals(1, portNames.length);
    }
    
    @Ignore
    @Test
    public void ReadsAndWritesAreEchoed(){
        try {
            String[] portNames = SimpleSerialPort.getPorts();
            SimpleSerialPort port = new SimpleSerialPort(portNames[0]);
            String originalString = "hello world";
            port.write(originalString + "\n");
            String resultString = port.readLine();
            assertEquals(originalString, resultString);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SimpleSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Ignore
    @Test
    public void CanWriteManyLines(){
        try {
            String[] portNames = SimpleSerialPort.getPorts();
            SimpleSerialPort port = new SimpleSerialPort(portNames[0]);
            String originalString = "hello world";
            port.write(originalString + "\n");
            port.write(originalString + "\n");
            port.write(originalString + "\n");
            
            String resultString = port.readLine();
            assertEquals(originalString, resultString);
            
            resultString = port.readLine();
            assertEquals(originalString, resultString);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SimpleSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleSerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
