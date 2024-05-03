package com.spcloud.app.upload.service.impl;

import cn.hutool.core.io.FileUtil;
import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.upload.config.Configure;
import com.spcloud.app.upload.service.AttachService;
import com.wosys.base.utils.R;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service("AttachService")
public class LocalAttachService implements AttachService {

    @Override
    public R<Object> save(MultipartFile fujian) throws IOException {
        String fileName = fujian.getOriginalFilename();
        String suffix = FileUtil.getSuffix(fileName);
        String md5 = DigestUtils.md5DigestAsHex(fujian.getInputStream());
        if (StringUtil.isNullOrEmpty(suffix)) {
            suffix = "tmp";
        }

        String newName = md5 + "." + suffix;
        String uploadPath = Configure.UPLOAD_DIR + newName;
        File file = new File(uploadPath);
        FileUtils.forceMkdirParent(file);

        FileUtils.copyInputStreamToFile(fujian.getInputStream(), file);
        String url = Configure.FILE_PREFIX + newName;
        return R.success(url);
    }
}
