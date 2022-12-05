package com.lll.poweradmin.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 岗位信息表
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("post")
@ApiModel(value="Post对象", description="岗位信息表")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位id")
    @TableId(type = IdType.AUTO)
    private Long postId;

    @NotBlank(message = "岗位编号不能为空")
    @ApiModelProperty(value = "岗位编号")
    private String postCode;

    @NotBlank
    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "显示顺序")
    private Integer postSort;

    @ApiModelProperty(value = "岗位状态;0正常,1停用")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private boolean del_flag;
}
