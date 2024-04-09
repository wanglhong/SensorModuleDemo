import { createApp } from 'vue'
import App from '@/App.vue'

import Layui from '@layui/layui-vue'
import '@layui/layui-vue/lib/index.css'

import Router from '@/router'
import Store from '@/store'
import '@/mockjs'

const app = createApp(App)

app.use(Store)
app.use(Router)

app.use(Layui).mount('#app')
