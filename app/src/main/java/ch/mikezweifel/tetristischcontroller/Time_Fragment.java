package ch.mikezweifel.tetristischcontroller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Time_Fragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    private static final int SERVERPORT = 5009;
    private static final String SERVER_IP = "mikezweifel.homeip.net";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_time, container, false);

        FloatingActionButton b = (FloatingActionButton) v.findViewById(R.id.button5);
        FloatingActionButton c = (FloatingActionButton) v.findViewById(R.id.button6);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        return v;
    }

    public static Time_Fragment newInstance() {

        Time_Fragment f = new Time_Fragment();

        return f;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button5:
                try {
                    UDPCall("TIMStart",v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button6:
                try {
                    UDPCall("AbOrTTrObA",v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public void UDPCall(final String UDPmess,View v) {
        class ClientThread implements Runnable {

            @Override
            public void run() {

                try {
                    InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                    DatagramSocket socket = new DatagramSocket(null);
                    socket.setReuseAddress(true);
                    socket.bind(new InetSocketAddress(SERVERPORT));
                    int msg_length= UDPmess.length();
                    byte[] message = UDPmess.getBytes();
                    DatagramPacket p = new DatagramPacket(message, msg_length,serverAddr,SERVERPORT);
                    socket.send(p);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        }
        new Thread(new ClientThread()).start();
        Snackbar.make(v, "UDP message \""+UDPmess+"\" sent successfully", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show();
    }
}