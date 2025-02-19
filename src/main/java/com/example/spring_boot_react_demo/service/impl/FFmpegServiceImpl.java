package com.example.spring_boot_react_demo.service.impl;

import com.example.spring_boot_react_demo.service.FFmpegService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FFmpegServiceImpl implements FFmpegService {

    public String extractAudio(String videoPath) {
        String audioPath = videoPath.replace(".mp4", ".mp3");
        String command = "ffmpeg -i " + videoPath + " -vn -acodec mp3 " + audioPath;

        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return "Audio extraction completed, output saved to: " + audioPath;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error during audio extraction";
        }
    }
}
