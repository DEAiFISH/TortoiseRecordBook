package com.deaifish.tortoiserecordbook.controller;

import com.deaifish.tortoiserecordbook.bean.Tortoise;
import com.deaifish.tortoiserecordbook.dto.TortoiseAddDTO;
import com.deaifish.tortoiserecordbook.dto.TortoiseUpdDTO;
import com.deaifish.tortoiserecordbook.exception.TortoiseException;
import com.deaifish.tortoiserecordbook.properties.PathProperties;
import com.deaifish.tortoiserecordbook.service.IMGService;
import com.deaifish.tortoiserecordbook.service.TortoiseService;
import com.deaifish.tortoiserecordbook.vo.ResultVO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @description 乌龟控制器
 *
 * @author DEAiFISH
 * @date 2023/11/24 16:53
 */
@RequestMapping("/tortoise")
@RestController
@Slf4j
@CrossOrigin()
public class TortoiseController {
    @Autowired
    TortoiseService tortoiseService;
    @Autowired
    IMGService imgService;
    @Autowired
    PathProperties path;

    /**
     * @description 添加乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 16:56
     * @param tortoise 乌龟信息
     * @return boolean true:成功;false:失败
     */
    @PostMapping("/add")
    public ResultVO<String> addTortoise(@Validated @RequestBody TortoiseAddDTO tortoise) {
        tortoiseService.addTortoise(Tortoise.builder()
                .uid(tortoise.getUid())
                .name(tortoise.getName())
                .firstTime(tortoise.getFirstTime())
                .headTilts(tortoise.getHeadTilts())
                .memo(tortoise.getMemo())
                .price(tortoise.getPrice())
                .status(tortoise.getStatus())
                .kind(tortoise.getKind())
                .build());

        return new ResultVO<>(HttpStatus.OK.value(), "添加乌龟", "添加成功。");
    }

    /**
     * @description 上传图像
     *
     * @author DEAiFISH
     * @date 2023/11/27 21:38
     * @param img 图像文件
     * @return java.lang.String 图片保存全路径
     */
    @PostMapping("/upload-photo")
    public ResultVO<String> uploadTortoisePhoto(@NotNull(message = "文件不能为空。") @RequestParam MultipartFile img) throws IOException {
        String imgPath = imgService.uploadImg(img.getInputStream(), path.tortoiseHeadTiltsDirPath);
        return new ResultVO<>(HttpStatus.OK.value(), "上传图像", imgPath);
    }

    /**
     * @description 上传乌龟头像
     *
     * @author DEAiFISH
     * @date 2023/11/29 17:56
     * @param img 乌龟头像
     * @param tid 乌龟ID
     * @return java.lang.String
     */
    @PutMapping("/upd-photo")
    public ResultVO<String> updatePhoto(@NotNull(message = "文件不能为空。") @RequestParam MultipartFile img, @NotEmpty(message = "乌龟ID不能为空。") @RequestParam String tid) throws IOException {
        Tortoise tortoise = tortoiseService.selByTID(tid);
        if (tortoise == null) {
            throw new TortoiseException("乌龟不存在。");
        }
        String imgPath = imgService.updImg(tortoise.getHeadTilts(), img.getInputStream(), path.tortoiseHeadTiltsDirPath);
        tortoise.setHeadTilts(imgPath);
        tortoiseService.updTortoise(tortoise);
        return new ResultVO<>(HttpStatus.OK.value(), "上传乌龟头像", imgPath);
    }

    /**
     * @description 删除乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 16:58
     * @param tid 乌龟ID
     * @return boolean true:成功;false:失败
     */
    @DeleteMapping("/del")
    public ResultVO<String> delTortoise(@NotEmpty(message = "乌龟ID不能为空。") @RequestParam String tid) {
        tortoiseService.delTortoise(tid);
        return new ResultVO<>(HttpStatus.OK.value(), "删除乌龟", "删除成功。");
    }

    /**
     * @description 根据用户ID查询持有的所有龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 17:01
     * @param uid 用户ID
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.Tortoise>
     */
    @GetMapping("/selOfUser")
    public ResultVO<List<Tortoise>> selByUID(@NotEmpty(message = "用户ID不能为空。") @RequestParam String uid) {
        List<Tortoise> tortoises = tortoiseService.selByUID(uid);
        return new ResultVO<>(HttpStatus.OK.value(), "根据用户ID查询持有的所有龟", tortoises);
    }

    /**
     * @description 根据乌龟ID查询乌龟信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 17:09
     * @param tId 乌龟ID
     * @return com.deaifish.tortoiserecordbook.bean.Tortoise
     */
    @GetMapping("/id")
    public ResultVO<Tortoise> selByTID(@NotEmpty(message = "乌龟ID不能为空。") @RequestParam("id") String tId) {
        Tortoise tortoise = tortoiseService.selByTID(tId);
        return new ResultVO<>(HttpStatus.OK.value(), "根据乌龟ID查询乌龟信息", tortoise);
    }

    /**
     * @description 查询所有乌龟信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 17:02
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.Tortoise>
     */
    @GetMapping("/sel")
    public ResultVO<List<Tortoise>> selAll() {
        List<Tortoise> tortoises = tortoiseService.selAll();
        return new ResultVO<>(HttpStatus.OK.value(), "查询所有乌龟信息", tortoises);
    }

    /**
     * @description 修改乌龟信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 17:03
     * @param tortoise 乌龟信息
     * @return boolean true:成功;false:失败
     */
    @PutMapping("/upd")
    public ResultVO<String> updTortoise(@Validated @RequestBody TortoiseUpdDTO tortoise) {
        Tortoise t = tortoiseService.selByTID(tortoise.getId());
        if (t == null) {
            throw new TortoiseException("乌龟不存在。");
        }
        tortoiseService.updTortoise(Tortoise.builder()
                .id(tortoise.getId())
                .name(tortoise.getName())
                .kind(tortoise.getKind())
                .status(tortoise.getStatus())
                .headTilts(t.getHeadTilts())
                .price(tortoise.getPrice())
                .memo(tortoise.getMemo())
                .firstTime(tortoise.getFirstTime())
                .build());
        return new ResultVO<>(HttpStatus.OK.value(), "修改乌龟信息", "修改成功。");
    }
}
