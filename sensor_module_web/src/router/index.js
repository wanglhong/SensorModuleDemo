import { createRouter, createWebHashHistory } from "vue-router"
import NProgress from "nprogress"
import "nprogress/nprogress.css"
import routes from "@/router/module/base-routes.js"
import { useUserStore } from "@/store/user.js"

NProgress.configure({ showSpinner: false })

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

/**
 * Router 前置拦截
 *
 * 1.验证 token 存在, 并且有效, 否则 -> login.vue
 * 2.验证 permission 存在, 否则 -> 403.vue
 * 3.验证 router 是否存在, 否则 -> 404.vue
 *
 * @param to 目标
 * @param from 来至
 */
router.beforeEach((to, from, next) => {
    NProgress.start()

    const userStore = useUserStore()

    if (to.meta.requireAuth) {
        next()
    } else if (to.matched.length == 0) {
        next({ path: "/error/404" })
    } else {
        next()
    }
})

router.afterEach(() => {
    NProgress.done()
})

export default router
