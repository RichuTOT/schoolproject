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
      const hiddenNavPages = ['Content', 'PageOne', 'PageTwo', 'PageThree', 'PageFour', 'PageFive', 'Member','Statistics','ClubApproval','ClubManagement','ActivityApproval','Members'];
      this.showNav = !hiddenNavPages.includes(route.name);
    }
  }
};
</script>


<style>
body {
  background: url('./assets/beijing1.jpg') no-repeat center center fixed;
  background-size: cover;
  margin: 0;
  padding: 0;
}

.header2 {
  width: 100%;
  text-align: left;
  padding: 10px 20px;
  font-size: 24px;
  margin-left: 25px;
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
  color: #000;
}

nav a:hover {
  color: #fff;
}

#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  width: 100%;
}

@media (max-width: 768px) {
  .header2 {
    font-size: 20px;
  }

  nav a {
    margin: 0 10px;
  }
}

</style>
