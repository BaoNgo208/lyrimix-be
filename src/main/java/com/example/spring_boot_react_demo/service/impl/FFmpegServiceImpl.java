package com.example.spring_boot_react_demo.service.impl;

import com.example.spring_boot_react_demo.service.FFmpegService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FFmpegServiceImpl implements FFmpegService {

    @Override
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

    @Override
    public String cutAudio(MultipartFile file, String startTime, String endTime) {
        try{
            File tempFile = File.createTempFile("temp_audio_",".mp3");
            file.transferTo(tempFile);

            String outputFilePath = "output_" + tempFile.getName();
            String ffmpegCommand = String.format(
                    "ffmpeg -i %s -ss %s -to %s -c copy %s",
                    tempFile.getAbsolutePath(), startTime, endTime, outputFilePath
            );


            ProcessBuilder processBuilder = new ProcessBuilder(ffmpegCommand.split(" "));
            processBuilder.inheritIO();  // Hiển thị output ra console
            Process process = processBuilder.start();
            process.waitFor();

            return "Audio file cut successfully. Output file: " + outputFilePath;

        }catch (IOException | InterruptedException e)  {
            e.printStackTrace();
            return "Error while cutting audio.";
        }

    }
    @Override
    public String mergeAudio(MultipartFile file1, MultipartFile file2) {
        try {
            // Lưu các file âm thanh tạm thời
            File tempFile1 = File.createTempFile("temp_audio_1_", ".mp3");
            File tempFile2 = File.createTempFile("temp_audio_2_", ".mp3");
            file1.transferTo(tempFile1);
            file2.transferTo(tempFile2);

            // Tạo file danh sách chứa các file cần ghép
            File listFile = File.createTempFile("fileList", ".txt");
            String listContent = "file '" + tempFile1.getAbsolutePath() + "'\n" +
                    "file '" + tempFile2.getAbsolutePath() + "'\n";
            java.nio.file.Files.write(listFile.toPath(), listContent.getBytes());

            // Lệnh FFmpeg để ghép 2 file âm thanh
            String outputFilePath = "output_merged.mp3";
            String ffmpegCommand = String.format(
                    "ffmpeg -f concat -safe 0 -i %s -c copy %s",
                    listFile.getAbsolutePath(), outputFilePath
            );

            // Thực thi lệnh FFmpeg
            ProcessBuilder processBuilder = new ProcessBuilder(ffmpegCommand.split(" "));
            processBuilder.inheritIO();  // Hiển thị output ra console
            Process process = processBuilder.start();
            process.waitFor();

            // Trả về đường dẫn tới file đầu ra
            return "Audio files merged successfully. Output file: " + outputFilePath;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error while merging audio.";
        }
    }
}
