<template>
  <header>
    <div class="l-content">
      <el-button class="el-icon-menu" size="mini"></el-button>
      <h3 style="color: #fff">首頁</h3>
    </div>
    <div class="r-content">
      <el-dropdown trigger="click" szie="mini" :hide-on-click="false">
        <span class="el-dropdown-link">
          <img class="user" :src="userImg" />
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>个人中心</el-dropdown-item>
          <el-dropdown-item>退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </header>
</template>
<script>
export default {
  name: 'commonHeaderMenu',
  data() {
    return {
      userImg: require('../assets/images/user.png')
    }
  }
}
</script>
<style lang="less" scoped>
header {
  display: flex;
  height: 100%;
  justify-content: space-between;
  align-items: center;
}
.l-content {
  display: flex;
  align-items: center;
  .el-button {
    margin-right: 20px;
  }
}
.r-content {
  .user {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.el-icon-arrow-down {
  font-size: 12px;
}
</style>
