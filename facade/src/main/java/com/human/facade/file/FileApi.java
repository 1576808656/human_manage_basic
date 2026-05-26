package com.human.facade.file;

import com.human.common.http.ResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileApi {

    @PostMapping("/common/upload")
    ResponseDTO upload(@RequestParam("file") MultipartFile file);
}
