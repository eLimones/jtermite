package com.mycompany.termite.model;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.stream.Stream;

public class SimpleSerialPort {

    private final SerialPort commPort;
    private final OutputStream outputStream;

    public static String[] getPorts() {
        Stream<SerialPort> stream = Arrays.stream(SerialPort.getCommPorts());
        return stream
                .map((port) -> port.getSystemPortName())
                .toArray(String[]::new);
    }
    
    private final BufferedReader bufferedReader;

    public SimpleSerialPort(String portName) throws UnsupportedEncodingException {
        this.commPort = SerialPort.getCommPort(portName);
        this.commPort.openPort();
        this.commPort.setBaudRate(9600);
        InputStream inputStream = this.commPort.getInputStream();
        this.outputStream = this.commPort.getOutputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "US-ASCII");
        bufferedReader = new BufferedReader(inputStreamReader, 1024);
    }

    public void write(String s) throws IOException {
        char[] chars = s.toCharArray();

        for (char c : chars) {
            this.outputStream.write(c);
        }
    }

    public String readLine() throws IOException {
        while(!bufferedReader.ready()){}
        return bufferedReader.readLine();
    }
}
