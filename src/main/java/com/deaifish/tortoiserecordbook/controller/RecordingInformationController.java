package com.deaifish.tortoiserecordbook.controller;

import com.deaifish.tortoiserecordbook.bean.RecordingInformation;
import com.deaifish.tortoiserecordbook.dto.RecordingInformationAddDTO;
import com.deaifish.tortoiserecordbook.dto.RecordingInformationUpdDTO;
import com.deaifish.tortoiserecordbook.properties.PathProperties;
import com.deaifish.tortoiserecordbook.service.IMGService;
import com.deaifish.tortoiserecordbook.service.RecordingInformationService;
import com.deaifish.tortoiserecordbook.vo.RecordingInformationVO;
import com.deaifish.tortoiserecordbook.vo.ResultVO;
import com.deaifish.tortoiserecordbook.vo.WeightVO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 饲养记录控制器
 *
 * @author DEAiFISH
 * @date 2023/11/25 08:45
 */
@RestController
@Slf4j
@RequestMapping("/tortoise/recording")
@CrossOrigin()
public class RecordingInformationController {
    @Autowired
    RecordingInformationService recordingInformationService;
    @Autowired
    IMGService imgService;
    @Autowired
    PathProperties pathProperties;


    /**
     * @description 根据乌龟ID查询对应乌龟的所有体重数据
     *
     * @author DEAiFISH
     * @date 2023/11/25 08:59
     * @return java.util.List<com.deaifish.tortoiserecordbook.vo.WeightVO>
     */
    @GetMapping("/weight")
    public ResultVO<List<WeightVO>> selWeightByTID(@NotEmpty(message = "乌龟ID不能为空。") @RequestParam String tid) {
        List<RecordingInformation> recordingInformation = recordingInformationService.selRecordingInformationByTID(tid);
        List<WeightVO> weightVOS = new ArrayList<>();
        for (RecordingInformation t : recordingInformation) {
            if (t.getWeight() != null && t.getWeight() != 0) {
                weightVOS.add(WeightVO.builder().weight(t.getWeight()).size(t.getSize()).build());
            }
        }
        return new ResultVO<>(HttpStatus.OK.value(), "根据乌龟ID查询对应乌龟的所有体重数据", weightVOS);
    }

    /**
     * @description 查询所有饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/25 09:04
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.TortoiseInformation>
     */
    @GetMapping("/all")
    public ResultVO<List<RecordingInformation>> selRecordingInformationAll() {
        List<RecordingInformation> recordingInformationList = recordingInformationService.selAllRecordingInformation();
        return new ResultVO<>(HttpStatus.OK.value(), "查询所有饲养记录", recordingInformationList);
    }

    /**
     * @description 根据乌龟ID查询对应的所有饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/25 09:04
     * @param tid 乌龟ID
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.TortoiseInformation>
     */
    @GetMapping("/list")
    public ResultVO<List<RecordingInformation>> selRecordingInformationByTID(@NotEmpty(message = "乌龟ID不能为空。") @RequestParam String tid) {
        List<RecordingInformation> recordingInformationList = recordingInformationService.selRecordingInformationByTID(tid);
        return new ResultVO<>(HttpStatus.OK.value(), "根据乌龟ID查询对应的所有饲养记录", recordingInformationList);
    }

    /**
     * @description 根据饲养记录ID查询饲养记录信息
     *
     * @author DEAiFISH
     * @date 2023/11/25 09:56
     * @param iid 饲养记录ID
     * @return com.deaifish.tortoiserecordbook.vo.TortoiseInformationVO
     */
    @GetMapping("/sel")
    public ResultVO<RecordingInformationVO> selRecordingInformationByIID(@NotEmpty(message = "饲养记录ID不能为空。") @RequestParam String iid) {
        RecordingInformation recordingInformation = recordingInformationService.selRecordingInformationByIID(iid);
        RecordingInformationVO recordingInformationVO = RecordingInformationVO.builder().tid(recordingInformation.getTid())
                .iid(recordingInformation.getIid())
                .img(recordingInformation.getImg())
                .remark(recordingInformation.getRemark())
                .date(recordingInformation.getDate())
                .build();
        return new ResultVO<>(HttpStatus.OK.value(), "根据饲养记录ID查询饲养记录信息", recordingInformationVO);
    }

