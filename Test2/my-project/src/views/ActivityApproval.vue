<template>
  <div class="activity-approval">
    <el-card v-for="(activity, index) in activities" :key="index" class="activity-card">
      <div slot="header" class="clearfix">
        <span>活动申请</span>
        <el-button 
          type="success" 
          icon="el-icon-check" 
          size="mini" 
          @click="approveActivity(activity.id, index)"
          style="float: right; margin-left: 10px;">       
        </el-button>
        <el-button 
          type="danger" 
          icon="el-icon-close" 
          size="mini" 
          @click="rejectActivity(activity.id, index)"
          style="float: right;">
        </el-button>
      </div>
      <div>
        <p><strong>活动状态:</strong> {{ activity.status }}</p>
        <p><strong>申请社团:</strong> {{ activity.clubName }}</p>
        <p><strong>活动名称:</strong> {{ activity.name }}</p>
        <p><strong>活动时间:</strong> {{ formatDate(activity.publishTime) }}</p>
        <p><strong>活动地点:</strong> {{ activity.location }}</p>
        <p><strong>活动介绍:</strong> {{ activity.description }}</p>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';

export default {
  name: 'ActivityApproval',
  setup() {
    const activities = ref([]);

    const approveActivity = async (id, index) => {
      try {
        await axios.post(`http://localhost:8088/api/activities/approve/${id}`);
        activities.value[index].status = 'approved';
      } catch (error) {
        console.error('Error approving activity:', error);
      }
    };

    const rejectActivity = async (id, index) => {
      try {
        await axios.post(`http://localhost:8088/api/activities/reject/${id}`);
        activities.value[index].status = 'rejected';
      } catch (error) {
        console.error('Error rejecting activity:', error);
      }
    };

    const fetchActivities = async () => {
      try {
        const response = await axios.get('http://localhost:8088/api/activities/pending');
        activities.value = response.data;
      } catch (error) {
        console.error('Error fetching activities:', error);
      }
    };

    onMounted(fetchActivities);

    const formatDate = (dateString) => {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    };

    return {
      activities,
      approveActivity,
      rejectActivity,
      formatDate
    };
  }
};
</script>

<style scoped>
.activity-approval {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.activity-card {
  width: 80%;
  margin: 10px 0;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.el-card__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-button {
  margin-left: 10px;
}
</style>
