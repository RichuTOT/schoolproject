<template>
    <div class="register-container">
      <el-form :model="registerForm" ref="registerForm" :rules="rules" label-width="80px" class="register-form">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="registerForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" v-model="registerForm.confirmPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Register',
    data() {
      return {
        registerForm: {
          username: '',
          password: '',
          confirmPassword: ''
        },
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ],
          confirmPassword: [
            { required: true, message: '请确认密码', trigger: 'blur' },
            { validator: (rule, value, callback) => {
                if (value !== this.registerForm.password) {
                  callback(new Error('两次输入的密码不一致'));
                } else {
                  callback();
                }
              }, trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      handleRegister() {
        this.$refs.registerForm.validate((valid) => {
          if (valid) {
            alert('注册成功');
            // 在此处添加注册逻辑，例如 API 调用
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  };
  </script>
  
  <style scoped>
  .register-container {
    width: 300px;
    margin: 50px auto;
    padding: 60px;
    border: 1px solid #dcdcdc;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    background-color: #ffffff;
    margin-top: 50px; /* 使表单居中 */
  }
  .register-form {
    width: 100%;
  }
  </style>
  