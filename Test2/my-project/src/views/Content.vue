<template>
    <div class="content">
      <img src="../assets/tu.jpg"  />
      <p>活动详情</p>
      
    </div>
  </template>
  
  <script>
  export default {
    name: 'Content'
  };
  </script>
  
  <style scoped>
  .content {
    text-align: center;
  }
  
  .content img {
    max-width: 100%;
    height: 200px;
    margin-bottom: 20px;
  }
  
  .content p {
    display: flex;
    font-size: 16px;
  }
  
  .content p a:hover {
    text-decoration: underline;
  }
  </style>
  