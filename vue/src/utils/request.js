import axios from 'axios'
import router from "@/router";
import Cookie from 'js-cookie'

const request = axios.create({
    baseURL: 'http://localhost:8090/api',
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    var admin = Cookie.get("admin");
    if (admin) {
        // router.push('/login')
        config.headers['token'] = JSON.parse(admin).token
    }
    // config.headers['token'] = user.token;  // 设置请求头
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if (res.code === "401") {
            router.push("/login")
        }
        return res;
    },
    error => {
        // console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request