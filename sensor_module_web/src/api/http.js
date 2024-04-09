import axios from 'axios'
import { layer } from '@layui/layui-vue'
import { useUserStore } from '@/store/user.js'
import router from '@/router/index.js'
import JSONbig from 'json-bigint'

const JSONbigString = new JSONbig({ storeAsString: true })

export const baseURL = 'http://localhost:8080'

const config = {
  timeout: 5000,
  baseURL: baseURL,
  transformResponse: [
    function (data) {
      if (typeof data === 'string') {
        return JSONbigString.parse(data)
      } else {
        return data
      }
    },
  ],
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

class Http {
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
            break
        }
      },
      (error) => {
        return Promise.reject(error)
      }
    )
  }

  /* GET 方法 */
  get(url, params, _object = {}) {
    return this.service.get(url, { params, ..._object })
  }
  /* POST 方法 */
  post(url, params, _object = {}) {
    return this.service.post(url, params, _object)
  }
  /* PUT 方法 */
  put(url, params, _object = {}) {
    return this.service.put(url, params, _object)
  }
  /* DELETE 方法 */
  delete(url, params, _object = {}) {
    return this.service.delete(url, { params, ..._object })
  }

  download(url, params, filename) {
    return this.service.post(url, params, {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      responseType: 'blob',
    })
  }
}

export default new Http(config)
