package com.woody.framework.file;

import com.woody.framework.utils.ConfigUitl;
import com.woody.framework.utils.StringUtil;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class LocalFileUtil {

    public static String getFullPath(String path) {

        return StringUtil.isNullOrBlank(path) ? null : FilenameUtils.normalizeNoEndSeparator(ConfigUitl.getUploadDir() + File.separator + path);

    }
}
