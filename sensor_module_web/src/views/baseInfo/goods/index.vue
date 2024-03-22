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
            :page="page"
            :columns="columns"
            :dataSource="dataSource"
            :loading="loading"
            :default-toolbar="defaultToolbar"
            v-model:selectedKeys="selectedKeys"
            @row="rowClick"
            @change="change"
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
            <!-- <template v-slot:footer>
              {{ selectedKeys }}
            </template> -->
          </lay-table>
        </lay-card>
      </lay-col>
    </lay-row>
  </lay-container>
</template>

<script lang="ts">
import { ref, watch } from 'vue'
import { layer } from '@layui/layer-vue'
import { list } from '../../../api/module/goodsController'
import { List, number } from 'echarts';

export default {
  setup() {
    const loading = ref(false);
    const selectedKeys = ref([]);
    const checkbox = ref(true);
    const defaultToolbar = ref(true);
    const page:object = ref({ total: 100, limit: 10, current: 2 });

    const columns = [
      {
        title: '复选',
        width: '50px',
        type: 'checkbox',
        fixed: 'left',
        align: 'center'
      },
      {
        title: '编码',
        key: 'goodsCode',
        width: '200px',
        align: 'center'
      },
      {
        title: '名称',
        key: 'goodsName',
        width: '200px',
        align: 'center'
      },
      {
        title: '单位价值（单位：元）',
        key: 'goodsUnitValue',
        customSlot: 'goodsUnitValue',
        width: '180px',
        align: 'center'
      },
      {
        title: '体积（单位：cm³）',
        key: 'goodsUnitVolume',
        width: '180px',
        align: 'center'
      },
      {
        title: '重量（单位：克）',
        key: 'goodsUnitWeight',
        width: '180px',
        align: 'center'
      },
      {
        title: '描述',
        key: 'goodsDescription',
        ellipsisTooltip: true,
        align: 'center'
      },
      {
        title: '操作',
        width: '180px',
        customSlot: 'operator',
        key: 'operator',
        fixed: 'right',
        align: 'center'
      }
    ]

    let dataSource = ref([]);

    const loadDataSource = (page: any, pageSize: number) => {
      var response = ref([]);
      var startIndex = (page - 1) * pageSize + 1;
      var endIndex = page * pageSize;
      
      list({}).then(({ success, code, msg, data }) => {
        setTimeout(() => {
          // loading.value = false;
          if (success) {
            response.value = data;
          } else {
            layer.msg(msg, { icon: 2 })
          }
        }, 1000)
      });
      return response;
    };
    
    // 行单击事件
    const rowClick = function (data: any) {}
    // 行双击事件
    const rowDoubleClick = function (data: any) {}
    // 分页事件
    const change = function ({ current, limit }: any) {
      layer.msg('current:' + current + ' limit:' + limit);
      loading.value = true;
      setTimeout(() => {
        dataSource.value = loadDataSource(current, limit);
        loading.value = false;
      }, 1000);
    }
    function toSearch() {
      layer.load(2, { time: 3000 })
    }
    const searchAccount = ref('')
    const searchEmail = ref('')
    function toReset() {
      searchAccount.value = ''
      searchEmail.value = ''
    }

    loading.value = false;
    return {
      loading,
      columns,
      dataSource,
      selectedKeys,
      checkbox,
      defaultToolbar,
      page,
      rowClick,
      rowDoubleClick,
      change,
      toReset,
      toSearch,
      searchAccount,
      searchEmail
    }
  }
}
list({}).then(({ success, code, msg, data }) => {
  setTimeout(() => {
    if (success) {
      console.log("data --> " + JSON.stringify(data));
      dataSource = data;
      console.log("dataSource --> " + JSON.stringify(dataSource));
    } else {
      layer.msg(msg, { icon: 2 })
    }
  }, 1000)
});
</script>
