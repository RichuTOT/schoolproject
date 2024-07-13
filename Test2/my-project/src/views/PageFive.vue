<template>
  <div class="activity-release">
    <h2>创建社团</h2>
    <el-form :model="form" label-width="100px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="社团名称">
            <el-input v-model="form.clubName" placeholder="请输入名称"></el-input>
          </el-form-item>
          <el-form-item label="详细介绍">
            <el-input type="textarea" v-model="form.description" placeholder="请输入关于社团的详细介绍"></el-input>
          </el-form-item>
          <el-form-item label="社团分类">
            <el-select v-model="form.category" placeholder="请选择">
              <el-option label="文学类" value="1"></el-option>
              <el-option label="体育类" value="2"></el-option>
              <el-option label="哲学类" value="3"></el-option>
              <el-option label="其他" value="4"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布人">
            <el-input v-model="form.publisher" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="success" @click="onApply">申请</el-button>
      </el-form-item>
    </el-form>
    <div class="club-list">
      <h3>社团清单</h3>
      <el-table :data="clubs" style="width: 100%">
        <el-table-column prop="date" label="时间" width="180"></el-table-column>
        <el-table-column prop="name" label="社团名称" width="180"></el-table-column>
        <el-table-column prop="status" label="审核状态" width="180"></el-table-column>
      </el-table>
    </div>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';

export default {
  name: 'PageFive',
  setup() {
    const form = ref({
      clubName: '',
      description: '',
      category: '',
      publisher: '',
      images: []
    });

    const dialogImageUrl = ref('');
    const dialogVisible = ref(false);
    const userId = localStorage.getItem('userId');
    const username = localStorage.getItem('username');

    onMounted(() => {
      if (username) {
        form.value.publisher = username;
      }
    });

    const handlePictureCardPreview = (file) => {
      dialogImageUrl.value = file.url;
      dialogVisible.value = true;
    };

    const handleRemove = (file, fileList) => {
      console.log(file, fileList);
    };

    const handleUploadSuccess = (response, file, fileList) => {
      console.log('Upload success:', response);
      form.value.images.push(response.url);
    };

    const handleUploadError = (err, file, fileList) => {
      console.error('Upload error:', err);
    };

    const onApply = async () => {
  if (!userId) {
    console.error('用户未登录');
    return;
  }
  const clubApplicationData = {
    ...form.value,
    userId: userId,
    applyTime: new Date().toISOString(),
    status: 'pending'
  };
  try {
    const response = await axios.post('http://localhost:8088/api/club-applications', clubApplicationData, { withCredentials: true });
    console.log('申请成功', response.data);
    form.value = {
      clubName: '',
      description: '',
      category: '',
      publisher: '',
      images: []
    };
  } catch (error) {
    console.error('申请失败', error);
  }
};

    return {
      form,
      dialogImageUrl,
      dialogVisible,
      handlePictureCardPreview,
      handleRemove,
      handleUploadSuccess,
      handleUploadError,
      onApply
    };
  }
};
</script>

<style scoped>
.activity-release {
  padding: 20px;
}

.el-form-item {
  margin-top: 50px;
}

.el-upload {
  display: block;
  width: 100%;
  margin-top: 20px;
}

.el-button {
  display: flex;
  margin-right: 20px;
}
</style>
