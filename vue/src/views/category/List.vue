<template>
    <div class="home">
        <!--    头部搜索-->
        <div class="search" style="padding:10px 10px">
            <el-input style="width: 240px" placeholder="请输入分类名称" v-model="params.name"></el-input>

            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索
            </el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置
            </el-button>
        </div>
        <!--    <el-button type="primary">按钮</el-button>-->
        <el-table :data="tableData" stripe row-key="id" default-expand-all>
            <el-table-column prop="id" label="编号" width="80"></el-table-column>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="remark" label="备注"></el-table-column>
            <el-table-column prop="createTime" label="创建日期"></el-table-column>
            <el-table-column prop="updateTime" label="修改日期"></el-table-column>

            <el-table-column label="操作">
                <template v-slot="scope">
                    <el-button type="primary" icon="el-icon-edit" circle
                               @click="$router.push('/editCategory?id='+scope.row.id)"></el-button>
                    <el-popconfirm
                            title="确定删除这条数据吗？"
                            @confirm="del(scope.row.id)"
                            style="margin-left: 5px"
                    >
                        <el-button type="danger" icon="el-icon-delete" slot="reference" alt="删除" circle></el-button>
                    </el-popconfirm>
                    <el-button type="success" v-if="!scope.row.pid" icon="el-icon-plus" circle style="margin-left: 5px" @click="show(scope.row)"></el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="page">
            <el-pagination
                    background
                    :current-page="params.pageNum"
                    :page-size="params.pageSize"
                    layout="prev, pager, next"
                    @current-change="handleCurrentChange"
                    :total="total">
            </el-pagination>
        </div>


        <el-dialog
                title="添加二级分类"
                :visible.sync="dialogVisible"
                width="30%">
            <el-form :rules="rules" :model="form" ref="form">
                <el-form-item prop="name" label="二级分类名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="form.remark" ></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

    </div>

</template>

<script>


    import request from "@/utils/request.js";
    import Cookie from 'js-cookie'

    export default {
        name: 'CategoryList',
        data() {
            return {
                // value: false,
                tableData: [],
                total: 0,
                params: {
                    pageNum: 1,
                    pageSize: 5,
                    name: "",
                },
                category: Cookie.get("admin") ? JSON.parse(Cookie.get("admin")) : {},
                dialogVisible: false,
                form: {
                    name:"",
                    remark:"",
                    pid:null
                },
                // pid: null,
                rules: {
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'},
                    ],
                },
            }
        },

        created() {
            this.load()
        },
        methods: {
            show(row){
                console.log(row.id)
                this.form.pid = row.id
                console.log(this.form)
                this.dialogVisible = true
            },

            save() {
                this.$refs['form'].validate((valid) => {
                    request.post("/category/save", this.form).then(res => {
                        if (res.code === "200") {
                            this.dialogVisible = false
                            this.$notify.success("添加二级菜单成功")
                            this.$refs[ruleForm].resetFields();
                            this.load()
                        }else{
                            this.dialogVisible = false
                            this.$notify.error("添加失败")
                        }
                    })
                })
            },

            load() {
                // fetch("http://localhost:8090/user/list").then(res => res.json()).then(res => {
                //     this.tableData = res
                // })
                request.get("/category/page", {
                    params: this.params
                }).then(res => {
                    if (res.code === "200") {
                        this.tableData = res.data.list;
                        this.total = res.data.total;
                    }
                })
            },
            reset() {
                this.params = {
                    pageNum: 1,
                    pageSize: 5,
                    name: "",
                    phone: ""
                }
            },
            handleCurrentChange(pageNum) {
                // console.log(pageNum)
                this.params.pageNum = pageNum;
                this.load()
            },
            del(id) {
                request.delete("/category/delete/" + id).then(res => {
                    if (res.code == "200") {
                        this.$notify.success('删除成功')
                        this.load()
                    } else {
                        this.$notify.error(res.msg)
                    }
                })
            },

        }
    }
</script>

<style scoped>

</style>