<template>
    <div class="add">
        <h2>修改</h2>
        <el-form :inline="true"  label-width="150px" :rules="rules" :model="form" ref="form">
            <el-form-item label="姓名" prop="username">
                <el-input v-model="form.username" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <br/>

            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
            </el-form-item>
            <br/>

            <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
            </el-form-item>
        </el-form>

        <div class="buttons" style="margin-left:180px">
            <el-button type="primary" @click="edit('form')">提交</el-button>
            <!--            <el-button type="danger">取消</el-button>-->
        </div>


    </div>

</template>


<script>
    import request from "@/utils/request";

    export default {
        name: "EditAdmin",
        data() {
            var checkEmail = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('邮箱不能为空'));
                }
                if (!/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(value)) {
                    callback(new Error('邮箱格式错误'));
                } else {
                    callback();
                }
            }
            var checkPhone = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('年龄不能为空'));
                }
                if (!/^1[3456789]\d{9}$/.test(value)) {
                    callback(new Error('请输入合法的手机号'));
                }else{
                    callback();
                }
            }
            return {
                form: {
                    username: '',
                    phone: '',
                    email:''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入姓名', trigger: 'blur'},
                        {min: 3, max: 8, message: '只能输入在 3 ~ 8 个字符', trigger: 'blur'}
                    ],
                    email: [
                        { validator: checkEmail, trigger: 'blur' }
                    ],
                    phone: [
                        {validator: checkPhone, trigger: 'blur' }
                    ]
                }
            }
        },
        created() {
            const id = this.$route.query.id;
            request.get("/admin/"+id).then(res => {
                if (res.code == "200") {
                   this.form = res.data;
                } else {
                    this.$notify.error(res.msg)
                }
            })
        },
        methods: {
            edit(ruleForm) {
                // this.form.img = this.imageUrl;
                this.$refs[ruleForm].validate((valid) => {
                    if (valid) {
                        request.put("/admin/edit", this.form).then(res => {
                            if (res.code == "200") {
                                this.$notify.success('修改成功')
                                this.$router.push("/adminList")
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