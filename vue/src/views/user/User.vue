<template>
    <div class="home">
        <!--    头部搜索-->
        <div class="search" style="padding:10px 10px">
            <el-input style="width: 240px" placeholder="请输入名称" v-model="params.name"></el-input>
            <el-input style="width: 240px;margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
        </div>
        <!--    <el-button type="primary">按钮</el-button>-->
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="编号" width="80"></el-table-column>
            <el-table-column prop="name" label="姓名"></el-table-column>
            <el-table-column prop="username" label="用户名"></el-table-column>
            <el-table-column prop="age" label="年龄" width="80"></el-table-column>
            <el-table-column prop="img" label="头像">
                <template slot-scope="scope">
                    <el-image :src="url+scope.row.img" :preview-src-list="[url+scope.row.img]" alt="" style="width: 100px; height: 100px"/>
                </template>
            </el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="sex" label="性别"></el-table-column>
            <el-table-column prop="account" label="账户余额"></el-table-column>
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
            <el-table-column  label="操作" width="160">
                <template v-slot="scope">
                    <el-button type="primary" icon="el-icon-edit" circle @click="$router.push('/editUser?id='+scope.row.id)"></el-button>
                    <el-button type="success" v-if="!scope.row.pid" icon="el-icon-plus" circle style="margin-left: 5px" @click="handleAccount(scope.row)"></el-button>

                    <el-popconfirm
                                title="确定删除这条数据吗？"
                                @confirm="del(scope.row.id)"
                                style="margin-left: 5px"
                        >
                            <el-button type="danger" icon="el-icon-delete" slot="reference" circle></el-button>
                        </el-popconfirm>
                </template>

            </el-table-column>
        </el-table>

<!--        分页-->
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

<!--        弹出框-->
        <el-dialog
                title="充值积分"
                :visible.sync="dialogVisible"
                width="30%">
            <el-form :rules="rules" :model="form" ref="form">
                <el-form-item label="当前积分">
                    <el-input v-model="form.account" disabled></el-input>
                </el-form-item>

                <el-form-item label="充值积分" prop="score">
                    <el-input v-model="form.score" ></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addAccount">确 定</el-button>
            </div>
        </el-dialog>

    </div>
    
</template>

<script>


    import request from "@/utils/request.js";
    export default {
        name: 'User',
        data() {
            var checkAccount = (rule, value, callback) => {
                    value = parseInt(value);
                    if (value === null || value === undefined) {
                        return callback(new Error('请输入数字'));
                    }
                    if (!Number.isInteger(value) || value < 0) {
                        callback(new Error('请输入大于零的整数'));
                    } else {
                        callback();
                    }
                }
            return {
                tableData:[],
                total:0,
                params:{
                    pageNum:1,
                    pageSize:5,
                    name: "",
                    phone: ""
                },
                form:{},
                url:'http://localhost:8090',
                dialogVisible: false,
                rules: {
                    score:[
                        {required: true, message: '请输入要充值的积分', trigger: 'blur'},
                        {validator: checkAccount, trigger: 'blur'},
                    ]
                }
            }
        },
        created() {
            this.load()
        },
        methods:{
            changeStatus(row){
                console.log(row)
                request.put("/user/edit",row).then(res => {
                    if (res.code == "200") {
                        this.$notify.success('操作成功')
                        this.load()
                    } else {
                        this.$notify.error(res.msg)
                    }
                })
            },
            addAccount(){
                this.$refs["form"].validate((valid) => {
                    // console.log(valid)
                    if (valid) {
                        request.post("/user/account", this.form).then(res => {
                            if (res.code == "200") {
                                this.$notify.success('充值成功')
                                this.dialogVisible =  false
                                this.load();
                            } else {
                                this.$notify.error(res.msg)
                            }
                        })
                    }
                })
            },

            handleAccount(row){
                this.form = JSON.parse(JSON.stringify(row))
                this.dialogVisible =  true
            },

            load(){
                // fetch("http://localhost:8090/user/list").then(res => res.json()).then(res => {
                //     this.tableData = res
                // })
                request.get("/user/page",{
                    params: this.params
                }).then(res => {
                    if (res.code == "200"){
                        this.tableData = res.data.list;
                        this.total = res.data.total;
                        console.log(this.tableData)
                    }
                })
            },
            reset(){
                this.params = {
                    pageNum:1,
                    pageSize:5,
                    name: "",
                    phone: ""
                }
            },
            handleCurrentChange(pageNum){
                // console.log(pageNum)
                this.params.pageNum = pageNum;
                this.load()
            },
            del(id){
                request.delete("/user/delete/"+id).then(res => {
                    if (res.code == "200"){
                        this.$notify.success('删除成功')
                        this.load()
                    }else{
                        this.$notify.error(res.msg)
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>