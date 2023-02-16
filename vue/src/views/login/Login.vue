<template>
    <div style="position: relative">
<!--        <el-card class="cover" v-if="loginAdmin.id">-->
<!--            <slide-verify :l="42"-->
<!--                          :r="10"-->
<!--                          :w="310"-->
<!--                          :h="155"-->
<!--                          slider-text="向右滑动"-->
<!--                          @success="onSuccess"-->
<!--                          @fail="onFail"-->
<!--                          @refresh="onRefresh"-->
<!--            ></slide-verify>-->
<!--        </el-card>-->

        <div class="login">
            <div class="title">登录</div>
            <el-form ref="admin" :model="admin" :rules="rules">
                <el-form-item label-width="30px" prop="username">
                    <el-input v-model="admin.username" placeholder="请输入用户名..." prefix-icon="el-icon-user"></el-input>
                </el-form-item>
                <el-form-item  label-width="30px" prop="password">
                    <el-input v-model="admin.password" show-password placeholder="请输入密码..." prefix-icon="el-icon-lock"></el-input>
                </el-form-item>
                <el-form-item label-width="30px">
                    <el-button type="primary" style="width: 100%" size="medium" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>

</template>


<script>

    import  request from "@/utils/request";
    import Cookie from 'js-cookie'
    export default {
        name: "Login",
        data(){
            return {
                loginAdmin:{},
                admin:{},
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                    ]
                }
            }
        },
        methods:{

            onSuccess() {
                Cookie.set('admin', JSON.stringify(this.loginAdmin))
                this.$router.push('/')
                this.$notify.success("登录成功")
            },
            onFail() {

            },
            onRefresh() {
                console.log('refresh')
            },

            login(){
                this.$refs['admin'].validate((valid) => {
                    if (valid){
                        request.post("/admin/login",this.admin).then(res => {
                            if (res.code == "200"){
                                this.loginAdmin = res.data
                                console.log(this.loginAdmin)


                                Cookie.set('admin', JSON.stringify(this.loginAdmin))
                                this.$router.push('/')
                                this.$notify.success("登录成功")
                                // this.$notify.success("登录成功")
                                // if (res.data != null){
                                //     Cookie.set("admin",JSON.stringify(res.data))
                                // }
                                // this.$router.push("/home")
                            }else{
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
    body{
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        overflow: hidden;
    }
    .login{
        width: 500px;
        height: 400px;
        background-color: white;
        border-radius: 15px;
        margin: 200px auto;
        padding: 50px;
    }
    .title{
        /*padding: 60px ;*/
        text-align: center;
        margin: 50px;
        /*padding-top: 120px;*/
        font-size: 36px;
        color: dodgerblue;
        font-weight: bold;
    }
    .cover {
        width: fit-content;
        background-color: white;
        position: absolute;
        top:50%;
        left:50%;
        transform: translate(-50%, -50%);
        z-index: 1000;
    }
</style>