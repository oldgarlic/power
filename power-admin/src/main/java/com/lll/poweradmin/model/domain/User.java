package com.lll.poweradmin.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author oldgarlic
 * @since 2022-11-03
 */
@Data
@Accessors(chain = true)
@TableName("user")
@ApiModel(value="User对象", description="用户信息表")
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId( type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    // 它用的是string
    @ApiModelProperty(value = "状态;0正常,1停用")
    private String status;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "岗位id")
    private Long postId;

    @ApiModelProperty(value = "登录IP")
    private LocalDateTime loginIp;

    @ApiModelProperty(value = "最后登录时间")
    private LocalDateTime lastLogin;

    @ApiModelProperty(value = "逻辑删除;0存在,1删除")
    private Integer delFlag;

    @ApiModelProperty(value = "创建人")
    private Long createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "注解")
    private String remark;

}
