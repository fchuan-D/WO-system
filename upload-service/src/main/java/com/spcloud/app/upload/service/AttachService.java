package com.spcloud.app.upload.service;

import com.wosys.base.utils.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachService {
    public R<Object> save(MultipartFile fujian) throws IOException;
}
