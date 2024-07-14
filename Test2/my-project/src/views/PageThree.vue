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
              action="http://localhost:8088/api/activities/upload"
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
        <el-button type="success" @click="publishActivity">发布</el-button>
      </el-form-item>
    </el-form>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
    <h2>审核中的活动</h2>
    <el-table :data="pendingActivities" style="width: 100%">
      <el-table-column prop="name" label="活动名称" width="180"></el-table-column>
      <el-table-column prop="status" label="状态" width="180"></el-table-column>
      <el-table-column prop="date" label="申请时间" width="180"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';

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
    const pendingActivities = ref([]);

    const handlePictureCardPreview = (file) => {
      dialogImageUrl.value = file.url;
      dialogVisible.value = true;
    };

    const handleRemove = (file, fileList) => {
      console.log(file, fileList);
    };

    const handleUploadSuccess = (response, file, fileList) => {
      console.log('Upload success:', response);
      activityForm.value.images.push(response.url);
    };

    const handleUploadError = (err, file, fileList) => {
      console.error('Upload error:', err);
    };

    const fetchPendingActivities = async () => {
      try {
        const response = await axios.get('http://localhost:8088/api/activities/pending', { withCredentials: true });
        pendingActivities.value = response.data;
      } catch (error) {
        console.error('Error fetching pending activities:', error);
      }
    };

    const publishActivity = async () => {
      const activityData = {
        ...activityForm.value,
        publishTime: new Date().toISOString(),
        status: 'pending'
      };
      try {
        const response = await axios.post('http://localhost:8088/api/activities', activityData);
        console.log('Activity submitted for approval', response.data);
        activityForm.value = {
          name: '',
          description: '',
          location: '',
          clubName: '',
          images: []
        };
        fetchPendingActivities(); // Refresh pending activities list
      } catch (error) {
        console.error('Error publishing activity:', error);
      }
    };

    onMounted(() => {
      fetchPendingActivities();
    });

    return {
      activityForm,
      dialogImageUrl,
      dialogVisible,
      pendingActivities,
      handlePictureCardPreview,
      handleRemove,
      handleUploadSuccess,
      handleUploadError,
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
