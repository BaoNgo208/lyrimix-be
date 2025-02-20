package com.example.spring_boot_react_demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface FFmpegService {
    public String extractAudio(String videoPath);
    public String cutAudio(MultipartFile file,String startTime,String endTime);

    public String mergeAudio(MultipartFile file1 ,MultipartFile file2);
}
