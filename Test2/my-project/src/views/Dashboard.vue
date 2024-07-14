<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <div class="header-content">
          <span>登录平台</span>
          <el-button class="logout-button" type="primary" @click="logout">退出登录</el-button>
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
              <span slot="title">首页</span>
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
              <i class="el-icon-message"></i>
              <span slot="title">社团交流</span>
            </el-menu-item>
            <el-menu-item index="5">
              <i class="el-icon-setting"></i>
              <span slot="title">创建社团</span>            
            </el-menu-item>       
            <el-menu-item index="6">
              <i class="el-icon-setting"></i>
              <span slot="title">活动发布</span>
            </el-menu-item> 
            <el-menu-item index="7">
              <i class="el-icon-setting"></i>
              <span slot="title">成员管理</span>
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
import { ElMessage } from 'element-plus';

export default {
  name: 'Dashboard',
  data() {
    return {
      activeMenu: '1',
      userRole: localStorage.getItem('role') || 'member' // assuming you store userRole in localStorage
    };
  },
  methods: {
    handleMenuSelect(key, keyPath) {
      console.log('Selected menu item:', key);
      console.log('Current user role:', this.userRole);

      if ((key === '6' || key === '7') && this.userRole !== 'clubleader') {
        console.log('User is not clubleader, showing warning message.');
        ElMessage({
          message: '只有成立了社团才能使用该功能',
          type: 'warning',
        });
        return;
      }

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
          this.$router.push({ name: 'PageFour' });
          break;
        case '5':
          this.$router.push({ name: 'PageFive' });
          break;
        case '6':
          this.$router.push({ name: 'PageThree' });
          break;
        case '7':
          this.$router.push({ name: 'Members' });
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
  },
  created() {
    console.log('Dashboard created, user role:', this.userRole);
    this.$router.push({ path: '/dashboard/content' });
  }
};
</script>

<style scoped>
.common-layout {
  min-height: 100vh;
}

.header {
  background-color: #409EFF;
  width: 100%;
  color: #000000;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.logout-button {
  margin-left: auto;
}

.aside {
  background-color: #f5f5f5;
}

.main { 
  background-color: #fff;
  height: 700px;
  width: 1500px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); 
}
</style>
