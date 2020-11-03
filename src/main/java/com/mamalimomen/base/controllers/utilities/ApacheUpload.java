package com.mamalimomen.base.controllers.utilities;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public final class ApacheUpload {

    private ApacheUpload() {
    }

    public static synchronized String fileUpload(HttpServletRequest req) {
        String path = "";
        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                List<FileItem> fileItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
                for (FileItem item : fileItems) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        path = "D:\\عزم راسخ\\جاوا مکتب\\کلاس\\46\\HW16_BackEnd\\src\\main\\webapp\\upload\\" + name;
                        item.write(new File("D:\\عزم راسخ\\جاوا مکتب\\کلاس\\46\\HW16_BackEnd\\src\\main\\webapp\\upload\\" + File.separator + name));
                    }
                }
                return path;
            } catch (Exception e) {
                return "File Upload Failed due to" + e;
            }
        } else {
            return "No File found";
        }
    }
}
