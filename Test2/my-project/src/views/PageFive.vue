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
              <el-option label="文学类" value="文学类"></el-option>
              <el-option label="体育类" value="体育类"></el-option>
              <el-option label="哲学类" value="哲学类"></el-option>
              <el-option label="其他" value="其他"></el-option>
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
      <h3>申请创立社团</h3>
      <el-table :data="clubApplications" style="width: 100%">
        <el-table-column prop="applyTime" label="时间" width="180" :formatter="formatDate"></el-table-column>
        <el-table-column prop="clubName" label="社团名称" width="180"></el-table-column>
        <el-table-column label="审核状态" width="180">
          <template #default="scope">
            <span :class="statusClass(scope.row.status)">
              {{ formatStatus(scope.row.status) }}
            </span>
          </template>
        </el-table-column>
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
import { ElMessage } from 'element-plus';

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
    const clubApplications = ref([]);
    const userId = localStorage.getItem('userId');
    const username = localStorage.getItem('username');

    onMounted(async () => {
      if (username) {
        form.value.publisher = username;
      }
      await fetchClubApplications();
    });

    const fetchClubApplications = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/api/club-applications/user?userId=${userId}`);
        const sortedApplications = response.data.sort((a, b) => {
          if (a.status === 'approved' && b.status !== 'approved') {
            return -1;
          } else if (a.status !== 'approved' && b.status === 'approved') {
            return 1;
          } else {
            return new Date(b.applyTime) - new Date(a.applyTime);
          }
        });
        clubApplications.value = sortedApplications;
      } catch (error) {
        console.error('Error fetching club applications:', error);
      }
    };

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

      const existingApplications = clubApplications.value.filter(app => app.status === 'pending' || app.status === 'approved');
      if (existingApplications.length > 0) {
        ElMessage({
          message: '已经申请过了，正在审核或已通过，耐心等待~',
          type: 'error'
        });
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
        await fetchClubApplications(); // Refresh the club applications list
      } catch (error) {
        ElMessage({
          message: '申请失败，请重试。',
          type: 'error'
        });
        console.error('申请失败', error);
      }
    };

    const formatDate = (row, column, cellValue) => {
      const date = new Date(cellValue);
      return date.toLocaleDateString();
    };

    const formatStatus = (status) => {
      if (status === 'pending') {
        return '审核中';
      }
      return status === 'approved' ? '已同意' : '已拒绝';
    };

    const statusClass = (status) => {
      if (status === 'pending') {
        return 'pending-status';
      } else if (status === 'approved') {
        return 'approved-status';
      } else {
        return 'rejected-status';
      }
    };

    return {
      form,
      dialogImageUrl,
      dialogVisible,
      clubApplications,
      handlePictureCardPreview,
      handleRemove,
      handleUploadSuccess,
      handleUploadError,
      onApply,
      formatDate,
      formatStatus,
      statusClass
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

.pending-status {
  color: orange;
}

.approved-status {
  color: green;
}

.rejected-status {
  color: red;
}
</style>
