<template>
    <div class="home">
        <!--    头部搜索-->
        <div class="search" style="padding:10px 10px">
            <el-input style="width: 240px" placeholder="请输入用户名" v-model="params.username"></el-input>
            <el-input style="width: 240px;margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
            <el-input style="width: 240px;margin-left: 5px" placeholder="请输入邮箱" v-model="params.email"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索
            </el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置
            </el-button>
        </div>
        <!--    <el-button type="primary">按钮</el-button>-->
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="编号" width="80"></el-table-column>
            <el-table-column prop="username" label="用户名"></el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="status" label="状态">
                <template v-slot="scope">
                    <el-switch
                            v-model="scope.row.status"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            @change="changeStatus(scope.row)"
                    >
                    </el-switch>
                </template>

            </el-table-column>
            <el-table-column prop="createtime" label="创建日期"></el-table-column>
            <el-table-column prop="updatetime" label="修改日期"></el-table-column>
            <el-table-column label="操作">
                <template v-slot="scope">
                    <el-button type="primary" icon="el-icon-edit" circle
                               @click="$router.push('/editAdmin?id='+scope.row.id)"></el-button>
                    <el-popconfirm
                            title="确定删除这条数据吗？"
                            @confirm="del(scope.row.id)"
                            style="margin-left: 5px"
                    >
                        <el-button type="danger" icon="el-icon-delete" slot="reference" circle></el-button>
                    </el-popconfirm>
                    <el-popconfirm
                            title="确定要修改密码吗？"
                            @confirm="handlerChangePass(scope.row)"
                            style="margin-left: 5px"
                    >
                        <el-button type="warning" icon="el-icon-key" slot="reference" circle
                                   ></el-button>
                    </el-popconfirm>
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
                title="修改密码"
                :visible.sync="dialogVisible"
                width="30%">
            <el-form :rules="rules" :model="form" ref="form">
                <el-form-item prop="password" label="新密码">
                    <el-input v-model="form.newPass" show-password></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="savePass">确 定</el-button>
            </div>
        </el-dialog>

    </div>

</template>

<script>


    import request from "@/utils/request.js";
    import Cookie from 'js-cookie'
    export default {
        name: 'AdminList',
        data() {
            return {
                // value: false,
                tableData: [],
                total: 0,
                params: {
                    pageNum: 1,
                    pageSize: 5,
                    username: "",
                    phone: "",
                    email: ""
                },
                dialogVisible: false,
                form: {},
                rules: {
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        // {min: 3, max: 8, message: '请输入在 3 ~ 8 个字符', trigger: 'blur'}
                    ],
                },
                admin: Cookie.get("admin") ? JSON.parse(Cookie.get("admin")) :{},
            }
        },

        created() {
            this.load()
        },
        methods: {
            changeStatus(row){
                if (this.admin.id === row.id && !row.status){
                    row.status = true
                    this.$notify.warning("您的操作不合法")
                    return
                }
                request.put("/admin/edit",row).then(res => {
                    if (res.code == "200") {
                        this.$notify.success('操作成功')
                        this.load()
                    } else {
                        this.$notify.error(res.msg)
                    }
                })
            },
            handlerChangePass(row) {
                this.form = row
                this.dialogVisible = true
            },
            savePass() {
                this.$refs['form'].validate((valid) => {
                    if (valid){
                        request.put("/admin/password", this.form).then(res => {
                            if (res.code === "200") {
                                this.$notify.success("修改成功")
                                if (this.form.id === this.admin.id){
                                    Cookie.remove("admin")
                                    this.$router.push("/login")
                                }else{
                                    this.dialogVisible = false;
                                    this.load();
                                }
                            }else{
                                this.$notify.error("修改失败")
                            }
                        })
                    }
                })
            },
            load() {
                // fetch("http://localhost:8090/user/list").then(res => res.json()).then(res => {
                //     this.tableData = res
                // })
                request.get("/admin/page", {
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
                request.delete("/admin/delete/" + id).then(res => {
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