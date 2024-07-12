<template>
    <div class="create-club-container">
      <h2>创建社团</h2>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="社团名称">
          <el-input v-model="form.clubName" placeholder="请输入名称"></el-input>
        </el-form-item>
        <el-form-item label="社团分类">
          <el-select v-model="form.category" placeholder="请选择">
            <el-option label="文学类" value="1"></el-option>
            <el-option label="体育类" value="2"></el-option>
            <el-option label="哲学类" value="3"></el-option>
            <el-option label="其他" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布人">
          <el-input v-model="form.publisher" placeholder="请输入名称"></el-input>
        </el-form-item>
        <el-form-item label="介绍">
          <el-input type="textarea" v-model="form.description" placeholder="请输入关于社团的详细..."></el-input>
        </el-form-item>
        <div class="upload-container">
          <div class="upload-section">
            <label>添加图片</label>
            <el-upload
              class="upload-demo"
              drag
              action="#"
              multiple
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">点击上传图片</div>
            </el-upload>
          </div>
          <div class="upload-section">
            <label>添加申请书</label>
            <el-upload
              class="upload-demo"
              drag
              action="#"
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">点击上传申请书</div>
            </el-upload>
          </div>
        </div>
      </el-form>
      <div class="buttons">
        <el-button type="primary" @click="onSave">保存</el-button>
        <el-button type="success" @click="onApply">申请</el-button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'PageFive',
    data() {
      return {
        form: {
          clubName: '',
          category: '',
          publisher: '',
          description: '',
        }
      };
    },
    methods: {
      onSave() {
        // 保存逻辑
        console.log('保存', this.form);
      },
      onApply() {
        // 申请逻辑
        console.log('申请', this.form);
      }
    }
  };
  </script>
  
  <style scoped>
  .create-club-container {
    padding: 20px;
    max-width: 800px;
    margin: 0 auto;
  }
  
  .upload-container {
    display: flex;
    justify-content: flex-start;
  }
  .upload-section {
    width: 15%;
    margin-right: 100px;
  }
  
  
  .upload-demo {
    width: 100%;
    height: 100px;
    margin-top: 30px;
  }
  
  .buttons {
    display: flex;
    justify-content: end;  
    margin-top: 20px;
  }
  </style>
