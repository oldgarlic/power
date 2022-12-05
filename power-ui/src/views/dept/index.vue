<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="部门名称" prop="deptName">
        <el-input v-model="queryParams.filter.deptName" placeholder="请输入部门名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.filter.status" placeholder="部门状态" clearable>
          <el-option v-for="dict in dictType.sys_normal_disable" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
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
        <el-button type="info" plain icon="el-icon-sort" size="mini" @click="toggleExpandAll">展开/折叠</el-button>
      </el-col>
    </el-row>

    <el-table v-if="refreshTable" :data="deptList" row-key="deptId" :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
      <el-table-column prop="deptName" label="部门名称" width="260" />
      <el-table-column prop="deptSort" label="排序" width="200" />
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag type="success">{{ dict[scope.row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)">新增</el-button>
          <el-button v-if="scope.row.parentId !== 0" size="mini" type="text" icon="el-icon-delete"
                     @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改部门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24" v-if="form.parentId !== 0">
            <el-form-item label="上级部门" prop="parentId">
              <treeselect v-model="form.parentId" :options="deptOptions" :normalizer="normalizer"
                placeholder="选择上级部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部门名称" prop="deptName">
              <el-input v-model="form.deptName" placeholder="请输入部门名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.deptSort" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="负责人" prop="leader">
              <el-input v-model="form.leader" placeholder="请输入负责人" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门状态">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dictType.sys_normal_disable" :key="dict.value" :label="dict.value">
                  {{dict.label}}
                </el-radio>
              </el-radio-group>
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
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { addOrUpdateDept, getDeptList, deleteDept } from '@/api/dept'
export default {
  name: 'Dept',
  components: { Treeselect },
  data() {
    return {
      dict: {
        0: '正常',
        1: '停用'
      },
      // 表格树数据
      deptList: [{
        deptId: 1,
        deptName: '财务',
        orderNum: 1,
        status: 0,
        createTime: '2022-11-02',
        children: [{
          deptId: 2,
          deptName: '财务',
          orderNum: 2,
          status: 0,
          createTime: '2022-11-02'
        }, {
          deptId: 3,
          deptName: '财务',
          orderNum: 2,
          status: 0,
          createTime: '2022-11-02'
        }]
      },
      {
        deptId: 4,
        deptName: '财务',
        orderNum: 2,
        status: 0,
        createTime: '2022-11-02'
      },
      {
        deptId: 6,
        deptName: '财务',
        orderNum: 3,
        status: 0,
        createTime: '2022-11-02'
      },
      {
        deptId: 5,
        deptName: '财务',
        orderNum: 4,
        status: 0,
        createTime: '2022-11-02'
      }],
      // 部门树选项
      deptOptions: [{
        id: 1,
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
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        filter:{
          deptName: undefined,
          status: undefined
        },
        sort: 'dept_sort'
      },
      dictType: {
        sys_normal_disable: [
          {
            value: 0,
            label: '正常'
          },
          {
            value: 1,
            label: '停用'
          }
        ]
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: '上级部门不能为空', trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '部门名称不能为空', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '显示排序不能为空', trigger: 'blur' }
        ],
        email: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change']
          }
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询部门列表 */
    getList() {
      getDeptList(this.queryParams).then(response => {
        // this.deptList = response.data;
        // 这里要构建出树形数据
        this.deptList = this.handleTree(response.data, "deptId");
        this.deptOptions = this.handleTree(response.data, "deptId");
      })
    },
    /** 转换部门数据结构 */
    normalizer(node) {

    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    // 表单重置
    reset() {
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this. queryParams = {
        filter:{
          deptName: undefined,
          status: undefined
        },
        sort: 'dept_sort'
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      console.log('执行添加')
      this.open = true
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      console.log('执行修改')
      this.open = true;
      this.form = row
    },
    /** 提交按钮 */
    submitForm() {
      addOrUpdateDept(this.form).then(()=>{
        this.getList()
      })
      this.open = false;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      console.log('执行删除')
      deleteDept(row.deptId).then(()=>{
        this.getList()
      })
    },
    /**
     * 构造树型结构数据
     * @param {*} data 数据源
     * @param {*} id id字段 默认 'id'
     * @param {*} parentId 父节点字段 默认 'parentId'
     * @param {*} children 孩子节点字段 默认 'children'
     */
    handleTree(data, id, parentId = "parentId", children = "children") {
      let config = {
        id: id || 'id',
        parentId: parentId || 'parentId',
        childrenList: children || 'children'
      };

      const childrenListMap = {}
      const nodeIds = {}
      const tree = []

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

