package ch.mikezweifel.tetristischcontroller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Lounge_Fragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    private static final int SERVERPORT = 5009;
    private static final String SERVER_IP = "mikezweifel.homeip.net";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lounge, container, false);

        Button b = (Button) v.findViewById(R.id.button2);
        Button c = (Button) v.findViewById(R.id.button);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        return v;
    }

    public static Lounge_Fragment newInstance() {

        Lounge_Fragment f = new Lounge_Fragment();
        Bundle b = new Bundle();

        return f;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                SeekBar sb = (SeekBar) getView().findViewById(R.id.seekBar);
                SeekBar sb2 = (SeekBar) getView().findViewById(R.id.seekBar2);
                SeekBar sb3 = (SeekBar) getView().findViewById(R.id.seekBar3);
                SeekBar sb4 = (SeekBar) getView().findViewById(R.id.seekBar4);
                try {
                    UDPCall("LOU"+String.format("%03d", sb.getProgress())+String.format("%03d", sb2.getProgress())+String.format("%04d", sb3.getProgress())+sb4.getProgress());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button:
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