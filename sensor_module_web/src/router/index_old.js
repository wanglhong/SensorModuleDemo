// import Vue from 'vue'
// import Router from 'vue-router'
//
// Vue.use(Router)
//
// /* 布局 */
// import Layout from '@/layout'
//
// /**
//  * 注意：子菜单仅在路由 children.length >= 1 时出现
//  * 详情请见：https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
//  *
//  * hidden: true                   如果设置为 true，则项目不会显示在侧边栏中（默认为 false）
//  * alwaysShow: true               如果设置为 true，将始终显示根菜单
//  *                                如果未设置 alwaysShow，则当项目具有多个子路由时，
//  *                                它将成为嵌套模式，否则不显示根菜单
//  * redirect: noRedirect           如果设置了 noRedirect，则不会在痕迹导航中进行重定向
//  * name:'router-name'             该名称由<keep-alive>（必须设置!!）使用。
//  * meta : {
//  *     roles: ['admin','editor']    控制页面角色（可以设置多个角色）
//  *     title: 'title'               侧边栏和痕迹导航中的名称显示（推荐集）
//  *     icon: 'svg-name'/'el-icon-x' 边栏中的图标显示
//  *     breadcrumb: false            如果设置为 false，则项目将隐藏在痕迹导航中（默认值为 true）
//  *     activeMenu: '/example/list'  如果设置了路径，边栏将突出显示您设置的路径
//  * }
//  */
//
// /**
//  * constantRoutes
//  * a base page that does not have permission requirements
//  * all roles can be accessed
//  */
// export const constantRoutes = [
//   {
//     path: '/login',
//     component: () => import('@/views/login/index'),
//     hidden: true
//   },
//
//   {
//     path: '/404',
//     component: () => import('@/views/404'),
//     hidden: true
//   },
//
//   {
//     path: '/',
//     component: Layout,
//     redirect: '/dashboard',
//     children: [{
//       path: 'dashboard',
//       name: 'Dashboard',
//       component: () => import('@/views/dashboard/index'),
//       meta: { title: 'Dashboard', icon: 'dashboard' }
//     }]
//   },
//
//   {
//     path: '/example',
//     component: Layout,
//     redirect: '/example/table',
//     name: 'Example',
//     meta: { title: 'Example', icon: 'el-icon-s-help' },
//     children: [
//       {
//         path: 'table',
//         name: 'Table',
//         component: () => import('@/views/table/index'),
//         meta: { title: 'Table', icon: 'table' }
//       },
//       {
//         path: 'tree',
//         name: 'Tree',
//         component: () => import('@/views/tree/index'),
//         meta: { title: 'Tree', icon: 'tree' }
//       }
//     ]
//   },
//
//   {
//     path: '/form',
//     component: Layout,
//     children: [
//       {
//         path: 'index',
//         name: 'Form',
//         component: () => import('@/views/form/index'),
//         meta: { title: 'Form', icon: 'form' }
//       }
//     ]
//   },
//
//   {
//     path: '/nested',
//     component: Layout,
//     redirect: '/nested/menu1',
//     name: 'Nested',
//     meta: {
//       title: 'Nested',
//       icon: 'nested'
//     },
//     children: [
//       {
//         path: 'menu1',
//         component: () => import('@/views/nested/menu1/index'), // Parent router-view
//         name: 'Menu1',
//         meta: { title: 'Menu1' },
//         children: [
//           {
//             path: 'menu1-1',
//             component: () => import('@/views/nested/menu1/menu1-1'),
//             name: 'Menu1-1',
//             meta: { title: 'Menu1-1' }
//           },
//           {
//             path: 'menu1-2',
//             component: () => import('@/views/nested/menu1/menu1-2'),
//             name: 'Menu1-2',
//             meta: { title: 'Menu1-2' },
//             children: [
//               {
//                 path: 'menu1-2-1',
//                 component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
//                 name: 'Menu1-2-1',
//                 meta: { title: 'Menu1-2-1' }
//               },
//               {
//                 path: 'menu1-2-2',
//                 component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
//                 name: 'Menu1-2-2',
//                 meta: { title: 'Menu1-2-2' }
//               }
//             ]
//           },
//           {
//             path: 'menu1-3',
//             component: () => import('@/views/nested/menu1/menu1-3'),
//             name: 'Menu1-3',
//             meta: { title: 'Menu1-3' }
//           }
//         ]
//       },
//       {
//         path: 'menu2',
//         component: () => import('@/views/nested/menu2/index'),
//         name: 'Menu2',
//         meta: { title: 'menu2' }
//       }
//     ]
//   },
//
//   {
//     path: 'external-link',
//     component: Layout,
//     children: [
//       {
//         path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
//         meta: { title: 'External Link', icon: 'link' }
//       }
//     ]
//   },
//
//   // 404 page must be placed at the end !!!
//   { path: '*', redirect: '/404', hidden: true }
// ]
//
// const createRouter = () => new Router({
//   // mode: 'history', // require service support
//   scrollBehavior: () => ({ y: 0 }),
//   routes: constantRoutes
// })
//
// const router = createRouter()
//
// // Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
// export function resetRouter() {
//   const newRouter = createRouter()
//   router.matcher = newRouter.matcher // reset router
// }
//
// export default router
