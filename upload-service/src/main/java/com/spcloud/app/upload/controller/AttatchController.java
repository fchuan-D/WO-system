package com.spcloud.app.upload.controller;

import com.jntoo.db.utils.StringUtil;
import com.spcloud.app.upload.service.AttachService;
import com.spcloud.app.upload.utils.FileUnitUtils;
import com.wosys.base.utils.R;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

// 上传控制器
@RestController
@RequestMapping("/attach")
/**
 * 上传文件控制器
 */
public class AttatchController {

    /**
     * 允许上传最大限制
     */
    private static final String ALLOW_FILE_LENGTH = "10GB";

    /**
     * 允许上传的类型
     */
    private static final String[] ALLOW_EXTENSIONS = new String[]{
            // 图片
            "jpg",
            "jpeg",
            "png",
            "gif",
            "bmp",
            "webp",
            // 压缩包
            "zip",
            "rar",
            "gz",
            "7z",
            "cab",
            "tmp",
            // 音视频,
            "wav",
            "avi",
            "mp4",
            "mp3",
            "m3u8",
            "ogg",
            "wma",
            "wmv",
            "rm",
            "rmvb",
            "aac",
            "mov",
            "asf",
            "flv",
            // 文档
            "doc",
            "docx",
            "xls",
            "xlsx",
            "ppt",
            "pptx",
            "pot",
            "txt",
            "csv",
            "md",
            "pdf",
    };

    @Autowired
    private AttachService attachService;

    /**
     * 判断文件名是否允许上传
     *
     * @param fileName 文件名
     * @return
     */
    public boolean checkAllow(String fileName) {
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        if (StringUtil.isNullOrEmpty(ext)) {
            ext = "tmp";
        }
        for (String allowExtension : ALLOW_EXTENSIONS) {
            if (allowExtension.toLowerCase().equals(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测文件长度是否超出设置的值
     *
     * @param size
     * @return
     */
    public boolean checkFileSizeLength(long size) {
        if (size <= FileUnitUtils.unitToLong(ALLOW_FILE_LENGTH)) {
            return true;
        }
        return false;
    }

    /**
     * 保存文件到本地服务器
     *
     * @param fujian
     * @return
     * @throws IOException
     */
    @PostMapping("save")
    public R<Object> upload(@RequestPart(value = "fujian") MultipartFile fujian) throws IOException {
        String fileName = fujian.getOriginalFilename();
        if (!checkAllow(fileName)) {
            return R.error("文件类型不匹配，无法上传");
        }
        if (!checkFileSizeLength(fujian.getSize())) {
            return R.error("文件长度超出：" + ALLOW_FILE_LENGTH + "，无法上传");
        }
        return attachService.save(fujian);
    }
}
