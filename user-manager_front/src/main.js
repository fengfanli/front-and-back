import Vue from 'vue'
import App from './App.vue'
import {Button, Container, Main, Aside, Header, Menu, Submenu, MenuItem, MenuItemGroup, Dropdown, DropdownItem, DropdownMenu, Table, TableColumn, Select, Option, Pagination, Form, FormItem, Input, Collapse, CollapseItem, Tabs, 
  TabPane, Message, Loading  } from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';   // 样式文件单独引入，与 完整引入/按需引入 无关
import './assets/less/index.less'
import './assets/css/global.css'

import router from './router'
import axios from 'axios'



Vue.config.productionTip = false
Vue.prototype.$http = axios
Vue.prototype.$message = Message

Vue.use(Button)

// Container 容器
Vue.use(Container)
Vue.use(Main)
Vue.use(Aside)
Vue.use(Header)

// Aside 侧边栏
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)

// Header 头部 下拉菜单
Vue.use(Dropdown)
Vue.use(DropdownItem)
Vue.use(DropdownMenu)

// 表格
Vue.use(Table)
Vue.use(TableColumn)

// 下拉框
Vue.use(Select)
Vue.use(Option)

// 分页
Vue.use(Pagination)

// 表单
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)

// 折叠面板
Vue.use(Collapse)
Vue.use(CollapseItem)

// Tab
Vue.use(Tabs)
Vue.use(TabPane)

Vue.use(Loading.directive)


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
