<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar :user="user" />
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user" />用户名称
                <div class="pull-right"> {{user.username}}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-phone" />手机号码
                <div class="pull-right">{{user.phone}}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-chicken" />用户邮箱
                <div class="pull-right">{{user.email}}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="tree" />所属部门
                <div class="pull-right">{{user.deptName}} / {{user.postName}}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-sugar" />所属角色
                <div class="pull-right">管理员</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-coffee-cup" />创建日期
                <div class="pull-right">{{user.createTime}}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userInfo">
              <userInfo :user="user" />
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd />
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import resetPwd from "./resetPwd";
import userInfo from "./userInfo";
import userAvatar from "./userAvatar";
import { addOrUpdateUser, list,deleteUserByIds ,getInfo} from '@/api/user'
export default {
  name: "Profile",
  components: { userAvatar,userInfo, resetPwd },
  data() {
    return {
      user: {
        img: "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
      },
      roleGroup: {},
      postGroup: {},
      activeTab: "userInfo",
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    getUser() {
      getInfo().then(response => {
        this.user = response.data;
        // this.roleGroup = response.roleGroup;
        // this.postGroup = response.postGroup;
      });
    },

  }
};
</script>


<style>
.list-group {
  padding-left: 0px;
  list-style: none;
}


.list-group-item {
  border-bottom: 1px solid #e7eaec;
  border-top: 1px solid #e7eaec;
  margin-bottom: -1px;
  padding: 11px 0px;
  font-size: 13px;
}


.list-group-striped>.list-group-item {
  border-left: 0;
  border-right: 0;
  border-radius: 0;
  padding-left: 0;
  padding-right: 0;
}

.pull-right {
  float: right !important;
}

.text-center {
  text-align: center
}

</style>
