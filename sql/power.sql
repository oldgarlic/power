DROP TABLE IF EXISTS user;
CREATE TABLE user(
                     `id` BIGINT NOT NULL AUTO_INCREMENT  COMMENT '用户ID' ,
                     `username` VARCHAR(16)    COMMENT '用户名称' ,
                     `nickname` VARCHAR(16)    COMMENT '用户昵称' ,
                     `password` VARCHAR(16)  NOT NULL  COMMENT '密码' ,
                     `sex` TINYINT    COMMENT '性别' ,
                     `avatar` VARCHAR(255)    COMMENT '头像地址' ,
                     `email` VARCHAR(16)    COMMENT '邮箱' ,
                     `phone` CHAR(11)    COMMENT '手机号码' ,
                     `status` TINYINT  DEFAULT 0  COMMENT '状态;0正常,1停用' ,
                     `dept_id` BIGINT    COMMENT '部门ID' ,
                     `login_ip` VARCHAR(32)    COMMENT '登录IP' ,
                     `last_login` DATETIME    COMMENT '最后登录时间' ,
                     `del_flag` TINYINT DEFAULT 0   COMMENT '逻辑删除;0存在,1删除' ,
                     `create_by` BIGINT COMMENT '创建人' ,
                     `create_time` DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间' ,
                     `update_by` VARCHAR(32)    COMMENT '更新人' ,
                     `update_time` DATETIME  DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP  COMMENT '更新时间' ,
                     `remark` VARCHAR(255)    COMMENT '注解' ,
                     PRIMARY KEY (id)
)  COMMENT = '用户信息表';

DROP TABLE IF EXISTS dept;
CREATE TABLE dept(
                     `id` BIGINT NOT NULL AUTO_INCREMENT  COMMENT '部门ID' ,
                     `parent_id` BIGINT    COMMENT '父部门ID' ,
                     `dept_name` VARCHAR(16)  NOT NULL  COMMENT '部门名称' ,
                     `ancestors` VARCHAR(16)    COMMENT '祖级部门' ,
                     `dept_sort` INT    COMMENT '显示顺序' ,
                     `leader` VARCHAR(20)    COMMENT '领导' ,
                     `phone` CHAR(11)    COMMENT '联系电话' ,
                     `email` VARCHAR(16)    COMMENT '邮箱' ,
                     `status` TINYINT  DEFAULT 0  COMMENT '部门状态;0正常,1停用' ,
                     `create_by` VARCHAR(32)    COMMENT '创建人' ,
                     `create_time` DATETIME  DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间' ,
                     `update_by` VARCHAR(32)    COMMENT '更新人' ,
                     `update_time` DATETIME   DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP COMMENT '更新时间' ,
                     PRIMARY KEY (id)
)  COMMENT = '部门信息表';
DROP TABLE IF EXISTS post;
CREATE TABLE post(
                     `id` BIGINT NOT NULL AUTO_INCREMENT  COMMENT '岗位id' ,
                     `post_code` VARCHAR(64) NOT NULL   COMMENT '岗位编号' ,
                     `post_name` VARCHAR(64) NOT NULL   COMMENT '岗位名称' ,
                     `post_sort` INT    COMMENT '显示顺序' ,
                     `status` TINYINT DEFAULT 0   COMMENT '岗位状态;0正常,1停用' ,
                     `create_by` VARCHAR(32)    COMMENT '创建人' ,
                     `create_time` DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间' ,
                     `update_by` VARCHAR(32)    COMMENT '更新人' ,
                     `update_time` DATETIME  DEFAULT CURRENT_TIMESTAMP  on update CURRENT_TIMESTAMP  COMMENT '更新时间' ,
                     PRIMARY KEY (id)
)  COMMENT = '岗位信息表';

DROP TABLE IF EXISTS user_post;
CREATE TABLE user_post(
                          `user_id` BIGINT NOT NULL   COMMENT '用户ID' ,
                          `post_id` BIGINT NOT NULL   COMMENT '岗位ID' ,
                          PRIMARY KEY (user_id,post_id)
)  COMMENT = '用户与岗位关系表';
