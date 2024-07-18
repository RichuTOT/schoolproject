<template>
  <div class="activity-approval">
    <el-table v-if="filteredActivities.length > 0" :data="filteredActivities" style="width: 100%">
      <el-table-column prop="clubName" label="申请社团" width="180"></el-table-column>
      <el-table-column prop="name" label="活动名称" width="180"></el-table-column>
      <el-table-column prop="publishTime" label="活动时间" width="180">
        <template v-slot="scope">
          <span>{{ formatDate(scope.row.publishTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="location" label="活动地点" width="180"></el-table-column>
      <el-table-column prop="description" label="活动介绍"></el-table-column>
      <el-table-column prop="status" label="活动状态" width="180">
        <template v-slot="scope">
          <span :style="{ color: getStatusColor(scope.row.status) }">
            {{ scope.row.status }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template v-slot="scope">
          <el-button 
            type="success" 
            size="mini" 
            @click="confirmApprove(scope.row.id, scope.$index)"
            :disabled="scope.row.status !== '审核中'"
            style="margin-right: 10px;">
          同意</el-button>
          <el-button 
            type="danger"  
            size="mini" 
            @click="confirmReject(scope.row.id, scope.$index)"
            :disabled="scope.row.status !== '审核中'">
          拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div v-else class="no-activities">暂无活动</div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted, computed } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';


export default {
  name: 'ActivityApproval',
  setup() {
    const activities = ref([]);

    const confirmApprove = async (id, index) => {
      ElMessageBox.confirm(
        '确认通过该活动？',
        '提示',
        {
          confirmButtonText: '通过',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        approveActivity(id, index);
      }).catch(() => {
        ElMessage.info('已取消操作');
      });
    };

    const confirmReject = async (id, index) => {
      ElMessageBox.confirm(
        '确认拒绝该活动？',
        '提示',
        {
          confirmButtonText: '拒绝',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        rejectActivity(id, index);
      }).catch(() => {
        ElMessage.info('已取消操作');
      });
    };

    const approveActivity = async (id, index) => {
      try {
        await axios.post(`http://localhost:8088/api/activities/approve/${id}`);
        activities.value[index].status = '已通过';
        ElMessage.success('活动已通过审核');
        activities.value.splice(index, 1);
        fetchActivities();
      } catch (error) {
        console.error('Error approving activity:', error);
        ElMessage.error('审核活动时发生错误');
      }
    };

    const rejectActivity = async (id, index) => {
      try {
        await axios.post(`http://localhost:8088/api/activities/reject/${id}`);
        activities.value[index].status = '已拒绝';
        ElMessage.success('活动已拒绝');
        activities.value.splice(index, 1);
        fetchActivities();
      } catch (error) {
        console.error('Error rejecting activity:', error);
        ElMessage.error('拒绝活动时发生错误');
      }
    };

    const fetchActivities = async () => {
      try {
        const response = await axios.get('http://localhost:8088/api/activities');
        activities.value = response.data;
      } catch (error) {
        console.error('Error fetching activities:', error);
        ElMessage.error('获取活动数据失败');
      }
    };

    onMounted(fetchActivities);

    const formatDate = (dateString) => {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    };

    const getStatusColor = (status) => {
      switch (status) {
        case '已通过':
          return 'green';
        case '已拒绝':
          return 'red';
        case '审核中':
        default:
          return 'orange';
      }
    };

    const filteredActivities = computed(() => {
      return activities.value.filter(activity => 
        ['审核中', '已通过', '已拒绝'].includes(activity.status)
      ).sort((a, b) => {
        if (a.status === '审核中' && b.status !== '审核中') {
          return -1;
        } else if (a.status !== '审核中' && b.status === '审核中') {
          return 1;
        }
        return new Date(b.publishTime) - new Date(a.publishTime);
      });
    });

    return {
      activities,
      confirmApprove,
      confirmReject,
      approveActivity,
      rejectActivity,
      formatDate,
      getStatusColor,
      filteredActivities
    };
  }
};
</script>

<style scoped>
.activity-approval {
  padding: 20px;
}

.no-activities {
  text-align: center;
  color: #999;
  margin-top: 20px;
}

.el-button {
  margin-right: 10px;
}
</style>
