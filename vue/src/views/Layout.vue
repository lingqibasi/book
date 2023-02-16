<template>
    <div>
        <!--    头部start-->
        <div id="header">
            <div id="header_left">
                <img src="@/assets/logo.png" class="logo"/>
                <span class="title"></span>
            </div>
            <div class="header_right">
                <el-dropdown size="medium" trigger="click">
                    <span class="el-dropdown-link" style="cursor:pointer">
                        {{admin.username}}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item><div @click="logout">退出</div></el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>

            </div>
        </div>

        <!--    头部end-->


        <!--    侧边栏和主体start-->
        <div id="section">
            <!--        侧边栏start-->
            <div id="aside">
                <el-menu :default-active="$route.path" router class="el-menu-demo">
                    <el-menu-item index="/home">
                        <i class="el-icon-s-home"></i>
                        <span>首页</span>
                    </el-menu-item>
                    <el-submenu index="/book">
                        <template slot="title"><i class="el-icon-reading"></i> <span>图书管理</span></template>
                        <el-menu-item index="/bookList">图书列表</el-menu-item>
                        <el-menu-item index="/addBook">图书添加</el-menu-item>
                    </el-submenu>
                    <el-submenu index="/borrow">
                        <template slot="title"><i class="el-icon-notebook-1"></i> <span>借书管理</span></template>
                        <el-menu-item index="/borrowList">借书列表</el-menu-item>
                        <el-menu-item index="/addBorrow">借书</el-menu-item>
                    </el-submenu>
                    <el-submenu index="/back">
                        <template slot="title"><i class="el-icon-notebook-2"></i> <span>还书管理</span></template>
                        <el-menu-item index="/backBook">还书列表</el-menu-item>
<!--                        <el-menu-item index="/addBorrow"></el-menu-item>-->
                    </el-submenu>
                    <el-submenu index="/user">
                        <template slot="title"><i class="el-icon-info"></i> <span>用户管理</span></template>
                        <el-menu-item index="/userList">用户列表</el-menu-item>
                        <el-menu-item index="/addUser">用户添加</el-menu-item>
                    </el-submenu>
                    <el-submenu index="/admin">
                        <template slot="title"><i class="el-icon-s-custom"></i> <span>管理员管理</span></template>
                        <el-menu-item index="/adminList">管理员列表</el-menu-item>
                        <el-menu-item index="/addAdmin">管理员添加</el-menu-item>
                    </el-submenu>
                    <el-submenu index="/category">
                        <template slot="title"><i class="el-icon-s-operation"></i> <span>图书分类管理</span></template>
                        <el-menu-item index="/categoryList">图书分类列表</el-menu-item>
                        <el-menu-item index="/addCategory">图书分类添加</el-menu-item>
                    </el-submenu>
                </el-menu>
            </div>
            <!--        侧边栏end-->

            <div id="main">
                <router-view/>
            </div>
        </div>

        <!--    侧边栏和主体end-->

    </div>

</template>

<script>
    import Cookie from "js-cookie";
    export default {
        name: "Layout",
        data(){
            return{
                admin: Cookie.get("admin") ? JSON.parse(Cookie.get("admin")) :{},
            }
        },
        // created() {
        //     this.user = Cookie.get("user") ? JSON.parse(Cookie.get("user")) :{}
        // },
        methods:{
            logout(){
                Cookie.remove("admin")
                this.$router.push("/login")
            }
        }
    }
</script>

<style scoped>
    #header {
        display: flex;
        background-color: white;
    }

    #header_left {
        width: 300px;
        height: 60px;
        line-height: 60px;
        position: relative;
    }

    #header_left .logo {
        position: absolute;
        width: 45px;
        top: 5px;
        left: 25px;
    }

    #header_left .title {
        position: absolute;
        left: 70px;
    }

    .header_right {
        text-align: right;
        padding-top: 15px;
        padding-right: 20px;
        flex: 1;
    }
    .header_right .el-dropdown-link{
        font-size: 22px;
    }

    #section {
        display: flex;
        margin-top: 3px;
    }

    #section #aside {
        width: 240px;
        overflow: hidden;
        background-color: white;
        margin-right: 5px;
        min-height: calc(100vh - 63px);
        /*flex: 1;*/
    }

    #section #main {
        flex: 1;
        /*width: 0;*/
        background-color: white;
    }
</style>