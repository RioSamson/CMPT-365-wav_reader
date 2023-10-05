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
    private LineChart<Number, Number> lineChart;

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
//                String format = new String(af.getType().toString());
                String format = new String(buffer, 0, 4);

                fileInputStream.read(buffer);
                String fmtSubchunk = new String(buffer, 0, 4);

                fileInputStream.read(buffer);
                int subChunkSize = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

//                smallBuffer.sk
                fileInputStream.read(smallBuffer, 0, 2);
                String audioFormat = new String(af.getFormat().getEncoding().toString());

                fileInputStream.read(smallBuffer, 0, 2);
//                fileInputStream.read(buffer); //to put the buffer at the right spot
                int numChannels = af.getFormat().getChannels();

                fileInputStream.read(buffer, 0, 4);
//                int sampleRate = (int) af.getFormat().getSampleRate ();
                int sampleRate = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

                fileInputStream.read(buffer);
                int byteRate = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

                fileInputStream.read(smallBuffer, 0, 2);
                short blockAlign = ByteBuffer.wrap(smallBuffer).order(ByteOrder.LITTLE_ENDIAN).getShort();

                fileInputStream.read(smallBuffer, 0, 2);
                short bitsPerSample = ByteBuffer.wrap(smallBuffer).order(ByteOrder.LITTLE_ENDIAN).getShort();

//                fileInputStream.read(buffer);
//                String subChunk2ID = new String(buffer, 0, 4);
//
//                fileInputStream.read(buffer);
//                int subChunk2Size = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();

//                for(int i=0; i<50; i++){
//                    fileInputStream.read(smallBuffer, 0, 2);
//                    int p = ByteBuffer.wrap(new byte[]{0, 0, smallBuffer[0], smallBuffer[1]}).order(ByteOrder.LITTLE_ENDIAN).getInt();
//                    System.out.println(p);
//                }

//                fileInputStream.read(smallBuffer);
//                int sampleOne = ByteBuffer.wrap(new byte[]{0, 0, smallBuffer[0], smallBuffer[1]}).order(ByteOrder.LITTLE_ENDIAN).getInt();




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
//                    System.out.println(subChunkName);

                    if (subChunkName.equals("data")){
                        dataNotFound = false;
//                        System.out.println("good");
                    } else {
                        fileInputStream.skip(newSubChunkSize);
//                        System.out.println("skipping by: " + newSubChunkSize);
                    }
                }

                System.out.println("subChunk2ID: " + subChunkName);
                System.out.println("subChunk2Size: " + newSubChunkSize);

                int sampleSize = newSubChunkSize / (numChannels * (bitsPerSample/8));
                System.out.println("Sample Size: " + sampleSize);

//                for(int i=0; i<90000; i++){
//                    fileInputStream.read(smallBuffer, 0, 2);
//                    short amp = ByteBuffer.wrap(smallBuffer).order(ByteOrder.LITTLE_ENDIAN).getShort();
////                    System.out.println("channel 1: " + amp);
//
//                    fileInputStream.read(smallBuffer, 0, 2);
////                    amp = ByteBuffer.wrap(smallBuffer).order(ByteOrder.LITTLE_ENDIAN).getShort();
////                    System.out.println("channel 2: " + amp);
//
//                    int newAmp = (int) amp/80;
//                    if(newAmp > 0) {
//                        for (int j = 0; j < newAmp; j++) {
//                            System.out.print("*");
//                        }
//                        System.out.println();
//                    }
//
//                }


                //-----------------------
//                final NumberAxis xAxis = new NumberAxis();
//                final NumberAxis yAxis = new NumberAxis();

                XYChart.Series series = new XYChart.Series();

//                series.getData().add(new XYChart.Data("1", 5));
//                series.getData().add(new XYChart.Data("2", 200));
//                series.getData().add(new XYChart.Data("3", 2));

                for(int i=0; i<sampleSize; i++) {
                    fileInputStream.read(smallBuffer, 0, 2);
                    short amp = ByteBuffer.wrap(smallBuffer).order(ByteOrder.LITTLE_ENDIAN).getShort();
                    fileInputStream.read(smallBuffer, 0, 2);

                    String xVal = new String(String.valueOf(i));
                    series.getData().add(new XYChart.Data(xVal, amp));
//                    System.out.println(i);
                }

                lineChart.getData().add(series);


                //-------------------------
            } catch (Exception e) {
                System.out.println("Error reading file");
            }
        } else {
            System.out.println("Invalid File");
        }
    }

}




//------------------------------------------------------------------------------


//--------------------------------------------
//                AudioInputStream inStream = AudioSystem.getAudioInputStream(wavFile);
//                BufferedInputStream bStream = new BufferedInputStream(inStream);
//                AudioFileFormat af = AudioSystem.getAudioFileFormat(wavFile);
//                FileInputStream fileInputStream = new FileInputStream(wavFile);
////                AudioFormat af = inStream.getFormat();
//                byte[] buffer = new byte[4];
//
//                System.out.println("format: " + af);
//                System.out.println("Sample rate: " + af.getType());
//                System.out.println("Sample rate: " + af.getFormat().getSampleRate());
//                System.out.println("Sample rate: " + af.getFormat().getSampleSizeInBits());
//
//                //chunk ID
//                fileInputStream.read(buffer);
//                String chunkID;
//                chunkID = new String(buffer);
//                System.out.println("Chunk ID: " + chunkID);
//
//                //Chunk Size
//                fileInputStream.read(buffer);
//                int chunkSize = ((buffer[3] & 0xFF) << 24) |
//                        ((buffer[2] & 0xFF) << 16) |
//                        ((buffer[1] & 0xFF) << 8) |
//                        (buffer[0] & 0xFF);
//                System.out.println("Chunk Size: " + chunkSize);
//
//                //Format
//                fileInputStream.read(buffer);
//                String format = new String(af.getType().toString());
//                System.out.println("Format: " + format);
//
//
//
//
//
//
////                FileInputStream fileInputStream = new FileInputStream(wavFile);
//////                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
////                byte[] buffer = new byte[4];
////                int bytesRead;
////                int num = 1;
////                while((bytesRead = fileInputStream.read(buffer)) != -1) {
//////                    System.out.println("iteration: " + num + " Bytes read: " + bytesRead);
//////                    System.out.println(new String(buffer));
//////                    System.out.println( buffer[0] +" " + buffer[1] +" " + buffer[2] +" " + buffer[3] +" " );
////
////                    int value = ((buffer[3] & 0xFF) << 24) |
////                            ((buffer[2] & 0xFF) << 16) |
////                            ((buffer[1] & 0xFF) << 8) |
////                            (buffer[0] & 0xFF);
//////
//////                  System.out.println(new String(buffer));
////                    System.out.println(value);
////
//////                    System.out.println((int) buffer());
////                    num++;
////                    if(num == 44){
////                        break;
////                    }
////                }


//---------------------------------------------------------------