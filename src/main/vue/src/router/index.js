import VueRouter from "vue-router"
import Vue from 'vue'
import student_login from "@/components/student/login.vue"
import student_index from "@/components/student/index.vue"
import student_find_money from "@/components/student/find_money.vue"
import student_find_list from "@/components/student/find_list.vue"
import student_add_money from "@/components/student/add_money.vue"
import student_lost_card from "@/components/student/lost_card.vue"

import face from "@/components/pay/face.vue"
import pay from "@/components/pay/pay.vue"








Vue.use(VueRouter)

const router = new VueRouter({
    routes : [
    {path : '/student/login',component:student_login},
    {path : '/student/index',component:student_index},
    {path : '/student/find_money',component:student_find_money},
    {path : '/student/find_list',component:student_find_list},
    {path : '/student/add_money',component:student_add_money},
    {path : '/student/lost_card',component:student_lost_card},
    {path : '/pay/face',component:face},
    {path : '/pay/pay',component:pay},
    {path : '/admin/index',component:() => import('@/components/admin/index.vue'),meta:{title:"首页"},
    children:[
        {path:'/admin/student',component:() => import('@/components/admin/student.vue'),meta:{title:"学生管理"},},
        {path:'/admin/card',component:() => import('@/components/admin/card.vue'),meta:{title:"一卡通管理"},},
        {path:'/admin/admin',component:() => import('@/components/admin/admin.vue'),meta:{title:"管理人员管理"},},
        {path: '/admin/lost',component:() => import('@/components/admin/lost.vue'),meta:{title:"挂失管理"},},
        {path: '/admin/me',component:() => import('@/components/admin/me.vue'),meta:{title:"个人信息"},},
        {path : '/admin/teacher',component:() => import('@/components/admin/teacher.vue'),meta:{title:"教师信息"},},
        {path : '/admin/front',component:() => import('@/components/admin/front.vue')},
        {path : '/admin/shop',component:() => import('@/components/admin/shop.vue')},

    ]
},
    {path : '/admin/indexs',component:() => import('@/components/admin/indexs.vue')},
    {path : '/admin/student',component:() => import('@/components/admin/student.vue')},
    {path : '/admin/admin',component:() => import('@/components/admin/admin.vue')},
    {path : '/admin/goods',component:() => import('@/components/admin/goods.vue')},
    {path : '/admin/login',component:() => import('@/components/admin/login.vue')},




    {path : '/pay/login',component:() => import('@/components/pay/login.vue')},
    {path : '/pay/new_login',component:() => import('@/components/pay/new_login.vue')},
    {path : '/pay/index',component:() => import('@/components/pay/index.vue'),
    children:[
        {path : '/pay/pay_it',component:() => import('@/components/pay/pay_it.vue')},
        {path : '/pay/bill',component:() => import('@/components/pay/bill.vue')},
        {path : '/pay/admin',component:() => import('@/components/pay/admin.vue')},
        {path : '/pay/money',component:() => import('@/components/pay/money.vue')},
        {path : '/pay/user',component:() => import('@/components/pay/user.vue')},
        {path : '/pay/class',component:() => import('@/components/pay/class.vue')},
        {path : '/pay/type',component:() => import('@/components/pay/type.vue')},
    ]
    },

        {path : '/book/index',component:() => import('@/components/book/index.vue')},

        {path : '/view',component:() => import('@/view/hello')},

        {path : '/face/come',component:() => import('@/components/face/come.vue')},
        {path : '/face/go',component:() => import('@/components/face/go.vue')},
        {path : '/face/index',component:() => import('@/components/face/index.vue')},


        {path : '/face/test',component:() => import('@/components/face/bbb.vue')},

        {path : '/teacher/login',component:() => import('@/components/teacher/login.vue')},
        {path : '/teacher/classroom',component:() => import('@/components/teacher/classroom.vue')},

        {path : '/teacher/index',component:() => import('@/components/teacher/index.vue'),
            children:[
                {path : '/teacher/check_it',component:() => import('@/components/teacher/check_it.vue')},
                {path : '/teacher/sick',component:() => import('@/components/teacher/sick.vue')},
                {path : '/teacher/come_go',component:() => import('@/components/teacher/come_go.vue')},
                {path : '/teacher/classes',component:() => import('@/components/teacher/classes.vue')},
                {path : '/teacher/classes/:id',component:() => import('@/components/teacher/class_check.vue')},
            ]
        }
















],


}

);





// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
next()


});


export default router
