<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input v-model="deptName" placeholder="请输入部门名称" clearable size="small" prefix-icon="el-icon-search"
            style="margin-bottom: 20px" />
        </div>
        <div class="head-container">
          <el-tree ref="tree" :data="deptOptions" :props="defaultProps" :expand-on-click-node="false"
            :filter-node-method="filterNode" node-key="id" default-expand-all highlight-current
            @node-click="handleNodeClick" />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="68px">
          <el-form-item label="用户名称" prop="username">
            <el-input v-model="queryParams.filter.username" placeholder="请输入用户名称" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input v-model="queryParams.filter.phone" placeholder="请输入手机号码" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.filter.status" placeholder="用户状态" clearable style="width: 240px">
              <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
                :value="dict.value" />
            </el-select>
          </el-form-item>
<!--          <el-form-item label="创建时间">-->
<!--            <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"-->
<!--              range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />-->
<!--          </el-form-item>-->
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini"  :disabled="delete1" @click="handleDelete">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport">导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
          </el-col>
        </el-row>
        <!--v-loading = 加载状态, data = 数据来源,element-loading-text = 加载的文字,
        border = 表格的边框, fit = 列的自适应,highlight-current-row = 当前行高亮-->
        <el-table ref="tableData" v-loading="listLoading" :data="userList" @selection-change="handleSelectionChange">
          <!-- 选择框 -->
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column key="userId" label="用户编号" align="center" prop="userId" />
          <el-table-column key="username" label="用户名称" align="center" prop="username" :show-overflow-tooltip="true" />
          <el-table-column key="nickname" label="用户昵称" align="center" prop="nickname" :show-overflow-tooltip="true" />
          <el-table-column key="deptName" label="部门" align="center"  :show-overflow-tooltip="true" >
            <template slot-scope="scope">
                  <span>{{ deptMap[scope.row.deptId] }}</span>
            </template>
          </el-table-column>
          <el-table-column key="postName" label="岗位" align="center"  :show-overflow-tooltip="true" >
            <template slot-scope="scope">
                  <span>{{ postMap[scope.row.postId] }}</span>
            </template>
          </el-table-column>
          <el-table-column key="phone" label="手机号码" align="center" prop="phone" width="120" />
          <!--status是一个String-->
          <el-table-column key="status" label="状态" align="center">
            <template slot-scope="scope">
              <!-- 切换栏 -->
              <el-switch v-model="scope.row.status" active-value="0" inactive-value="1" @change="handleStatusChange(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <!-- 没有方法，直接调用的 -->
              <span>{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
            <template v-if="scope.row.userId !== 1" slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)">
                <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleResetPwd" icon="el-icon-key">重置密码</el-dropdown-item>
                  <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check">分配角色</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页数据 -->
        <el-pagination layout="total, sizes, prev, pager, next, jumper" :total="total"
          :page-size.sync="queryParams.pageSize" :page-sizes="[5, 10, 20, 30]" :current-page.sync="queryParams.pageNumber"
          @size-change="handleSizeChange" @current-change="handleCurrentChange" />
      </el-col>
    </el-row>
    <!-- 添加或修改用户配置对话框 -->
    <el-dialog  :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickname">
              <el-input v-model="form.nickname" placeholder="请输入用户昵称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId === undefined" label="用户名称" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名称" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId === undefined" label="用户密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">
                  {{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="岗位">
              <el-select v-model="form.postId"  placeholder="请选择岗位">
                <el-option v-for="item in postOptions" :key="item.postId" :label="item.postName" :value="item.postId"
                  :disabled="item.status === 1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色">
              <el-select v-model="form.roleIds" multiple placeholder="请选择角色">
                <el-option v-for="item in roleOptions" :key="item.roleId" :label="item.roleName" :value="item.roleId"
                  :disabled="item.status === 1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addOrUpdateUser, list,deleteUserByIds } from '@/api/user'
import { getDeptList } from '@/api/dept'
import { getPostList } from '@/api/post'
import treeselect from "@riophae/vue-treeselect";
import '@riophae/vue-treeselect/dist/vue-treeselect.css';
export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  components: { treeselect },
  data() {
    return {
      // 这里是数据
      userList: [
        {
          userId: 1,
          username: '张飞',
          nickname: '战神',
          deptName: '马来西亚',
          phone: '110',
          status: 1,
          createTime: '2011-01-21 10:20:01'
        },
        {
          userId: 2,
          username: '张飞',
          nickname: '战神',
          deptName: '马来西亚',
          phone: '110',
          status: 1,
          createTime: '2011-01-21 10:20:01'
        },
        {
          userId: 3,
          username: '张飞',
          nickname: '战神',
          deptName: '马来西亚',
          phone: '110',
          status: 1,
          createTime: '2011-01-21 10:20:01'
        },
        {
          userId: 4,
          username: '张飞',
          nickname: '战神',
          deptName: '马来西亚',
          phone: '110',
          status: 1,
          createTime: '2011-01-21 10:20:01'
        },
        {
          userId: 5,
          username: '张飞',
          nickname: '战神',
          deptName: '马来西亚',
          phone: '110',
          status: 1,
          createTime: '2011-01-21 10:20:01'
        },
        {
          userId: 6,
          username: '张飞',
          nickname: '战神',
          deptName: '马来西亚',
          phone: '110',
          status: 1,
          createTime: '2011-01-21 10:20:01'
        }

      ],
      dict: {
        type: {
          sys_normal_disable: [{
            value: "0",
            label: '正常'
          },
          {
            value: "1",
            label: '停用'
          }],
          sys_user_sex:[{
            label: '男性',
            value: "1"
          },{
            label: '女性',
            value: "0"
          }],
        }
      },
      title: '',
      delete1: true,
      form: {},
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      // 表单校验
      rules: {
        username: [
          { required: true, message: "用户名称不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ],
        email: [
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true }
      ],
      total: 6,
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 10,
        sort: 'user_id',
        dept: undefined,
        filter: {
          username: undefined,
          phone: undefined,
          status: undefined,
          deptId: undefined
        }
      },
      dateRange: '',
      deptName: '',
      // 部门树选项
      deptOptions: [{
        id: 1,
        label: 'label',
        children: [{
          id: 2,
          label: 'children1'
        },
        {
          id: 3,
          label: 'children2'
        },
        {
          id: 4,
          label: 'children3'
        }]
      }],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      listLoading: false,
      open: false,
      ids: [],
      single: true,
      postMap:{},
      deptMap:{}
    }
  },
  created() {
    this.fetchDept()
    this.fetchPost()
    this.fetchUser()
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val)
    }
  },
  methods: {
    fetchPost(){
      getPostList().then(response=>{
        this.postOptions = response.data.records
        // TODO 这里再给封装出来一个信息
        this.postOptions.map(post=>this.postMap[post.postId] = post.postName)
        console.log(this.postMap)

      })
    },
    fetchDept() {
      getDeptList({ sort: 'dept_id' }).then(response => {
        this.deptOptions = this.handleTree(response.data, "deptId")
        // TODO 这里再给封装一个信息
        // console.log(response.data)
        response.data.map(dept=>this.deptMap[dept.deptId] = dept.deptName)
        console.log(this.deptMap)
      })
    },
    showSearch() {

    },
    handleQuery() {
      this.fetchUser();
    },
    resetQuery() {
      this.queryParams.filter = {
          username: undefined,
          phone: undefined,
          status: undefined
      }
      this.fetchUser()
    },
    fetchUser() {
      list(this.queryParams).then((response) => {
        this.userList = response.data.records
        this.total = response.data.total
        this.queryParams.pageNumber = response.data.current
        this.queryParams.pageSize = response.data.size
        console.log(this.userList)
      })
    },
    handleSelectionChange(selection) {
      console.log('选择切换')
      this.ids = selection.map(item => item.userId)
      this.single = selection.length !== 1
      this.delete1 = selection.length === 0
    },
    handleStatusChange(row) {
      console.log('状态切换: ${row.status}')
      addOrUpdateUser(row).then(()=>{
        this.fetchUser();
      })
    },
    handleDelete(row) {
      console.log('点击 删除')
      const userIds = row.userId || this.ids
      deleteUserByIds(userIds).then(()=>{
        this.fetchUser()
      })
    },
    handleCommand() {

    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
      this.fetchUser();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.fetchUser();
    },
    // 节点过滤
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    handleNodeClick(val) {
      console.log(`部门选择: ${val.deptName}`)
      this.queryParams.deptId = val.deptId
      this.fetchUser()
    },
    handleAdd() {
      console.log('添加用户')
      this.open = true
    },
    handleUpdate(row) {
      console.log("执行修改")
      console.log(row)
      let userId = null
      if (row.userId) {
        this.form = row
        userId = row.userId
      } else {
        userId = this.ids[0]
        this.form = this.$refs.tableData.selection[0]
      }
      this.open = true
    },
    handleImport() {
      console.log('导入用户')
    },
    handleExport() {
      console.log('导出用户')
    },
    submitForm() {
      console.log('提交表单')
      addOrUpdateUser(this.form).then(() => {
        this.fetchUser()
      })
      this.open = false;
      this.form = {}
    },
    cancel() {
      this.open = false;
    },
    /**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 */
    handleTree(data, id, parentId = 'parentId', children = 'children') {
      let config = {
        id: id || 'id',
        parentId: parentId || 'parentId',
        childrenList: children || 'children'
      };

      var childrenListMap = {};
      var nodeIds = {};
      var tree = [];

      for (let d of data) {
        let parentId = d[config.parentId];
        if (childrenListMap[parentId] == null) {
          childrenListMap[parentId] = [];
        }
        nodeIds[d[config.id]] = d;
        childrenListMap[parentId].push(d);
      }

      for (let d of data) {
        let parentId = d[config.parentId];
        if (nodeIds[parentId] == null) {
          // 自己补充
          d.id = d.deptId
          d.label = d.deptName
          tree.push(d);
        }
      }

      for (let t of tree) {
        adaptToChildrenList(t);
      }

      function adaptToChildrenList(o) {
        // 自己补充
        o.id = o.deptId
        o.label = o.deptName
        if (childrenListMap[o[config.id]] !== null) {
          o[config.childrenList] = childrenListMap[o[config.id]];
        }
        if (o[config.childrenList]) {
          for (let c of o[config.childrenList]) {
            adaptToChildrenList(c);
          }
        }
      }
      return tree;
    }
  }
}
</script>
