package com.woody.framework.file;

import com.woody.framework.exception.ServiceException;
import com.woody.framework.utils.StringUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

public class FileDownloadUtil {

    /**
     * 下载服务器/本地  文件
     * @param localPath
     */
    public static void localFileDownload(HttpServletResponse resp, String localPath) {

        if(StringUtil.isNullOrBlank(localPath)) {
            return;
        }

        ServletOutputStream os = null;

        try {

            File file = new File(LocalFileUtil.getFullPath(localPath));

            if(!file.exists()) {
                throw new ServiceException("文件不存在");
            }
            os = resp.getOutputStream();

            IOUtils.copyLarge(new FileInputStream(file),os);

            os.flush();

        } catch (Exception e) {
            throw new ServiceException("文件读取失败！");
        }
    }
}
