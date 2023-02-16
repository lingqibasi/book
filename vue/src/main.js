import Vue from 'vue'
import App from './App.vue'
import router from './router'
import "@/assets/common.css"
import ElmentUI from "element-ui"
import "element-ui/lib/theme-chalk/index.css"

import SlideVerify from 'vue-monoplasty-slide-verify';
Vue.use(SlideVerify);

Vue.use(ElmentUI)

Vue.config.productionTip = false

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
