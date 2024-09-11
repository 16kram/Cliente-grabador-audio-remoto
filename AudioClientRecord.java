package com.mycompany.audioclientrecord;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author char_
 */
public class AudioClientRecord {

    public static void main(String[] args) {
        try {
            System.out.println("Conectando al servidor de audio...");
            Socket socket = new Socket("192.168.1.139", 50005); // Dirección IP del servidor y puerto
            InputStream in = socket.getInputStream();

            // Configuración del formato de audio (Debe coincidir con el servidor)
            AudioFormat format = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED, // Codificación PCM
                    44100, // Frecuencia de muestreo (Hz)
                    16,    // Tamaño de muestra (bits)
                    2,     // Canales (estéreo)
                    4,     // Tamaño del frame (bytes por frame)
                    44100, // Frames por segundo
                    true   // Big Endian (Debe ser true para que coincida con el servidor)
            );

            // Crear un archivo WAV para guardar la grabación
            File outputFile = new File("grabacion.wav");

            // AudioInputStream para leer del InputStream del socket
            AudioInputStream audioInputStream = new AudioInputStream(in, format, AudioSystem.NOT_SPECIFIED);

            // Crear un archivo WAV para guardar la grabación
            System.out.println("Grabando audio...");
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);

            // Cerrar los recursos
            audioInputStream.close();
            in.close();
            socket.close();

            System.out.println("Grabación finalizada y guardada en " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
