<template>
  <div class="container">
    <header class="header">
      <h1>社团联盟登录平台</h1>
    </header>
    <el-form :model="registerForm" ref="registerForm" :rules="rules" label-width="80px" class="form">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="registerForm.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="registerForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input type="password" v-model="registerForm.confirmPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item class="form-actions">
        <el-button type="primary" class="register-button" @click="handleRegister">注册</el-button>
      </el-form-item>
    </el-form>
    <div class="alternative-login">
      <p>使用其他方式登录</p>
      <div class="icons">
        <i class="fab fa-weixin"></i>
        <i class="fab fa-qq"></i>
        <i class="fas fa-phone-alt"></i>
        <i class="fas fa-id-card"></i>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

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
          axios.post('/api/users/register', this.registerForm)
            .then(response => {
              alert(response.data);
              this.$router.push({ name: 'Login' }); // 注册成功后跳转到登录页面
            })
            .catch(error => {
              alert(`错误: ${error.response ? error.response.data : error.message}`);
            });
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
.container {
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #dcdcdc;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
  background-color: #ffffff;
  margin-top: 50px;
  text-align: center;
  height: 400px; /* 固定高度 */

}

.form {
  width: 100%;
}

.form-actions {
  text-align: right;
}

.register-button {
  position: absolute; /* 可以使用绝对定位调整位置 */
  right: 3px; /* 根据需要调整 */
  bottom: -25px; /* 根据需要调整 */
}

.header {
  margin-bottom: 20px;
}

.header h1 {
  font-size: 24px;
  margin: 0;
  padding: 10px 0;
}

.alternative-login {
  text-align: center;
  margin-top: 120px;
}

.icons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 10px;
}

.icons i {
  font-size: 24px;
  cursor: pointer;
  transition: color 0.3s;
}

.icons i:hover {
  color: #409EFF; /* 悬停颜色变化 */
}
</style>
