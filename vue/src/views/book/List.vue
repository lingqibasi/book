<template>
    <div class="home">
        <!--    头部搜索-->
        <div class="search" style="padding:10px 10px">
            <el-input style="width: 240px" placeholder="请输入图书名称" v-model="params.name"></el-input>
            <el-input style="width: 240px" placeholder="请输入图书标准码" v-model="params.bookNo"></el-input>

            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索
            </el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置
            </el-button>
        </div>
        <!--    <el-button type="primary">按钮</el-button>-->
        <el-table :data="tableData" stripe>
            <el-table-column prop="id" label="编号" width="60"></el-table-column>
            <el-table-column prop="name" label="书名"></el-table-column>
            <el-table-column prop="cover" label="封面" width="120">
                <template v-slot="scope">
                    <el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]" alt="" style="width: 100px; height: 100px"/>
                </template>
            </el-table-column>
            <el-table-column prop="description" width="200px" label="描述"></el-table-column>
            <el-table-column prop="publishDate" label="出版日期"></el-table-column>
            <el-table-column prop="author" label="作者"></el-table-column>
            <el-table-column prop="publicer" label="出版社"></el-table-column>
            <el-table-column prop="category" label="类型"></el-table-column>
            <el-table-column prop="bookNo" label="图书标准码"></el-table-column>
            <el-table-column prop="score" label="借书积分" width="60"></el-table-column>
            <el-table-column prop="nums" label="数量"></el-table-column>
            <el-table-column prop="createTime" label="创建日期"></el-table-column>
            <el-table-column prop="updateTime" label="修改日期"></el-table-column>

            <el-table-column label="操作" width="140">
                <template v-slot="scope">
                    <el-button type="primary" icon="el-icon-edit" circle
                               @click="$router.push('/editBook?id='+scope.row.id)"></el-button>
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
        name: 'BookList',
        data() {
            return {
                // value: false,
                tableData: [],
                total:0,
                params: {
                    pageNum: 1,
                    pageSize: 5,
                    name: "",
                    bookNo:""
                },
                category: Cookie.get("admin") ? JSON.parse(Cookie.get("admin")) : {},

            }
        },

        created() {
            this.load()
        },
        methods: {

            load() {
                // fetch("http://localhost:8090/user/list").then(res => res.json()).then(res => {
                //     this.tableData = res
                // })
                request.get("/book/page", {
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
                request.delete("/book/delete/" + id).then(res => {
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