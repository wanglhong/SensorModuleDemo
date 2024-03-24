import BasicLayout from "@/layouts/BasicLayout.vue"
import Login from "@/views/login/index.vue"

export default [
    {
        // 根地址
        path: "/",
        // 重定向地址
        redirect: "/baseInfo"
    },
    {
        path: "/login",
        component: Login,
        meta: { title: "登录页面" }
    },
    /* 基础信息管理 */
    {
        path: "/baseInfo",
        redirect: "/baseInfo/goods",
        component: BasicLayout,
        meta: { title: "基础信息管理" },
        children: [
            {
                path: "/baseInfo/goods",
                name: "goods",
                component: () => import("../../views/baseInfo/goods/index.vue"),
                meta: {
                    title: "货物管理",
                    requireAuth: true,
                    affix: true,
                    closable: false
                }
            }
        ]
    },
    /* 请求错误页面 */
    {
        path: "/error",
        component: BasicLayout,
        meta: { title: "错误页面" },
        children: [
            {
                path: "/error/401",
                component: () => import("../../views/error/401.vue"),
                meta: { title: "401" }
            },
            {
                path: "/error/403",
                component: () => import("../../views/error/403.vue"),
                meta: { title: "403" }
            },
            {
                path: "/error/404",
                component: () => import("../../views/error/404.vue"),
                meta: { title: "404" }
            },
            {
                path: "/error/500",
                component: () => import("../../views/error/500.vue"),
                meta: { title: "500" }
            }
        ]
    }
]
