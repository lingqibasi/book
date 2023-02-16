<template>
    <div class="add">
        <h2>修改用户</h2>
        <el-form :inline="true" :model="form" label-width="150px" >
            <el-form-item label="姓名" >
                <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
            </el-form-item><br/>

            <el-form-item label="用户名">
                <el-input v-model="form.username" disabled></el-input>
            </el-form-item><br/>

            <el-form-item label="年龄">
                <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
            </el-form-item><br/>

            <el-form-item label="图片">
                <el-upload
                        class="avatar-uploader"
                        action="http://localhost:8090/api/user/upload"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                        >
                    <img v-if="form.img" :src="'http://localhost:8090'+form.img" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item><br/>

            <el-form-item label="性别" >
                <el-radio v-model="form.sex" label="男">男</el-radio>
                <el-radio v-model="form.sex"  label="女">女</el-radio>
            </el-form-item><br/>
            <el-form-item label="账户余额">
                <el-input v-model="form.account" placeholder="请输入账户余额"></el-input>
            </el-form-item><br/>

            <el-form-item label="联系方式">
                <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
            </el-form-item><br/>

            <el-form-item label="地址">
                <el-input v-model="form.address" placeholder="请输入地址"></el-input>
            </el-form-item>
        </el-form>

        <div class="buttons"  style="margin-left:180px">
            <el-button type="primary" @click="edit">修改</el-button>
<!--            <el-button type="danger">取消</el-button>-->
        </div>


    </div>

</template>


<script>
    import request from "@/utils/request";

    export default {
        name: "EditUser",
        data() {
            return {
                form: {
                    name: '',
                    username: '',
                    age: '',
                    img: '',
                    sex: "",
                    phone: '',
                    address: '',
                    account:""
                },
                imageUrl: ''
            };
        },
        created() {
            const id = this.$route.query.id;
            request.get("/user/" + id).then(res => {
                console.log(res)
                this.form = res.data
                // console.log(this.form.img)
            })
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

            edit(){
                // this.form.img = this.imageUrl;
                request.put("/user/edit",this.form).then(res => {
                    if (res.code == "200"){
                        this.$notify.success('修改成功')
                        this.form = {}
                        this.$router.push("/userList")
                        // this.imageUrl = ''
                    }else{
                        this.$notify.error(res.msg)
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
    h2{
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
    .buttons{
        margin-left: 80px;
    }
    .buttons button{
        margin-right: 100px;
    }

</style>