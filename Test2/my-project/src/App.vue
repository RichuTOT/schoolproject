<template>
  <div id="app">

    <header class="header2">
      社团联盟
    </header>

    <router-view></router-view>
    <nav v-if="showNav">
      <!--导航栏-->
      <router-link to="/">登录</router-link>
      <router-link to="/register">注册</router-link>
    </nav>

    
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      showNav: true
    };
  },
  watch: {
    $route(to, from) {
      this.updateNavVisibility(to);
    }
  },
  created() {
    this.updateNavVisibility(this.$route);
  },
  methods: {
    updateNavVisibility(route) {
      const hiddenNavPages = ['Content', 'PageOne', 'PageTwo', 'PageThree', 'PageFour', 'PageFive', 'Member'];
      this.showNav = !hiddenNavPages.includes(route.name);
    }
  }
};
</script>


<style>
body {
  background: url('./assets/altercor.jpg') no-repeat center center fixed;
  background-size: cover; /* 改为cover，使背景适应屏幕大小 */
  margin: 0;
  padding: 0;
}

.header {
  text-align: left;
  padding: 10px 20px;
  font-size: 24px;
  font-weight: bold;
  color: #333;
  background-color: #ffffff; 
}

.header2 {
  width: 100%; /* 修改为100% */
  text-align: left;
  padding: 10px 20px;
  font-size: 24px;
  font-weight: bold;
  color: #333;
  background-color: #ffffff; 
}

nav {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

nav a {
  margin: 0 15px;
  text-decoration: none;
  color: #000000; /* 将链接颜色改为黑色 */
}

nav a:hover {
  color: #ffffff; /* 鼠标悬停时的颜色改为白色 */
}

#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
}
</style>
