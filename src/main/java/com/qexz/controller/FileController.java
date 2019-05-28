package com.qexz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
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
    public void fileUplaod(@RequestParam("file") MultipartFile file,
                           @RequestParam("contest")String contestId ) throws IOException {
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
            boolean result =  questionController.checkoutQuestions(file.getInputStream());
            if(result)
                questionController.insertQuestions(file.getInputStream(), Integer.parseInt(contestId));
        }

    }
}
