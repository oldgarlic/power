<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
            <el-form-item label="岗位编码" prop="postCode">
                <el-input v-model="queryParams.filter.postCode" placeholder="请输入岗位编码" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="岗位名称" prop="postName">
                <el-input v-model="queryParams.filter.postName" placeholder="请输入岗位名称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-select v-model="queryParams.filter.status" placeholder="岗位状态" clearable>
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
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    >修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete" >删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    >导出</el-button>
            </el-col>
        </el-row>

        <el-table ref="tableData" :data="postList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="岗位编号" align="center" prop="postId" />
            <el-table-column label="岗位编码" align="center" prop="postCode" />
            <el-table-column label="岗位名称" align="center" prop="postName" />
            <el-table-column label="岗位排序" align="center" prop="postSort" />
            <el-table-column label="状态" align="center" prop="status">
                <template slot-scope="scope">
                    <el-tag type="success">{{dict[scope.row.status]}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" prop="createTime" width="180">
                <template slot-scope="scope">
                    <span>{{ scope.row.createTime }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页数据 -->
        <el-pagination
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            :page-size.sync="queryParams.pageSize"
            :page-sizes="[10, 15, 20, 30]"
            :current-page="queryParams.pageNum"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
        <!-- 添加或修改岗位对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="岗位名称" prop="postName">
                    <el-input v-model="form.postName" placeholder="请输入岗位名称" />
                </el-form-item>
                <el-form-item label="岗位编码" prop="postCode">
                    <el-input v-model="form.postCode" placeholder="请输入编码名称" />
                </el-form-item>
                <el-form-item label="岗位顺序" prop="postSort">
                    <el-input-number v-model="form.postSort" controls-position="right" :min="0" />
                </el-form-item>
                <el-form-item label="岗位状态" prop="status">
                    <el-radio-group v-model="form.status">
                        <el-radio v-for="dict in dictType.sys_normal_disable" :key="dict.value" :label="dict.value">{{
                                dict.label
                        }}</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

import { addOrUpdatePost,getList,deletePost} from '@/api/post'

export default {
    name: "Post",
    data() {
        return {
            // 选中数组
            ids: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 10,
            // 岗位表格数据
            postList: [
                {
                    postId: 1,
                    postCode: 'hr',
                    postName: '人事',
                    postSort: 1,
                    status: 0,
                    createTime: '2022-11-09'
                },
                {
                    postId: 2,
                    postCode: 'hr',
                    postName: '人事',
                    postSort: 1,
                    status: 0,
                    createTime: '2022-11-09'
                },
                {
                    postId: 3,
                    postCode: 'hr',
                    postName: '人事',
                    postSort: 1,
                    status: 0,
                    createTime: '2022-11-09'
                },
                {
                    postId: 4,
                    postCode: 'hr',
                    postName: '人事',
                    postSort: 1,
                    status: 0,
                    createTime: '2022-11-09'
                },
                {
                    postId: 5,
                    postCode: 'hr',
                    postName: '人事',
                    postSort: 1,
                    status: 0,
                    createTime: '2022-11-09'
                }
            ],
            dict: {
                0: '正常',
                1: '停用'
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
            // 弹出层标题
            title: '',
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                filter: {
                    postCode: undefined,
                    postName: undefined,
                    status: undefined
                }
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                postName: [
                    { required: true, message: '岗位名称不能为空', trigger: 'blur' },
                ],
                postCode: [
                    { required: true, message: '岗位编码不能为空', trigger: 'blur' },
                ],
                postSort: [
                    { required: true, message: '岗位顺序不能为空', trigger: 'blur' }
                ]
            }
        }
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询岗位列表 */
        getList() {
            getList({}).then((response) => {
                this.postList = response.data.records
                this.total  = response.data.total
                this.queryParams.pageNum = response.data.current
                this.queryParams.pageSize = response.data.size
            })
        },
        // 取消按钮
        cancel() {
        },
        // 表单重置
        reset() { 
            this.form = {
                postId: undefined,
                postCode: undefined,
                postName: undefined,
                postSort: 0,
                status: "0",
                remark: undefined
            };
        },
        /** 搜索按钮操作 */
        handleQuery() {
            console.log('执行搜索')
            getList(this.queryParams).then((response) => {
                this.postList = response.data.records
                this.total  = response.data.total
                this.queryParams.pageNum = response.data.current
                this.queryParams.pageSize = response.data.size
            })
        },
        /** 重置按钮操作 */
        resetQuery() {
            console.log('取消搜索')
            this.queryParams.filter.postCode = undefined
            this.queryParams.filter.postName = undefined
            this.queryParams.filter.status = undefined
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            console.log('选择')
            console.log(selection)
            this.ids = selection.map(item => item.postId)
            this.single = this.ids.length !== 1
            this.multiple = this.ids.length == 0
        },
        /** 新增按钮操作 */
        handleAdd() {
            console.log('执行添加')
            this.open = true
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            var postId = null
            if(row.postId){
                this.form = row
                postId = row.postId
            }else{
                postId = this.ids[0]
                // console.log(this.$refs.tableData.selection)
                this.form = this.$refs.tableData.selection[0]
            }
            // console.log('执行修改,id: ' + postId)
            this.open = true
        },
        /** 提交按钮 */
        submitForm() { 
            console.log('提交表单')
            console.log(this.form)
            addOrUpdatePost(this.form)
            this.open = false
            this.getList()
        },
        /** 删除按钮操作 */
        handleDelete(row) {
            console.log('执行删除,id: ' + row.postId)
            // 单个删除还是批量删除
            const postIds = row.postId || this.ids;
            this.$confirm('是否确认删除岗位编号为"' + postIds + '"的数据项？').then(function() {
                return deletePost(postIds);
            }).then(() => {
                this.getList();
                this.$message({
                    type: 'success',
                    message: "删除成功"
                });
            }).catch(() => {
                this.$message("删除失败");
            });
            this.getList()
        },
        /** 导出按钮操作 */
        handleExport() { 
            console.log('文件导出')
        },
        handleSizeChange() {

        },
        handleCurrentChange() {
            
        }
    },
};
</script>
