<template>
  <el-container style="height: 100%">
    <el-aside width="auto">
      <common-aside-menu></common-aside-menu>
    </el-aside>
    <el-container>
      <el-header>
        <common-header-menu/>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import commonAsideMenu from '@/components/commonAsideMenu.vue'
import commonHeaderMenu from '@/components/commonHeaderMenu.vue'

export default {
  name: 'Main',
  components: {
    commonAsideMenu,
    commonHeaderMenu
  },
  data() {
    return {
    }
  },
}
</script>

<style lang="less" scoped>
.el-header {
  background-color: #333;
}

.el-main {
  padding-top: 0;
}
</style>