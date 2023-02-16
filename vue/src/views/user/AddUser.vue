<template>
    <div class="add">
        <h2>添加用户</h2>
        <el-form :inline="true"  label-width="150px" :rules="rules" :model="form" ref="form">
            <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <br>


            <!--            <el-form-item label="用户名">-->
            <!--                <el-input v-model="form.username" placeholder="请输入用户名"></el-input>-->
            <!--            </el-form-item><br/>-->

            <el-form-item label="年龄" prop="age">
                <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
            </el-form-item><br>

            <el-form-item label="图片">
                <el-upload
                        class="avatar-uploader"
                        action="http://localhost:8090/api/user/upload"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                >
                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item><br>

            <el-form-item label="性别">
                <!--<el-input v-model="form.sex" placeholder="请输入性别"></el-input>-->
                <el-radio v-model="form.sex" label="男">男</el-radio>
                <el-radio v-model="form.sex" label="女">女</el-radio>
            </el-form-item><br>

            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
            </el-form-item><br>

            <el-form-item label="地址">
                <el-input v-model="form.address" placeholder="请输入地址"></el-input>
            </el-form-item><br>
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
        name: "AddUser",
        data() {
            var checkAge = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('年龄不能为空'));
                }
                if (!/^[0-9]+$/.test(value)) {
                    callback(new Error('请输入数字值'));
                } else if (value > 120 || value < 1) {
                        callback(new Error('请输入合法年龄'));
                    } else {
                        callback();
                    }
            }
            var checkPhone = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('电话不能为空'));
                }
                if (!/^1[3456789]\d{9}$/.test(value)) {
                    callback(new Error('请输入合法的手机号'));
                }else{
                    callback();
                }
            }
            return {
                form: {
                    name: '',
                    username: '',
                    age: '',
                    img: '',
                    sex: "男",
                    phone: '',
                    address: ''
                },
                imageUrl: '',
                rules: {
                    name: [
                        {required: true, message: '请输入姓名', trigger: 'blur'},
                        {min: 3, max: 7, message: '只能输入在 3 ~ 7 个字符', trigger: 'blur'}
                    ],
                    age: [
                        { validator: checkAge, trigger: 'blur' }
                    ],
                    phone: [
                        {validator: checkPhone, trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleAvatarSuccess(res, file) {
                console.log(res)
                this.imageUrl = URL.createObjectURL(file.raw);
                this.form.img = res.data.img;
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },

            save(ruleForm) {
                // this.form.img = this.imageUrl;
                this.$refs[ruleForm].validate((valid) => {
                    // console.log(valid)
                    if (valid) {
                        request.post("/user/save", this.form).then(res => {
                            if (res.code == "200") {
                                this.$notify.success('新增成功')
                                this.form = {sex: '男'}
                                this.imageUrl = ''
                                // 只将有检验的表单回复默认
                                // this.$refs[ruleForm].resetFields();
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