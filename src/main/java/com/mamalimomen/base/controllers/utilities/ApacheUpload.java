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
        String lastFilePath = "";
        String directory = req.getServletContext().getInitParameter("uploadDirectory");
        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                List<FileItem> fileItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
                for (FileItem item : fileItems) {
                    if (!item.isFormField()) {
                        lastFilePath = new File(item.getName()).getName();
                        item.write(new File(directory + lastFilePath));
                    }
                }
                return directory + lastFilePath;
            } catch (Exception e) {
                return "File Upload Failed due to" + e;
            }
        } else {
            return "Wrong form enctype!";
        }
    }
}
