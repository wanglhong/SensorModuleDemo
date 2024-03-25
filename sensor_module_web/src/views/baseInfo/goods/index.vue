<template>
  <lay-container fluid="true" style="padding: 10px">
    <lay-row :space="10">
      <lay-col :md="24">
        <lay-card>
          <!-- 查询搜索框 -->
          <lay-form style="margin-top: 20px">
            <lay-row>
              <lay-col :md="6">
                <lay-form-item label="账号：" label-width="50">
                  <lay-input
                    v-model="searchAccount"
                    style="width: 90%"
                  ></lay-input>
                </lay-form-item>
              </lay-col>
              <lay-col :md="6">
                <lay-form-item label="邮箱：" label-width="50">
                  <lay-input
                    v-model="searchEmail"
                    style="width: 90%"
                  ></lay-input>
                </lay-form-item>
              </lay-col>
              <lay-col :md="6">
                <lay-form-item label-width="0">
                  <lay-button type="primary" @click="toSearch">查询</lay-button>
                  <lay-button @click="toReset">重置</lay-button>
                </lay-form-item>
              </lay-col>
            </lay-row>
          </lay-form>
        </lay-card>
      </lay-col>
      <lay-col :md="24">
        <lay-card>
          <lay-table
            :columns="columns"
            :dataSource="dataSource"
            :loading="loading"
            :default-toolbar="defaultToolbar"
            v-model:selectedKeys="selectedKeys"
            @row="rowClick"
            @change="change"
            style="height: 700px;"
          >
            <template v-slot:toolbar>
              <lay-button size="sm" type="primary">新增</lay-button>
              <lay-button size="sm">删除</lay-button>
            </template>
            <template v-slot:goodsUnitValue="{ data }">
              {{ (data.goodsUnitValue/100).toFixed(2) }}
            </template>
            <template v-slot:operator="{}">
              <lay-button size="xs" type="primary">修改</lay-button>
              <lay-button size="xs">删除</lay-button>
            </template>
            <template v-slot:footer>
<!--              {{ selectedKeys }}-->
              <lay-page v-model="page.pageNum"  :layout="page.layout" v-model:limit="page.pageSize" :pages="page.pages" :total="page.total" @change="change"></lay-page>
            </template>
          </lay-table>
<!--          <br/>-->
<!--          <lay-page v-model="page.pageNum"  :layout="page.layout" v-model:limit="page.pageSize" :pages="page.pages" :total="page.total" @change="change"></lay-page>-->
        </lay-card>
      </lay-col>
    </lay-row>
  </lay-container>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { layer } from '@layui/layer-vue'
import { list } from '@/api/module/goodsController.js'


const loading = ref(false)
const selectedKeys = ref([])
const checkbox = ref(true)
const defaultToolbar = ref(true)
const page = ref({
  // 当前页
  pageNum: 6,
  // 每页数量
  pageSize: 10,
  // 总条数
  total: 100,
  // 显示切页按钮数量
  pages: 3,
  // 不同部分的展示（count -> 总条数, page -> 页码选择器, prev -> 上一页, next -> 下一页, limits -> 每页的数量选择, refresh -> 刷新,  skip -> 跳页,）。
  layout: ref(['count', 'prev', 'page', 'next', 'limits',  'refresh', 'skip'])
})

const columns = [
  {title:"复选",width:"50px",type:"checkbox",fixed:"left",align:"center"},
  {title:"编码",key:"goodsCode",width:"200px",align:"center"},
  {title:"名称",key:"goodsName",width:"200px",align:"center"},
  {title:"单位价值（单位：元）",key:"goodsUnitValue",customSlot:"goodsUnitValue",width:"180px",align:"center"},
  {title:"体积（单位：cm³）",key:"goodsUnitVolume",width:"180px",align:"center"},
  {title:"重量（单位：克）",key:"goodsUnitWeight",width:"180px",align:"center"},
  {title:"描述",key:"goodsDescription",ellipsisTooltip:true,align:"center"},
  {title:"操作",width:"180px",customSlot:"operator",key:"operator",fixed:"right",align:"center"}
]

let dataSource = ref([])

const loadDataSource02 = async () => {
  loading.value = true
  try {
    const { success, code, msg, data } = await list({ page: page.value.pageNum, pageSize: page.value.pageSize })
    if (success) {
      dataSource.value = data
      // page.value.total = data.total // 假设后端返回的data中包含总条目数
    } else {
      layer.msg(msg, { icon: 2 })
    }
  } catch (error) {
    console.error("加载数据失败", error)
    layer.msg('加载数据失败', { icon: 2 })
  } finally {
    loading.value = false
  }
}

const loadDataSource = (page, pageSize) => {
  var response = ref([])
  var startIndex = (page - 1) * pageSize + 1
  var endIndex = page * pageSize

  list({}).then(({ success, code, msg, data }) => {
    setTimeout(() => {
      // loading.value = false;
      if (success) {
        response.value = data
      } else {
        layer.msg(msg, { icon: 2 })
      }
    }, 1000)
  })
  return response
}

// 行单击事件
const rowClick = function(data) {}
// 行双击事件
const rowDoubleClick = function(data) {}
// 分页事件
const change = function({ pageNum, pageSize }) {
  layer.msg("pageNum:" + pageNum + " pageSize:" + pageSize)
  // loading.value = true
  // setTimeout(() => {
  //   dataSource.value = loadDataSource(pageNum, pageSize)
  //   loading.value = false
  // }, 1000)
}
function toSearch() {
  layer.load(2, { time: 3000 })
}
const searchAccount = ref("")
const searchEmail = ref("")
function toReset() {
  searchAccount.value = ""
  searchEmail.value = ""
}

// 使用onMounted生命周期钩子在组件挂载完成后加载数据
onMounted(() => {
  loadDataSource02()
})

</script>

<style scoped>

.table-box {
  margin-top: 10px;
  padding: 10px;
  height: 700px;
  width: 100%;
  border-radius: 4px;
  box-sizing: border-box;
  background-color: #fff;
}

</style>