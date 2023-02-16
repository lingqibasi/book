<template>
    <div class="error">
        <div class="first">404</div>
        <div>未找到页面</div>
        <div class="floter"><el-button type="text" @click="$router.push('/')">返回首页</el-button></div>
    </div>
</template>

<script>

    export default {
        name: "Error"
    }
</script>

<style scoped>

    .error div{
        width: 400px;
        margin: 20px auto;
        text-align: center;
        font-size: 60px;
        color: #8c939d;
        font-style: italic;
    }
    .error .first{
        margin-top: 140px;
    }
    .error .floter button {
        margin-top: 30px;
        font-size: 22px;
    }
</style>