<template>
    <div class="add">
        <h2>添加图书</h2>
        <el-form :inline="true" label-width="150px" :rules="rules" :model="form" ref="form">

            <el-form-item label="书名" prop="name">
                <el-input v-model="form.name" placeholder="请输入书名"></el-input>
            </el-form-item>
            <br>

            <el-form-item label="封面">
                <el-upload
                        class="avatar-uploader"
                        :action="'http://localhost:8090/api/book/file/upload?token='+ this.admin.token"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                >
                    <img v-if="form.cover" :src="form.cover" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item>
            <br>
            <el-form-item label="描述" prop="description">
                <el-input type="textarea" v-model="form.description" placeholder="请输入描述"></el-input>
            </el-form-item>
            <br>

            <el-form-item label="出版日期" prop="publishDate">
                <el-date-picker
                        v-model="form.publishDate"
                        type="date"
                        placeholder="请选择出版日期">
                </el-date-picker>
            </el-form-item>
            <br>

            <el-form-item label="作者" prop="author">
                <el-input v-model="form.author" placeholder="请输入作者"></el-input>
            </el-form-item>
            <br>

            <el-form-item label="出版社" prop="publicer">
                <el-input v-model="form.publicer" placeholder="请输入出版社"></el-input>
            </el-form-item>
            <br>
            <el-form-item label="类型" prop="category">
                <el-cascader
                        style="width: 220px"
                        :options="categories"
                        v-model="form.categories"
                        :props="{value: 'name',label: 'name'}"
                ></el-cascader>
            </el-form-item>
            <br>
            <el-form-item label="图书标准码" prop="bookNo">
                <el-input v-model="form.bookNo" placeholder="请输入标准码"></el-input>
            </el-form-item>
            <br>
            <el-form-item label="积分" prop="score">
                <el-input-number v-model="form.score" :min="1" :max="50"></el-input-number>
            </el-form-item>
            <br>
            <el-form-item label="数量" prop="nums">
                <el-input v-model="form.nums" placeholder="请输入数量"></el-input>
            </el-form-item>
        </el-form>

        <div class="buttons" style="margin-left:180px">
            <el-button type="primary" @click="save('form')">提交</el-button>
            <!--            <el-button type="danger">取消</el-button>-->
        </div>

    </div>

</template>


<script>
    import request from "@/utils/request";
    import Cookie from "js-cookie";

    export default {
        name: "AddBook",
        data() {

            var checkNums = (rule, value, callback) => {
                value = parseInt(value);
                if (value === null || value === undefined) {
                    return callback(new Error('请输入数字'));
                }
                if (!Number.isInteger(value) || value < 0 || value > 1000) {
                    callback(new Error('请输入0~1000的整数'));
                } else {
                    callback();
                }
            }
            return {
                imageUrl: "",
                admin: Cookie.get("admin") ? JSON.parse(Cookie.get("admin")) :{},
                form: {
                    name: "",
                    bookNo: "",
                    description: "",
                    publishDate: "",
                    author: "",
                    publicer: "",
                    categories: "",
                    score: "",
                    nums: "",
                    cover:""
                },
                rules: {
                    name: [
                        {required: true, message: '请输入书名', trigger: 'blur'},
                    ],
                    score: [
                        {required: true, message: '请输入图书积分', trigger: 'blur'},
                    ],
                    nums: [
                        {required: true, message: '请输入图书数量', trigger: 'blur'},
                        {validator: checkNums, trigger: 'blur'},
                    ],

                },
                categories: [],
            }
        },
        created() {
            request.get("/category/tree").then(res => {
                this.categories = res.data;
                console.log(this.categories)
            })
        },
        methods: {

            handleAvatarSuccess(res, file) {
                // console.log(res)
                if (res.code == "200"){
                    this.form.cover = res.data;
                }

            },
            beforeAvatarUpload(file) {

            },
            save(ruleForm) {
                console.log(this.form)
                // this.form.img = this.imageUrl;
                this.$refs[ruleForm].validate((valid) => {
                    // console.log(valid)
                    if (valid) {
                        request.post("/book/save", this.form).then(res => {
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