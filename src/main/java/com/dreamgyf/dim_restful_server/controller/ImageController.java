package com.dreamgyf.dim_restful_server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.dreamgyf.dim_restful_server.entity.dao.AvatarEntity;
import com.dreamgyf.dim_restful_server.mapper.AvatarMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private AvatarMapper avatarMapper;

    @RequestMapping("/avatar/get/{id}")
    @ResponseBody
    public void getAvatar(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        String path = System.getProperty("user.dir");
        if(id == 0) {
            path += "/data/image/avatar/default.jpg";
        }
        else {
            AvatarEntity avatar = avatarMapper.selectById(id);
            if(avatar == null)
                return;
            path += avatar.getPath();
        }
        
        response.setContentType("image/jpeg");
        File file = new File(path);
        InputStream in = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while((len = in.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        os.close();
        in.close();
    }

}