<template>
    <div class="add">
        <h2>借书</h2>
        <el-form :inline="true" label-width="150px" :rules="rules" :model="form" ref="form">
            <el-form-item label="书名" prop="bookName">
                <el-input disabled v-model="form.bookName"></el-input>
            </el-form-item>
            <br>

            <el-form-item label="图书标准码" prop="bookNo">
                <el-select v-model="form.bookNo" filterable placeholder="请选择"  @change="selectBook()">
                    <el-option
                            v-for="item in books"
                            :key="item.id"
                            :label="item.bookNo"
                            :value="item.bookNo">
                    </el-option>
                </el-select>
            </el-form-item>
            <br>

            <el-form-item label="所需积分" prop="score">
                <el-input v-model="form.score" disabled></el-input>
            </el-form-item>
            <br>
            <el-form-item label="库存" prop="nums">
                <el-input v-model="form.nums" disabled></el-input>
            </el-form-item>
            <br>

            <el-form-item label="用户会员码" prop="userNo">
                <template>
                    <el-select v-model="form.userNo" filterable placeholder="请选择" @change="selectUser()">
                        <el-option
                                v-for="item in users"
                                :key="item.id"
                                :label="item.username"
                                :value="item.username">
                        </el-option>
                    </el-select>
                </template>
            </el-form-item>
            <br>

            <el-form-item label="用户名称" prop="userName">
                <el-input disabled v-model="form.userName" disabled></el-input>
            </el-form-item>
            <br>

            <el-form-item label="联系方式" prop="userPhone" >
                <el-input v-model="form.userPhone" disabled></el-input>
            </el-form-item>
            <br>
            <el-form-item label="用户积分" prop="account">
                <el-input disabled v-model="form.account" disabled></el-input>
            </el-form-item>
            <br>
            <el-form-item label="借出天数" prop="score">
                <el-input-number v-model="form.days" :min="1" :max="30"></el-input-number>
            </el-form-item>
            <br>

        </el-form>

        <div class="buttons" style="margin-left:180px">
            <el-button type="primary" @click="save('form')">提交</el-button>
            <!--            <el-button type="danger">取消</el-button>-->
        </div>

    </div>

</template>


<script>
    import request from "@/utils/request";

    export default {
        name: "AddBorrow",
        data() {
            return {
                books: [],
                users: [],
                form: {
                    bookName: "",
                    bookNo: "",
                    score: "",
                    userNo: "",
                    userName: "",
                    userPhone: "",
                    nums: "",
                    account:"",
                    days: 1
                },
                rules: {
                    bookNo: [
                        {required: true, message: '请输入图书标准码', trigger: 'blur'},
                    ],
                    userNo: [
                        {required: true, message: '请输入会员号', trigger: 'blur'},
                    ]

                },
            }
        },
        created() {
            request.get("/book/list").then(res => {
                this.books = res.data
            })
            request.get("/user/list").then(res => {
                this.users = res.data.filter(v => v.status)
            })
        },
        methods: {

            selectBook(){
                const book = this.books.find(v => v.bookNo === this.form.bookNo);
                request.get("/book/"+book.id).then(res => {
                    // this.form.bookName = res.data.name;
                    this.$set(this.form,"bookName",res.data.name)
                    this.form.score = res.data.score;
                    this.form.nums = res.data.nums;
                })

            },

            selectUser(){
                const user = this.users.find(v => v.username === this.form.userNo);
                request.get("/user/"+user.id).then(res => {
                    this.form.userPhone = res.data.phone;
                    this.form.userName = res.data.name;
                    this.form.account = res.data.account;
                })

            },

            save(ruleForm) {
                console.log(this.form)
                // this.form.img = this.imageUrl;
                this.$refs[ruleForm].validate((valid) => {
                    // console.log(valid)
                    if (valid) {
                        request.post("/borrow/save", this.form).then(res => {
                            if (res.code == "200") {
                                this.$notify.success('新增成功')
                                // 只将有检验的表单回复默认
                                this.$refs[ruleForm].resetFields();
                            } else {
                                this.$notify.error(res.msg)
                            }
                        })
                    }
                })

            }

        }
    }
</script>

<style scoped>
    /*@import url("//unpkg.com/element-ui@2.15.10/lib/theme-chalk/index.css");*/

    .add {
        padding: 0 20px;
        width: 70%;
    }

    h2 {
        color: #D3D3D3;
    }

    .avatar-uploader {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }

    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }

    .buttons {
        margin-left: 80px;
    }

    .buttons button {
        margin-right: 100px;
    }

</style>