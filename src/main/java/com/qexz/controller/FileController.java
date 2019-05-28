package com.qexz.controller;

import com.qexz.dto.AjaxMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

/**
 * ClassName FileController
 * Description TODO
 * Author LonelySeven
 * Date 2019/5/28 12:38
 * Version 1.0
 **/
@Controller
public class FileController {

    @Autowired
    private QuestionController questionController;


    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMsg fileUplaod(@RequestParam("file") MultipartFile file,
                              @RequestParam("contest")String contestId, HttpServletResponse response) throws IOException {
        System.out.println("文件名“" + file.getOriginalFilename());
        System.out.println("考试ID:" + contestId);

//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        //文件上传路径
////        String path = "E:/" ;
//        String path = "D:/springboot-penguin-master/src/main/resources" ;
//        //文件名（都用UUID命名吧）
//        String fileName = UUID.randomUUID() + suffix;
//        //传入路径和文件名这两个参数
//        file.transferTo(new File(path, fileName));

        if(file != null){
            StringBuffer stringBuffer =  questionController.checkoutQuestions(file.getInputStream());
            if(stringBuffer != null && !stringBuffer.equals("") && stringBuffer.length() > 0){
                return new AjaxMsg("-1",stringBuffer.toString());
            }
            else{
                questionController.insertQuestions(file.getInputStream(), Integer.parseInt(contestId));
                return new AjaxMsg("0","导入成功");
            }
        }
        else
            return new AjaxMsg("-2","文件为空");

    }
}
