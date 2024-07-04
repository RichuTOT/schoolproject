<template>
    <div class="dashboard">
      <h1>欢迎来到社团联盟</h1>
      <p>这是一个新的页面，只有登录成功后才能访问。</p>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Dashboard'
  };
  </script>
  
  <style scoped>
  .dashboard {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    text-align: center;
  }
  </style>
  