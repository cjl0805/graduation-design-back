package com.back.graduationdesign.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HairStyleChecked {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Integer id;

    /**
     * 发型名称
     */
    private String hairstyle;

    /**
     * 发型图片
     */
    private String image;

    private boolean checked;
}
