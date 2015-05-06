package server.service;

import server.model.PrefetchInstruction;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by florian on 06.05.15.
 */
public class Downloader {

    public void receiveMessage(PrefetchInstruction instruction) {
        System.out.println("Received <" + instruction.getContentName() + ">");
        DownloadThread dt = new DownloadThread();
        dt.instruction = instruction;
        Thread t = new Thread(dt);
        t.run();
    }

    class DownloadThread implements Runnable {

        PrefetchInstruction instruction;

        @Override
        public void run() {
            try {
                String path = "http://" +
                        instruction.getLocationInfo().getLocations().get(0) +
                        "/media/" + instruction.getContentName();

                URL website = new URL(path);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos;
                fos = new FileOutputStream(instruction.getContentName());
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
