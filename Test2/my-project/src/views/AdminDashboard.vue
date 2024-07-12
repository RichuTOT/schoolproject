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
                <span slot="title">基本情况</span>
              </el-menu-item>
              <el-menu-item index="2">
                <i class="el-icon-message"></i>
                <span slot="title">社团审核</span>
              </el-menu-item>
              <el-menu-item index="3">
                <i class="el-icon-menu"></i>
                <span slot="title">社团管理</span>
              </el-menu-item>
              <el-menu-item index="4">
                <i class="el-icon-menu"></i>
                <span slot="title">活动申请</span>
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
    name: 'AdminDashboard',
    data() {
      return {
        activeMenu: '1'
      };
    },
    methods: {
      handleMenuSelect(key, keyPath) {
        switch (key) {
          case '1':
            this.$router.push({ name: 'Infor' });
          break;
          case '2':
            this.$router.push({ name: 'Audits' });
            break;
          case '3':
            this.$router.push({ name: 'Managment' });
            break;
          case '4':
            this.$router.push({ name: 'Eventa' });
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
  