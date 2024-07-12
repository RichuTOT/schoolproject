<template>
  <div class="activity-release">
    <h2>活动发布</h2>
    <el-form :model="activityForm" label-width="100px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="活动名称">
            <el-input v-model="activityForm.name" placeholder="请输入"></el-input>
          </el-form-item>

          <el-form-item label="详细介绍">
            <el-input type="textarea" v-model="activityForm.description" placeholder="请输入"></el-input>
          </el-form-item>

          <el-form-item label="活动地点">
            <el-input v-model="activityForm.location" placeholder="请输入"></el-input>
          </el-form-item>

          <el-form-item label="社团名称">
            <el-input v-model="activityForm.clubName" placeholder="请输入"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="添加图片">
            <el-upload
              action="http://localhost:8088/api/upload"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item>
        <el-button type="primary" @click="saveActivity">保存</el-button>
        <el-button type="success" @click="publishActivity">发布</el-button>
      </el-form-item>
    </el-form>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import axios from '../axios';
import { ref } from 'vue';

export default {
  name: 'PageThree',
  setup() {
    const activityForm = ref({
      name: '',
      description: '',
      location: '',
      clubName: '',
      images: []
    });

    const dialogImageUrl = ref('');
    const dialogVisible = ref(false);

    const handlePictureCardPreview = (file) => {
      dialogImageUrl.value = file.url;
      dialogVisible.value = true;
    };

    const handleRemove = (file, fileList) => {
      console.log(file, fileList);
    };

    const handleUploadSuccess = (response, file, fileList) => {
      activityForm.value.images.push(response.url);
    };

    const handleUploadError = (err, file, fileList) => {
      console.error('Upload error:', err);
    };

    const saveActivity = () => {
      console.log('Activity saved', activityForm.value);
    };

    const publishActivity = async () => {
      try {
        const activityData = {
          ...activityForm.value,
          publishTime: new Date().toISOString()  // 添加当前时间
        };
        const response = await axios.post('/api/activities', activityData);
        console.log('Activity published', response.data);
        activityForm.value = {
          name: '',
          description: '',
          location: '',
          clubName: '',
          images: []
        };
      } catch (error) {
        console.error('Error publishing activity:', error);
      }
    };

    return {
      activityForm,
      dialogImageUrl,
      dialogVisible,
      handlePictureCardPreview,
      handleRemove,
      handleUploadSuccess,
      handleUploadError,
      saveActivity,
      publishActivity
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
