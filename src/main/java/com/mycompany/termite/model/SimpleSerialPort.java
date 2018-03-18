package com.mycompany.termite.model;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.stream.Stream;



public class SimpleSerialPort {

    private final SerialPort commPort;
    private final OutputStream outputStream;
    private Subject<Character> subject;
    private boolean isListening = false;

    public static String[] getPorts() {
        Stream<SerialPort> stream = Arrays.stream(SerialPort.getCommPorts());
        return stream
                .map((port) -> port.getSystemPortName())
                .toArray(String[]::new);
    }

    public SimpleSerialPort(String portName) throws UnsupportedEncodingException {
        this.commPort = SerialPort.getCommPort(portName);
        this.commPort.openPort();
        this.commPort.setBaudRate(9600);
        this.outputStream = this.commPort.getOutputStream();
        this.subject = new Subject<>();
    }

    public void write(String s) throws IOException {
        char[] chars = s.toCharArray();

        for (char c : chars) {
            this.outputStream.write(c);
        }
    }

    public void subscribe(Observer<Character> observer){
        this.subject.subscribe(observer);
        this.startListening();
    }

    private void startListening(){
        if(!isListening){
            this.listen();
            this.isListening = true;
        }
    }

    private void listen() {
        this.commPort.addDataListener(new SerialPortPacketListener() {
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
                subject.next(c);
            }
        });
    }

    public void close(){
        this.commPort.closePort();
    }
}