    /**
     * @description 添加饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/25 09:06
     * @param recordingInformation 饲养记录
     * @return boolean
     */
    @PostMapping("/add")
    public ResultVO<String> addRecordingInformation(@Validated @RequestBody RecordingInformationAddDTO recordingInformation) {
        RecordingInformation r = RecordingInformation.builder()
                .tid(recordingInformation.getTid())
                .weight(recordingInformation.getWeight())
                .date(recordingInformation.getDate())
                .size(recordingInformation.getSize())
                .remark(recordingInformation.getRemark())
                .img(recordingInformation.getImg()).build();
        recordingInformationService.addRecordingInformation(r);
        return new ResultVO<>(HttpStatus.OK.value(), "添加饲养记录", "添加成功。");
    }

    /**
     * @description 添加饲养记录照片
     *
     * @author DEAiFISH
     * @date 2023/12/6 00:31
     * @param file 记录照片文件
     * @return java.lang.String
     */
    @PostMapping("/upload-photo")
    public ResultVO<String> uploadPhotos(@NotNull(message = "文件不能为空。") @RequestParam MultipartFile[] file) {
        StringBuilder sb = new StringBuilder();
        try {
            for (MultipartFile img : file) {
                sb.append(imgService.uploadImg(img.getInputStream(), pathProperties.tortoiseRecordingDirPath))
                        .append(";");
            }
        } catch (IOException e) {
            log.info("uploadHeadTilts ==========> {}", e.getMessage());
        }
        return new ResultVO<>(HttpStatus.OK.value(), "添加饲养记录照片", sb.toString());
    }

    /**
     * @description 更新饲养记录图片
     *
     * @author DEAiFISH
     * @date 2023/11/29 21:27
     * @param files 图片文件
     * @param iid 饲养记录ID
     * @return java.lang.String
     */
    @PutMapping("/upd-photo")
    public ResultVO<String> updatePhoto(@NotNull(message = "文件不能为空。") @RequestParam MultipartFile[] files, @NotEmpty(message = "饲养记录ID不能为空。") @RequestParam String iid) {
        RecordingInformation recordingInformation = recordingInformationService.selRecordingInformationByIID(iid);
        if (recordingInformation == null) {
            return null;
        }
        StringBuilder sb = null;
        try {
            if (recordingInformation.getImg() != null && !recordingInformation.getImg().isEmpty()) {
                String[] path = recordingInformation.getImg().split(";");
                for (String img : path) {
                    imgService.delImg(img);
                }
            }
            sb = new StringBuilder();
            for (MultipartFile img : files) {
                sb.append(imgService.uploadImg(img.getInputStream(), pathProperties.tortoiseRecordingDirPath)).append(";");
            }
            recordingInformation.setImg(sb.toString());
            recordingInformationService.updRecordingInformation(recordingInformation);
        } catch (IOException e) {
            log.info("updUserPhoto ===========> {}", e.getMessage());
        }
        return new ResultVO<>(HttpStatus.OK.value(), "更新饲养记录图片", sb.toString());
    }

    /**
     * @description 修改饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/25 09:08
     * @param recordingInformation 饲养记录
     * @return boolean
     */
    @PutMapping("/upd")
    public ResultVO<String> updRecordingInformation(@Validated @RequestBody RecordingInformationUpdDTO recordingInformation) {
        RecordingInformation r = recordingInformationService.selRecordingInformationByIID(recordingInformation.getIid());
        recordingInformationService.updRecordingInformation(
                RecordingInformation.builder()
                        .iid(recordingInformation.getIid())
                        .remark(recordingInformation.getRemark())
                        .weight(recordingInformation.getWeight())
                        .img(r.getImg())
                        .size(recordingInformation.getSize())
                        .date(recordingInformation.getDate())
                        .build()
        );
        return new ResultVO<>(HttpStatus.OK.value(), "修改饲养记录", "修改成功。");
    }

    /**
     * @description 删除饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/25 09:09
     * @param iid 乌龟ID
     * @return boolean
     */
    @DeleteMapping("/del")
    public ResultVO<String> delRecordingInformationByIID(@NotEmpty(message = "饲养记录ID不能为空。") @RequestParam String iid) {
        RecordingInformation r = recordingInformationService.selRecordingInformationByIID(iid);
        if (r == null) {
            return new ResultVO<>(HttpStatus.OK.value(), "删除饲养记录", "饲养记录未找到。");
        }
        //删除饲养记录照片（OSS）
        if (r.getImg() != null && !r.getImg().isEmpty()) {
            String[] files = r.getImg().split(";");
            for (String img : files) {
                imgService.delImg(img);
            }
        }
        //删除饲养记录（数据库）
        recordingInformationService.delRecordingInformationByIID(iid);
        return new ResultVO<>(HttpStatus.OK.value(), "删除饲养记录", "删除成功。");
    }
}
