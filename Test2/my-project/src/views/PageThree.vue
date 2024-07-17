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
            <el-input v-model="activityForm.clubName" placeholder="请输入" readonly></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="添加图片">
            <el-upload
              action="http://localhost:8088/api/activities/upload"
              list-type="picture-card"
              :headers="uploadHeaders"
              :data="uploadData"
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
    <el-table :data="sortedPendingActivities" style="width: 100%">
      <el-table-column prop="publishTime" label="申请时间" width="180">
        <template v-slot="scope">
          <span>{{ formatDate(scope.row.publishTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="活动名称" width="180"></el-table-column>
      <el-table-column prop="status" label="状态" width="180">
        <template v-slot="scope">
          <span :style="{ color: getStatusColor(scope.row.status) }">
            {{ scope.row.status }}
          </span>
        </template>
      </el-table-column>
    </el-table>
    <div v-if="sortedPendingActivities.length === 0" class="no-requests">暂无审核中的活动</div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted, computed } from 'vue';
axios.defaults.withCredentials = true;

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
    const requestUrl = ref('');
    const requestError = ref('');

    // 从缓存中获取 user_id
    const userId = localStorage.getItem('userId');

    const handlePictureCardPreview = (file) => {
      dialogImageUrl.value = file.url;
      dialogVisible.value = true;
    };

    const handleRemove = (file, fileList) => {
      console.log(file, fileList);
    };

    const handleUploadSuccess = (response, file, fileList) => {
      console.log('Upload success:', response);
      activityForm.value.images.push({ url: response.url, name: file.name });
    };

    const handleUploadError = (err, file, fileList) => {
      console.error('Upload error:', err);
    };

    const fetchPendingActivities = async () => {
      requestUrl.value = `http://localhost:8088/api/activities/user/${userId}`;
      try {
        const response = await axios.get(requestUrl.value);
        pendingActivities.value = response.data;
      } catch (error) {
        requestError.value = `Error fetching activities: ${error}`;
        console.error(requestError.value);
      }
    };

    const fetchClubName = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/api/clubs/user/${userId}/getclub`, {
          withCredentials: true,
        });
        activityForm.value.clubName = response.data.name;
      } catch (error) {
        requestError.value = `Error fetching club name: ${error}`;
        console.error(requestError.value);
      }
    };

    const publishActivity = async () => {
      const activityData = {
        ...activityForm.value,
        publishTime: new Date().toISOString(),
        status: 'pending',
        userId: userId
      };
      try {
        const response = await axios.post('http://localhost:8088/api/activities', activityData);
        console.log('Activity submitted for approval', response.data);
        activityForm.value = {
          name: '',
          description: '',
          location: '',
          clubName: activityForm.value.clubName,
          images: []
        };
        await fetchPendingActivities();
      } catch (error) {
        console.error('Error publishing activity:', error);
      }
    };

    const getStatusColor = (status) => {
      switch (status) {
        case 'approved':
          return 'green';
        case 'rejected':
          return 'red';
        case 'pending':
        default:
          return 'orange';
      }
    };

    const formatDate = (dateString) => {
      const date = new Date(dateString);
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
    };

    const sortedPendingActivities = computed(() => {
      return pendingActivities.value.sort((a, b) => new Date(b.publishTime) - new Date(a.publishTime));
    });

    onMounted(() => {
      fetchPendingActivities();
      fetchClubName();
    });

    const uploadData = {
      userId,
    };

    const uploadHeaders = {
      Authorization: `Bearer ${localStorage.getItem('token')}`,
    };

    return {
      activityForm,
      dialogImageUrl,
      dialogVisible,
      sortedPendingActivities,
      handlePictureCardPreview,
      handleRemove,
      handleUploadSuccess,
      handleUploadError,
      publishActivity,
      requestUrl,
      requestError,
      getStatusColor,
      formatDate,
      uploadData,
      uploadHeaders,
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

.no-requests {
  text-align: center;
  color: #999;
  margin-top: 20px;
}
</style>
