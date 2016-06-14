package ch.mikezweifel.tetristischcontroller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Tetris_Fragment extends android.support.v4.app.Fragment  implements View.OnClickListener {
    private static final int SERVERPORT = 5009;
    private static final String SERVER_IP = "mikezweifel.homeip.net";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tetris, container, false);

        Button b = (Button) v.findViewById(R.id.button3);
        Button c = (Button) v.findViewById(R.id.button4);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        return v;
    }
    public static Tetris_Fragment newInstance() {

        Tetris_Fragment f = new Tetris_Fragment();

        return f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3:
                EditText et = (EditText) getView().findViewById(R.id.editText);
                if (et.getText().toString().equals("")) {
                    break;
                } else {
                    try {
                        UDPCall("TET"+et.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.button4:
                try {
                    UDPCall("AbOrTTrObA");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public void UDPCall(final String UDPmess) {
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
        Toast.makeText(getActivity(), "UDP message \""+ UDPmess +"\" sent successfully", Toast.LENGTH_SHORT).show();
    }

}