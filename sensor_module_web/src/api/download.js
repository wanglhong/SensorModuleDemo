import axios from 'axios'
import { layer } from '@layui/layui-vue'
import { useUserStore } from '@/store/user.js'
import router from '@/router/index.js'
import { saveAs } from 'file-saver'

const config = {
  timeout: 5000,
  baseURL: 'http://wlih.cn:8080',
}

/**
 * 解决Long类型精度失真
 * @type {(function(*): *)[]}
 */
// axios.defaults.transformResponse = [
// function (data) {
// const json = JSONbig({
// storeAsString: true,
// })
// return json.parse(data)
// },
// ]

class Download {
  constructor(config) {
    this.service = axios.create(config)

    /* 请求拦截 */
    this.service.interceptors.request.use(
      (config) => {
        const userInfoStore = useUserStore()
        if (userInfoStore.token) {
          config.headers.token = userInfoStore.token
        } else {
          if (router.currentRoute.value.path !== '/login') {
            router.push('/login')
          }
        }
        return config
      },
      (error) => {
        return Promise.reject(error)
      }
    )

    /* 响应拦截 */
    this.service.interceptors.response.use(
      (response) => {
        switch (response.data.code) {
          case 200:
            return response.data
          case 500:
            return response.data
          case 99998:
            layer.confirm('会话超时, 请重新登录', {
              icon: 2,
              yes: function () {
                router.push('/login')
                layer.closeAll()
              },
            })
            return response.data
          default:
            return response.data
        }
      },
      (error) => {
        return Promise.reject(error)
      }
    )
  }

  download(url, params, filename) {
    return this.service
      .post(url, params, {
        headers: { 'Content-Type': 'application/octet-stream' },
        responseType: 'blob',
      })
      .then(async (data) => {
        if (data && data.success === false) {
          layer.msg(data.msg, { icon: 2, time: 2000 })
        }
        const blob = new Blob([data])
        saveAs(blob, filename)
      })
      .catch((r) => {
        console.error(r)
        layer.msg('下载文件出现错误，请联系管理员！', { icon: 2, time: 2000 })
        downloadLoadingInstance.close()
      })
  }
}

export default new Download(config)
