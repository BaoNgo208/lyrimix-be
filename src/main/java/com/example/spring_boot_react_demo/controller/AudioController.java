package com.example.spring_boot_react_demo.controller;

import com.example.spring_boot_react_demo.service.FFmpegService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AudioController {
    private final FFmpegService ffmpegService;
    @PostMapping("/cut-audio")
    public String cutAudio(@RequestParam("file") MultipartFile file,
                           @RequestParam("start") String startTime,
                           @RequestParam("end") String endTime) {
        return  ffmpegService.cutAudio(file,startTime,endTime);
    }

    @PostMapping("/merge-audio")
    public String mergeAudio(@RequestParam("file1") MultipartFile file1,
                             @RequestParam("file2") MultipartFile file2) {
      return ffmpegService.mergeAudio(file1,file2);
    }
}
