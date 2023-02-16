<template>
    <div class="add">
        <h2>修改分类</h2>
        <el-form :inline="true"  label-width="150px" :rules="rules" :model="form" ref="form">
            <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入名称"></el-input>
            </el-form-item>
            <br/>

            <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
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
        name: "EditCategory",
        data() {

            return {
                form: {
                    name:"",
                    remark:""
                },
                rules: {
                    username: [
                        {required: true, message: '请输入分类名称', trigger: 'blur'},
                    ],

                }
            }
        },
        created() {
            const id = this.$route.query.id;
            request.get("/category/"+id).then(res => {
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
                        request.put("/category/edit", this.form).then(res => {
                            if (res.code == "200") {
                                this.$notify.success('修改成功')
                                this.$router.push("/categoryList")
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