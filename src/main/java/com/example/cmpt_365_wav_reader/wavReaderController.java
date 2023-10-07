package com.example.cmpt_365_wav_reader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javax.sound.sampled.*;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;

public class wavReaderController {

    @FXML
    NumberAxis xAxis = new NumberAxis();
    @FXML
    NumberAxis yAxis = new NumberAxis();
    @FXML
    private LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

    public void chooseFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your wav file");
//        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("WAV Files", "*.wav"));
//        File wavFile =  fileChooser.showOpenDialog(null);

        fileChooser.setInitialDirectory(new File ("C:\\Users\\User\\Downloads\\test samples\\test samples\\Q1"));
        File wavFile =  fileChooser.showOpenDialog(null);

        if (wavFile != null){
            try {
                FileInputStream fileInputStream = new FileInputStream(wavFile);
                AudioFileFormat af = AudioSystem.getAudioFileFormat(wavFile);
                byte[] buffer = new byte[4];
                byte[] smallBuffer = new byte[2];

                fileInputStream.read(buffer);
                String riffID = new String(buffer, 0, 4);

                fileInputStream.read(buffer);
                int chunkSize = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

                fileInputStream.read(buffer);
                String format = new String(buffer, 0, 4);

                fileInputStream.read(buffer);
                String fmtSubchunk = new String(buffer, 0, 4);

                fileInputStream.read(buffer);
                int subChunkSize = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

                fileInputStream.read(smallBuffer, 0, 2);
                String audioFormat = new String(af.getFormat().getEncoding().toString());

                fileInputStream.read(smallBuffer, 0, 2);
                int numChannels = af.getFormat().getChannels();

                fileInputStream.read(buffer, 0, 4);
                int sampleRate = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

                fileInputStream.read(buffer);
                int byteRate = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

                fileInputStream.read(smallBuffer, 0, 2);
                short blockAlign = ByteBuffer.wrap(smallBuffer).order(ByteOrder.LITTLE_ENDIAN).getShort();

                fileInputStream.read(smallBuffer, 0, 2);
                short bitsPerSample = ByteBuffer.wrap(smallBuffer).order(ByteOrder.LITTLE_ENDIAN).getShort();


                System.out.println(riffID);
                System.out.println(chunkSize);
                System.out.println("Format: " + format);
                System.out.println("fmtSubChunk: " + fmtSubchunk);
                System.out.println("fmtSubChunkSize: " + subChunkSize);
                System.out.println("audioFormat: " + audioFormat);
                System.out.println("numChannels: " + numChannels);
                System.out.println("sampleRate: " + sampleRate);
                System.out.println("byteRate: " + byteRate);
                System.out.println("blockAlign: " + blockAlign);
                System.out.println("bitsPerSample: " + bitsPerSample);


                boolean dataNotFound = true;
                int newSubChunkSize = -1;
                String subChunkName = "nil";
                while(dataNotFound){
                    fileInputStream.read(buffer);
                    subChunkName = new String(buffer, 0, 4);

                    fileInputStream.read(buffer);
                    newSubChunkSize = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

                    if (subChunkName.equals("data")){
                        dataNotFound = false;
                    } else {
                        fileInputStream.skip(newSubChunkSize);
                    }
                }

                System.out.println("subChunk2ID: " + subChunkName);
                System.out.println("subChunk2Size: " + newSubChunkSize);

                int sampleSize = newSubChunkSize / (numChannels * (bitsPerSample/8));
                System.out.println("Sample Size: " + sampleSize);

                short[] chanel_1 = new short[sampleSize];
                String[] xValStrings = new String[sampleSize];
                for(int i=0; i<sampleSize; i++) {
                    fileInputStream.read(smallBuffer, 0, 2);
                    short amp = ByteBuffer.wrap(smallBuffer).order(ByteOrder.LITTLE_ENDIAN).getShort();
                    fileInputStream.read(smallBuffer, 0, 2);

                    xValStrings[i] = new String(String.valueOf(i));
                    chanel_1[i] = amp;
                }


                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                for(int i=0; i<sampleSize; i++) {
                    series.getData().add(new XYChart.Data(xValStrings[i], chanel_1[i]));
                    System.out.println(i);
                }

                lineChart.getData().add(series);

            } catch (Exception e) {
                System.out.println("Error reading file");
            }
        } else {
            System.out.println("Invalid File");
        }
    }

}
