package com.davidlin54.fitbitchallenge;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class FitBitFetchTask extends AsyncTask<Void, Integer, Void> {
    final private static String mSocketAddress = "10.0.2.2";
    final private static int mSocketPort = 1234;
    final private static int RGB_DEFAULT = 127;

    private OnDataUpdateListener mListener;

    public FitBitFetchTask(OnDataUpdateListener listener) {
        mListener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Socket socket = null;

        try {
            int r = RGB_DEFAULT;
            int g = RGB_DEFAULT;
            int b = RGB_DEFAULT;

            socket = new Socket(mSocketAddress, mSocketPort);
            InputStream input = socket.getInputStream();

            while (socket.isConnected() && !isCancelled()) {
                if (input.available() == 0) continue;
                byte[] command = new byte[1];
                byte[] values;
                input.read(command);

                // relative
                if (command[0] == 0x1) {
                    // convert to int after fetching
                    values = new byte[6];
                    input.read(values);

                    // sourced from https://stackoverflow.com/questions/7157901/how-to-read-signed-int-from-bytes-in-java
                    int dr = (short)(values[0] << 8) | (values[1] & 0xFF);
                    int dg = (short)(values[2] << 8) | (values[3] & 0xFF);
                    int db = (short)(values[4] << 8) | (values[5] & 0xFF);

                    r += dr;
                    g += dg;
                    b += db;

                    // publish background, followed by displayed values
                    publishProgress(r, g, b, dr, dg, db);
                } else {
                    // absolute

                    values = new byte[3];
                    input.read(values);

                    r = values[0] & 0xFF;
                    g = values[1] & 0xFF;
                    b = values[2] & 0xFF;

                    // publish background, followed by displayed values
                    publishProgress(r, g, b, r, g, b);
                }
            }
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        RGBItem rgbItem = new RGBItem(values[0], values[1], values[2], values[3], values[4], values[5]);
        mListener.onDataUpdate(rgbItem);
    }
}
