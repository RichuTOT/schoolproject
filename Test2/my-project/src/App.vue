<template>
  <div id="app">

    <header class="header">
      社团联盟登录平台
    </header>

    <router-view></router-view>
    <nav>
      <!--导航栏-->
      <router-link to="/">登录</router-link>
      <router-link to="/register">注册</router-link>
    </nav>
    
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style>
body {
  background: url('./assets/altercor.jpg') no-repeat center center fixed;
  background-size: cover;
  margin: 0;
  padding: 0;
}

.header {
  width: 400%;
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
  color: #ffffff;
}

nav a:hover {
  color: #ffffff;
}

#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
}
</style>
