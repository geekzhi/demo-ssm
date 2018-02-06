package com.geekzhang.demo.service.impl;

import com.geekzhang.demo.enums.ResponseCode;
import com.geekzhang.demo.mapper.UserFileMapper;
import com.geekzhang.demo.orm.UserFile;
import com.geekzhang.demo.service.UserFileService;
import com.geekzhang.demo.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: zhangpengzhi<zhang_pz @ suixingpay.com>
 * @date: 2018/2/6 下午2:10
 * @version: V1.0
 */
@Service
@Slf4j
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileMapper userFileMapper;

    @Override
    public Map<String, Object> uploadFile(String userId, MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> fileMap = FileUtil.uploadFile(file);
        if((Boolean)fileMap.get("isSuccess")) {
            log.info("文件上传|成功");
            UserFile newFile = new UserFile();
            newFile.setName((String)fileMap.get("fileName"));
            String filePath = (String)fileMap.get("path");
            filePath = filePath.split("Projects")[1];
            newFile.setPath(filePath);
            newFile.setUserId(Integer.valueOf(userId));
            userFileMapper.insert(newFile);
            log.info("文件上传|已存入userId：【{}】的文件：【{}】",userId, newFile.getName());
            map.put("code", ResponseCode.SUCCESS.getCode());
            map.put("msg", ResponseCode.SUCCESS.getDesc());
        } else {
            log.info("文件上传|失败");
            map.put("code", ResponseCode.FILE_UPLOAD_FAIL.getCode());
            map.put("msg", ResponseCode.FILE_UPLOAD_FAIL.getDesc());
        }
        return map;
    }

    @Override
    public Map<String, Object> getFileList(String userId) {
        Map<String, Object> map = new HashMap<>();
        List<UserFile> fileList = userFileMapper.getFileList(userId);
        map.put("data", fileList);
        return map;
    }
}
