<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <div class="header-content">
          <span>欢迎</span>
          <el-button type="primary" @click="logout">退出登录</el-button>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px" class="aside">
          <el-menu
            :default-active="activeMenu"
            class="el-menu-vertical-demo"
            @select="handleMenuSelect"
          >
            <el-menu-item index="1">
              <i class="el-icon-message"></i>
              <span slot="title">Menu</span>
            </el-menu-item>
            <el-menu-item index="2">
              <i class="el-icon-message"></i>
              <span slot="title">个人中心</span>
            </el-menu-item>
            <el-menu-item index="3">
              <i class="el-icon-menu"></i>
              <span slot="title">搜索社团</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">Navigator Three</span>
            </el-menu-item>
            <el-menu-item index="5">
              <i class="el-icon-message"></i>
              <span slot="title">Navigator Four</span>
            </el-menu-item>
            <el-menu-item index="6">
              <i class="el-icon-message"></i>
              <span slot="title">Navigator Five</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main class="main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      activeMenu: '1'
    };
  },
  methods: {
    handleMenuSelect(key, keyPath) {
      switch (key) {
        case '1':
          this.$router.push({ name: 'Content' });
          break;
        case '2':
          this.$router.push({ name: 'PageOne' });
          break;
        case '3':
          this.$router.push({ name: 'PageTwo' });
          break;
        case '4':
          this.$router.push({ name: 'PageThree' });
          break;
        case '5':
          this.$router.push({ name: 'PageFour' });
          break;
        case '6':
          this.$router.push({ name: 'PageFour' });
          break;
        default:
          break;
      }
    },
    logout() {
      // 退出登录逻辑
      console.log('退出登录');
      this.$router.push({ name: 'Login' });
    }
  }
};
</script>

<style scoped>
.common-layout {
  min-height: 100vh;
}

.header {
  background-color: #409EFF;
  color: #ffffff;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
}

.header-content {
  display: flex;
  align-items: center;
}

.aside {
  background-color: #f5f5f5;
}

.main {
  background-color: #fff;
  height: calc(100vh - 60px); /* 减去header的高度 */
  padding: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
