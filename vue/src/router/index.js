import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout'
import Cookies from 'js-cookie'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Layout',
        component: Layout,
        redirect:'/home',
        children: [
            {path: 'home', name: 'Home', component:  () => import('@/views/home/HomeView')
            },
            // <=========> user =========>
            {path: 'userList', name: 'UserList', component: () => import('@/views/user/User')
            },
            {path: 'addUser', name: 'AddUser', component: () => import('@/views/user/AddUser')
            },
            {path: 'editUser', name: 'EditUser', component: () => import('@/views/user/EditUser')
            },

            // <=========> user ==========>
            {path: 'adminList', name: 'AdminList', component: () => import('@/views/admin/List')
            },
            {path: 'addAdmin', name: 'AddAdmin', component: () => import('@/views/admin/Add')
            },
            {path: 'editAdmin', name: 'EditAdmin', component: () => import('@/views/admin/Edit')
            },

            // <=========> category ==========>
            {path: 'categoryList', name: 'CategoryList', component: () => import('@/views/category/List')
            },
            {path: 'addCategory', name: 'AddCategory', component: () => import('@/views/category/Add')
            },
            {path: 'editCategory', name: 'EditCategory', component: () => import('@/views/category/Edit')
            },

            // <=========> book ==========>
            {path: 'bookList',name: 'BookList',component: () => import("@/views/book/List")},
            {path: 'addBook',name: 'AddBook',component: () => import("@/views/book/Add")},
            {path: 'editBook',name: 'EditBook',component: () => import("@/views/book/Edit")},
            // <=========> borrow ==========>
            {path: 'borrowList',name: 'BorrowList',component: () => import("@/views/borrow/List")},
            {path: 'addBorrow',name: 'AddBorrow',component: () => import("@/views/borrow/Add")},
            {path: 'editBorrow',name: 'EditBorrow',component: () => import("@/views/borrow/Edit")},
            // <=========> return ==========>
            {path: "backBook",name:"BackList",component:() => import("@/views/back/List")}
        ]
    },
    {path: '/login', name: 'Login', component: () => import('@/views/login/Login')
    },
    {path: '*', component: () => import('@/views/error')
    }


]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to,from,next) => {
    if(to.path === '/login') next()
    const admin = Cookies.get("admin")
    if (!admin && to.path !== '/login') return next("/login")
    next()
})

export default router
