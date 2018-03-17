package com.mycompany.termite.model;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.stream.Stream;

public class RandomSerialPort {

    private final SerialPort commPort;
    private final OutputStream outputStream;

    public static String[] getPorts() {
        Stream<SerialPort> stream = Arrays.stream(SerialPort.getCommPorts());
        return stream
                .map((port) -> port.getSystemPortName())
                .toArray(String[]::new);
    }

    public RandomSerialPort(String portName) throws UnsupportedEncodingException {
        this.commPort = SerialPort.getCommPort(portName);
        this.commPort.openPort();
        this.commPort.setBaudRate(9600);
        this.outputStream = this.commPort.getOutputStream();
    }

    public void write(String s) throws IOException {
        char[] chars = s.toCharArray();

        for (char c : chars) {
            this.outputStream.write(c);
        }
    }

    public void listen() {
        this.commPort.addDataListener(new SerialPortPacketListener() {
            
            StringBuffer charBuffer = new StringBuffer(1000);
            
            @Override
            public int getPacketSize() {
                return 1;
            }

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                char c = (char) event.getReceivedData()[0];
                if(c == '\n'){
                    String newString = charBuffer.toString();
                    charBuffer = new StringBuffer(1000);
                    System.out.println(newString);
                }else{
                    charBuffer.append(c);
                }
            }
        });
    }
}
