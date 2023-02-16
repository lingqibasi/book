<template>
    <div class="home">
        <!--    头部搜索-->
        <div class="search" style="padding:10px 10px">
            <el-input style="width: 240px" placeholder="请输入图书名称" v-model="params.borrowName"></el-input>
            <el-input style="width: 240px" placeholder="请输入图书标准码" v-model="params.borrowNo"></el-input>
            <el-input style="width: 240px" placeholder="请输入用户名称" v-model="params.userName"></el-input>

            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索
            </el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置
            </el-button>
        </div>
        <!--    <el-button type="primary">按钮</el-button>-->
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="编号"></el-table-column>
            <el-table-column prop="bookName" label="书名"></el-table-column>
            <el-table-column prop="bookNo" width="200px" label="图书标准码"></el-table-column>
            <el-table-column prop="userNo" label="用户ID"></el-table-column>
            <el-table-column prop="userName" label="用户名称"></el-table-column>
            <el-table-column prop="userPhone" label="用户联系方式"></el-table-column>
            <el-table-column prop="score" label="所用积分"></el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>

            <el-table-column prop="days" label="借书天数"></el-table-column>
            <el-table-column prop="createtime" label="借书日期"></el-table-column>
            <el-table-column prop="returnDate" label="归还日期"></el-table-column>
            <el-table-column prop="realDate" label="实际归还日期"></el-table-column>
<!--            <el-table-column prop="updatetime" label="修改日期"></el-table-column>-->

            <el-table-column label="操作">
                <template v-slot="scope">
<!--                    <el-button type="primary" icon="el-icon-edit" circle-->
<!--                               @click="$router.push('/editBorrow?id='+scope.row.id)"></el-button>-->

                    <el-popconfirm
                            title="确定删除这条数据吗？"
                            @confirm="del(scope.row.id)"
                            style="margin-left: 5px"
                    >
                        <el-button type="danger" icon="el-icon-delete" slot="reference" alt="删除" circle></el-button>
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




    </div>

</template>

<script>


    import request from "@/utils/request.js";
    import Cookie from 'js-cookie'

    export default {
        name: 'BackList',
        data() {
            return {
                // value: false,
                tableData: [],
                total:0,
                params: {
                    pageNum: 1,
                    pageSize: 5,
                    borrowName: "",
                    borrowNo:"",
                    userName:""
                },
                category: Cookie.get("admin") ? JSON.parse(Cookie.get("admin")) : {},

            }
        },

        created() {
            this.load()
        },
        methods: {
            returnBook(row){

            },

            load() {
                // fetch("http://localhost:8090/user/list").then(res => res.json()).then(res => {
                //     this.tableData = res
                // })
                request.get("/back/page", {
                    params: this.params
                }).then(res => {
                    if (res.code === "200") {
                        console.log(res.data.list)
                        this.tableData = res.data. list;
                        this.total = res.data.total;
                    }
                })
            },
            reset() {
                this.params = {
                    pageNum: 1,
                    pageSize: 5,
                    name: "",
                    bookNo: ""
                }
            },
            handleCurrentChange(pageNum) {
                // console.log(pageNum)
                this.params.pageNum = pageNum;
                this.load()
            },
            del(id) {
                request.delete("/back/delete/" + id).then(res => {
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